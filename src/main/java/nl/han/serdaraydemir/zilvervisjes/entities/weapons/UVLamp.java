package nl.han.serdaraydemir.zilvervisjes.entities.weapons;

import com.github.hanyaeger.api.Coordinate2D;
import nl.han.serdaraydemir.zilvervisjes.entities.projectiles.Projectile;
import nl.han.serdaraydemir.zilvervisjes.entities.projectiles.UVBeam;

/**
 * UV-lamp: het tweede wapen van de archivaris met lange cooldown,
 * smalle werking en hogere schade per treffer. Vuurt een UVBeam af
 * die direct verdwijnt na de eerste treffer.
 */
public class UVLamp extends Weapon {

    private static final long COOLDOWN_MS = 1500;

    /**
     * Maakt een nieuwe UV-lamp aan met een vaste cooldown van
     * 1500 milliseconden.
     */
    public UVLamp() {
        super(COOLDOWN_MS);
    }

    @Override
    protected Projectile createProjectile(Coordinate2D location, double angle) {
        return new UVBeam(location, angle);
    }
}