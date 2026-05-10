package nl.han.serdaraydemir.zilvervisjes.entities.documents;

import com.github.hanyaeger.api.AnchorPoint;
import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.Size;
import com.github.hanyaeger.api.entities.Collider;
import com.github.hanyaeger.api.entities.impl.SpriteEntity;

import java.util.function.Consumer;

/**
 * Abstracte basisklasse voor de te beschermen documenten in het archief.
 * Beheert de gemeenschappelijke health-logica en delegeert de
 * toestandsafhankelijke sprite-wisseling aan subklassen via de abstracte
 * methode updateAppearance().
 */
public abstract class Document extends SpriteEntity implements Collider {

    private final int maxHealth;
    private int health;
    private Consumer<Document> destructionListener = doc -> {};

    /**
     * Maakt een nieuw document aan met een multi-frame sprite voor de
     * toestandsafhankelijke weergave (pristine, damaged, ruined).
     *
     * @param spritePath pad naar het sprite-bestand
     * @param size grootte van de sprite
     * @param rows aantal rijen in het sprite-sheet
     * @param columns aantal kolommen in het sprite-sheet
     * @param location vaste positie van het document in de speelruimte
     * @param maxHealth maximale gezondheidswaarde
     */
    protected Document(String spritePath, Size size, int rows, int columns,
                       Coordinate2D location, int maxHealth) {
        super(spritePath, location, size, rows, columns);
        setAnchorPoint(AnchorPoint.CENTER_CENTER);
        this.maxHealth = maxHealth;
        this.health = maxHealth;
    }

    /**
     * Vermindert de health van dit document met de meegegeven schade.
     * Wanneer de health op nul of lager komt, wordt de destructionListener
     * aangeroepen en wordt het document uit de scene verwijderd. Anders
     * wordt updateAppearance() aangeroepen om het sprite-frame bij te
     * werken.
     *
     * @param amount hoeveelheid schade die wordt toegebracht
     */
    public void takeDamage(int amount) {
        if (health <= 0) {
            return;
        }
        health -= amount;
        if (health <= 0) {
            health = 0;
            destructionListener.accept(this);
            remove();
        } else {
            updateAppearance();
        }
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
     * Geeft de maximale health-waarde terug zoals ingesteld bij creatie.
     *
     * @return maximale health
     */
    public int getMaxHealth() {
        return maxHealth;
    }

    /**
     * Geeft aan of dit document volledig vernietigd is.
     *
     * @return true als de health op nul of lager staat
     */
    public boolean isDestroyed() {
        return health <= 0;
    }

    /**
     * Stelt de listener in die wordt aangeroepen zodra dit document
     * volledig vernietigd is. Wordt gebruikt door GameScene om de
     * game-over-conditie te detecteren.
     *
     * @param listener callback die het vernietigde document ontvangt
     */
    public void setDestructionListener(Consumer<Document> listener) {
        if (listener != null) {
            this.destructionListener = listener;
        }
    }

    /**
     * Werkt het sprite-frame bij op basis van de huidige health.
     * Subklassen leveren hun eigen logica (welk frame bij welke
     * health-grenswaarde getoond wordt).
     */
    protected abstract void updateAppearance();
}