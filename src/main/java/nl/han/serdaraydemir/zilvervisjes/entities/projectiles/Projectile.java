package nl.han.serdaraydemir.zilvervisjes.entities.projectiles;

import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.Size;
import com.github.hanyaeger.api.entities.Collided;
import com.github.hanyaeger.api.entities.Collider;
import com.github.hanyaeger.api.entities.SceneBorderCrossingWatcher;
import com.github.hanyaeger.api.entities.impl.DynamicSpriteEntity;
import com.github.hanyaeger.api.scenes.SceneBorder;
import nl.han.serdaraydemir.zilvervisjes.entities.silverfish.Silverfish;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Abstracte basisklasse voor projectielen die door wapens worden afgevuurd.
 * Beheert collision-afhandeling met zilvervisjes en houdt bij welke
 * zilvervisjes al geraakt zijn om dubbele schade te voorkomen. Subklassen
 * bepalen via isAreaEffect() of het projectiel meerdere doelwitten kan
 * raken of na de eerste treffer verdwijnt.
 */
public abstract class Projectile extends DynamicSpriteEntity
        implements SceneBorderCrossingWatcher, Collided {

    private final int damage;
    private final Set<Silverfish> alreadyHit = new HashSet<>();
    private boolean expired = false;

    /**
     * Maakt een nieuw projectiel aan en stelt direct zijn motion in.
     *
     * @param spritePath pad naar het sprite-bestand
     * @param size grootte van de sprite
     * @param location startpositie van het projectiel
     * @param speed snelheid in pixels per tick
     * @param angle richting waarin het projectiel beweegt, in graden
     * @param damage schade die per treffer wordt toegebracht
     */
    protected Projectile(String spritePath, Size size, Coordinate2D location,
                         double speed, double angle, int damage) {
        super(spritePath, location, size);
        this.damage = damage;
        setMotion(speed, angle);
    }

    /**
     * Geeft de schadewaarde van dit projectiel terug.
     *
     * @return schade per treffer
     */
    public int getDamage() {
        return damage;
    }

    @Override
    public void notifyBoundaryCrossing(SceneBorder border) {
        expired = true;
        remove();
    }

    /**
     * Wordt aangeroepen wanneer dit projectiel een ander object raakt.
     * Voor elke geraakte silverfish wordt schade toegebracht; bij niet-
     * area-effect projectielen verdwijnt het projectiel na de eerste
     * treffer. Dezelfde silverfish kan niet tweemaal door hetzelfde
     * projectiel geraakt worden.
     *
     * @param collidingObjects lijst van objecten waarmee dit projectiel botst
     */
    @Override
    public void onCollision(List<Collider> collidingObjects) {
        if (expired) {
            return;
        }
        for (Collider other : collidingObjects) {
            if (!(other instanceof Silverfish silverfish)) continue;
            if (alreadyHit.contains(silverfish)) continue;

            silverfish.takeDamage(damage);
            alreadyHit.add(silverfish);

            if (!isAreaEffect()) {
                remove();
                return;
            }
        }
    }

    /**
     * Markeert dit projectiel als verlopen, zodat het bij de volgende
     * collision-cyclus genegeerd wordt en uit de scene wordt verwijderd.
     */
    protected void markExpired() {
        expired = true;
    }

    /**
     * Geeft aan of dit projectiel meerdere doelwitten kan raken.
     *
     * @return true bij area-effect projectielen, false als het projectiel
     *         na de eerste treffer verdwijnt
     */
    protected boolean isAreaEffect() {
        return false;
    }
}