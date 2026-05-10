package nl.han.serdaraydemir.zilvervisjes.entities.weapons;

import com.github.hanyaeger.api.Coordinate2D;
import nl.han.serdaraydemir.zilvervisjes.entities.projectiles.Projectile;

/**
 * Abstracte basisklasse voor de wapens van de archivaris.
 * Beheert de gemeenschappelijke cooldown-logica via fire(); het concrete
 * projectiel-type wordt door subklassen bepaald via de abstracte methode
 * createProjectile() (factory method pattern).
 */
public abstract class Weapon {

    private final long cooldownMs;
    private long lastFiredTimestamp = 0;

    /**
     * Maakt een nieuw wapen met de gegeven cooldown.
     *
     * @param cooldownMs minimale tijd in milliseconden tussen twee schoten
     */
    protected Weapon(long cooldownMs) {
        this.cooldownMs = cooldownMs;
    }

    /**
     * Controleert of het wapen klaar is om te vuren op het gegeven moment.
     *
     * @param currentTimestamp huidige tijd in nanoseconden
     * @return true als de cooldown verstreken is
     */
    public boolean canFire(long currentTimestamp) {
        return (currentTimestamp - lastFiredTimestamp) >= cooldownMs * 1_000_000L;
    }

    /**
     * Probeert een schot af te vuren. Wanneer de cooldown is verstreken,
     * wordt een nieuw projectiel aangemaakt en geretourneerd; anders
     * retourneert de methode null.
     *
     * @param location startpositie van het projectiel
     * @param angle richting waarin het projectiel beweegt, in graden
     * @param currentTimestamp huidige tijd in nanoseconden
     * @return een nieuw projectiel, of null als het wapen nog in cooldown is
     */
    public Projectile fire(Coordinate2D location, double angle, long currentTimestamp) {
        if (!canFire(currentTimestamp)) {
            return null;
        }
        lastFiredTimestamp = currentTimestamp;
        return createProjectile(location, angle);
    }

    /**
     * Maakt een nieuw projectiel aan. De concrete subklasse bepaalt welk.
     * Type projectiel wordt geretourneerd (factory method pattern).
     *
     * @param location startpositie van het projectiel
     * @param angle richting waarin het projectiel beweegt, in graden
     * @return een nieuw aangemaakt projectiel
     */
    protected abstract Projectile createProjectile(Coordinate2D location, double angle);
}