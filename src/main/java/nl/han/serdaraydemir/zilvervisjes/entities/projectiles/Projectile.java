package nl.han.serdaraydemir.zilvervisjes.entities.projectiles;

import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.entities.Collided;
import com.github.hanyaeger.api.entities.Collider;
import com.github.hanyaeger.api.entities.SceneBorderCrossingWatcher;
import com.github.hanyaeger.api.entities.impl.DynamicRectangleEntity;
import com.github.hanyaeger.api.scenes.SceneBorder;
import nl.han.serdaraydemir.zilvervisjes.entities.silverfish.Silverfish;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class Projectile extends DynamicRectangleEntity implements SceneBorderCrossingWatcher, Collided {

    private final int damage;
    private final Set<Silverfish> alreadyHit = new HashSet<>();

    protected Projectile(Coordinate2D location, double speed, double angle, int damage) {
        super(location);
        this.damage = damage;
        setMotion(speed, angle);
    }

    public int getDamage() {
        return damage;
    }

    @Override
    public void notifyBoundaryCrossing(SceneBorder border) {
        remove();
    }

    @Override
    public void onCollision(List<Collider> collidingObjects) {
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

    protected boolean isAreaEffect() {
        return false;
    }
}