package nl.han.serdaraydemir.zilvervisjes.entities.weapons;

import com.github.hanyaeger.api.Coordinate2D;
import nl.han.serdaraydemir.zilvervisjes.entities.projectiles.Projectile;
import nl.han.serdaraydemir.zilvervisjes.entities.projectiles.SprayCloud;

public class InsectSpray extends Weapon {

    private static final long COOLDOWN_MS = 400;

    public InsectSpray() {
        super(COOLDOWN_MS);
    }

    @Override
    protected Projectile createProjectile(Coordinate2D location, double angle) {
        return new SprayCloud(location, angle);
    }
}
