package nl.han.serdaraydemir.zilvervisjes.entities.weapons;

import com.github.hanyaeger.api.Coordinate2D;
import nl.han.serdaraydemir.zilvervisjes.entities.projectiles.Projectile;

public abstract class Weapon {

    private final long cooldownMs;
    private long lastFiredTimestamp = 0;

    protected Weapon(long cooldownMs) {
        this.cooldownMs = cooldownMs;
    }

    public boolean canFire(long currentTimestamp) {
        return (currentTimestamp - lastFiredTimestamp) >= cooldownMs * 1_000_000L;
    }

    public Projectile fire(Coordinate2D location, double angle, long currentTimestamp) {
        if (!canFire(currentTimestamp)) {
            return null;
        }
        lastFiredTimestamp = currentTimestamp;
        return createProjectile(location, angle);

    }

    protected abstract Projectile createProjectile(Coordinate2D location, double angle);
}
