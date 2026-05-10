package nl.han.serdaraydemir.zilvervisjes.entities.silverfish;

import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.Size;
import com.github.hanyaeger.api.Timer;
import com.github.hanyaeger.api.TimerContainer;
import com.github.hanyaeger.api.entities.Collided;
import com.github.hanyaeger.api.entities.Collider;
import com.github.hanyaeger.api.entities.SceneBorderCrossingWatcher;
import com.github.hanyaeger.api.entities.impl.DynamicSpriteEntity;
import com.github.hanyaeger.api.scenes.SceneBorder;
import nl.han.serdaraydemir.zilvervisjes.entities.documents.Document;
import com.github.hanyaeger.api.AnchorPoint;

import java.util.List;
import java.util.function.Consumer;

/**
 * Abstracte basisklasse voor alle zilvervis-soorten in het spel.
 * Bevat de gemeenschappelijke logica voor beweging, doelwit-bepaling
 * en schade-afhandeling. Subklassen overschrijven gedrag waar nodig
 * (zigzag-beweging, voorkeur voor dossiers, frame-wisseling bij schade).
 */
public abstract class Silverfish extends DynamicSpriteEntity
        implements SceneBorderCrossingWatcher, Collided, Collider, TimerContainer {

    private static final int TARGET_REFRESH_INTERVAL_MS = 500;
    private static final int ATTACK_INTERVAL_MS = 3000;

    private final List<Document> targets;
    private final int pointsValue;
    private int health;
    private Document currentTarget = null;
    private Document attackingTarget = null;
    private Consumer<Silverfish> deathListener = silverfish -> {};

    /**
     * Maakt een nieuwe silverfish met een single-frame sprite.
     *
     * @param spritePath pad naar het sprite-bestand
     * @param size grootte van de sprite
     * @param location startpositie van de silverfish
     * @param targets lijst van documenten die als doelwit kunnen dienen
     * @param health beginwaarde van de health
     * @param pointsValue aantal punten dat de speler ontvangt bij uitschakelen
     */
    protected Silverfish(String spritePath, Size size, Coordinate2D location,
                         List<Document> targets, int health, int pointsValue) {
        this(spritePath, size, 1, 1, location, targets, health, pointsValue);
    }

    /**
     * Maakt een nieuwe silverfish met een multi-frame sprite. Deze constructor
     * wordt gebruikt door soorten die hun gezondheidstoestand visueel weergeven
     * via meerdere sprite-frames (zoals het Ovenvisje).
     *
     * @param spritePath pad naar het sprite-bestand
     * @param size grootte van de sprite
     * @param rows aantal rijen in het sprite-sheet
     * @param columns aantal kolommen in het sprite-sheet
     * @param location startpositie van de silverfish
     * @param targets lijst van documenten die als doelwit kunnen dienen
     * @param health beginwaarde van de health
     * @param pointsValue aantal punten dat de speler ontvangt bij uitschakelen
     */
    protected Silverfish(String spritePath, Size size, int rows, int columns,
                         Coordinate2D location, List<Document> targets,
                         int health, int pointsValue) {
        super(spritePath, location, size, rows, columns);
        setAnchorPoint(AnchorPoint.CENTER_CENTER);
        this.targets = targets;
        this.health = health;
        this.pointsValue = pointsValue;
    }

    @Override
    public void setupTimers() {
        addTimer(new TargetRefreshTimer(TARGET_REFRESH_INTERVAL_MS));
        addTimer(new AttackTimer(ATTACK_INTERVAL_MS));
    }

    @Override
    public void onCollision(List<Collider> collidingObjects) {
        for (Collider other : collidingObjects) {
            if (other instanceof Document doc && !doc.isDestroyed()) {
                setSpeed(0);
                attackingTarget = doc;
                currentTarget = doc;
                return;
            }
        }
    }

    @Override
    public void notifyBoundaryCrossing(SceneBorder border) {
        remove();
    }

    /**
     * Vermindert de health van deze silverfish met de meegegeven schade.
     * Wanneer de health op nul of lager komt, wordt de deathListener
     * aangeroepen en wordt de silverfish uit de scene verwijderd.
     *
     * @param amount hoeveelheid schade die wordt toegebracht
     */
    public void takeDamage(int amount) {
        if (health <= 0) {
            return;
        }
        health -= amount;
        if (health <= 0) {
            deathListener.accept(this);
            remove();
        }
    }

    /**
     * Geeft de puntenwaarde van deze silverfish terug.
     *
     * @return aantal punten dat de speler verdient bij het uitschakelen
     */
    public int getPointsValue() {
        return pointsValue;
    }

    /**
     * Geeft de huidige health-waarde terug.
     *
     * @return resterende health
     */
    public int getHealth() {
        return health;
    }

    /**
     * Stelt de listener in die wordt aangeroepen zodra deze silverfish sterft.
     * Wordt gebruikt door GameScene om de score bij te werken.
     *
     * @param listener callback die de stervende silverfish ontvangt
     */
    public void setDeathListener(Consumer<Silverfish> listener) {
        if (listener != null) {
            this.deathListener = listener;
        }
    }

    /**
     * Geeft de bewegingssnelheid van deze silverfish terug.
     * Subklassen leveren hun eigen waarde aan.
     *
     * @return snelheid in pixels per tick
     */
    protected abstract double getSpeedValue();

    /**
     * Geeft de schade terug die deze silverfish per aanval aan een document toebrengt.
     * Standaard één eenheid; subklassen kunnen dit overschrijven (bijvoorbeeld de
     * Papiervis voor een drievoudige schade-multiplier).
     *
     * @return schade per aanval
     */
    protected int getAttackDamage() {
        return 1;
    }

    /**
     * Wordt aangeroepen zodra de hoek naar het huidige doelwit is bijgewerkt.
     * Subklassen kunnen dit overschrijven om bijvoorbeeld een zigzag-effect
     * rond de basishoek toe te voegen.
     *
     * @param angle hoek naar het doelwit in graden (Yaeger-kompasconventie)
     */
    protected void onTargetAngleUpdated(double angle) {
        // Subclasses can override to track changes in target heading
    }

    /**
     * Geeft de lijst van documenten terug die als doelwit kunnen dienen.
     * Wordt door subklassen gebruikt om eigen doelwitlogica te implementeren
     * (zoals de Papiervis die dossiers boven boeken prioriteert).
     *
     * @return lijst van documenten
     */
    protected List<Document> getTargets() {
        return targets;
    }

    /**
     * Stelt zowel de motion als de rotatie van de sprite in op de gegeven hoek.
     * De rotatie krijgt een correctie van 180 graden omdat de zilvervis-sprite
     * standaard naar boven kijkt (Yaeger-kompasconventie versus JavaFX-rotatie).
     *
     * @param angle hoek waarin de silverfish moet bewegen, in graden
     */
    protected void aimAt(double angle) {
        setMotion(getSpeedValue(), angle);
        setRotate(angle + 180);
    }

    /**
     * Bepaalt het dichtstbijzijnde nog intacte document vanuit de gegeven positie.
     *
     * @param from positie van waaruit de afstand wordt berekend
     * @return het dichtstbijzijnde intacte document, of null als er geen meer over zijn
     */
    protected Document findClosestDocumentTo(Coordinate2D from) {
        Document closest = null;
        double minDistance = Double.MAX_VALUE;
        for (Document doc : targets) {
            if (doc.isDestroyed()) continue;
            double distance = from.distance(doc.getAnchorLocation());
            if (distance < minDistance) {
                minDistance = distance;
                closest = doc;
            }
        }
        return closest;
    }

    private void refreshTarget() {
        if (attackingTarget != null && !attackingTarget.isDestroyed()) {
            return;
        }
        attackingTarget = null;

        Document closest = findClosestDocumentTo(getAnchorLocation());
        if (closest == null) {
            setSpeed(0);
            currentTarget = null;
            return;
        }

        currentTarget = closest;
        double angle = getAnchorLocation().angleTo(closest.getAnchorLocation());
        onTargetAngleUpdated(angle);
        aimAt(angle);
    }

    private void attackTick() {
        if (attackingTarget != null && !attackingTarget.isDestroyed()) {
            attackingTarget.takeDamage(getAttackDamage());
        }
    }

    private class TargetRefreshTimer extends Timer {
        protected TargetRefreshTimer(int intervalInMs) {
            super(intervalInMs);
        }

        @Override
        public void onAnimationUpdate(long timestamp) {
            refreshTarget();
        }
    }

    private class AttackTimer extends Timer {
        protected AttackTimer(int intervalInMs) {
            super(intervalInMs);
        }

        @Override
        public void onAnimationUpdate(long timestamp) {
            attackTick();
        }
    }
}