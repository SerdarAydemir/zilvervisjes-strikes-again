package nl.han.serdaraydemir.zilvervisjes.entities.weapons;

import com.github.hanyaeger.api.Coordinate2D;
import nl.han.serdaraydemir.zilvervisjes.entities.projectiles.Projectile;
import nl.han.serdaraydemir.zilvervisjes.entities.projectiles.SprayCloud;

/**
 * Insectenspray: het basiswapen van de archivaris met korte cooldown
 * en brede werking. Vuurt een SprayCloud af die meerdere zilvervisjes
 * tegelijk kan raken.
 */
public class InsectSpray extends Weapon {

    private static final long COOLDOWN_MS = 400;

    /**
     * Maakt een nieuwe insectenspray aan met een vaste cooldown van
     * 400 milliseconden.
     */
    public InsectSpray() {
        super(COOLDOWN_MS);
    }

    @Override
    protected Projectile createProjectile(Coordinate2D location, double angle) {
        return new SprayCloud(location, angle);
    }
}