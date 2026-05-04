package nl.han.serdaraydemir.zilvervisjes.entities.projectiles;

// Step 1 Creating Projectile
import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.entities.SceneBorderCrossingWatcher;
import com.github.hanyaeger.api.entities.impl.DynamicRectangleEntity;
import com.github.hanyaeger.api.scenes.SceneBorder;
// Step 2 Adding Collided
import com.github.hanyaeger.api.entities.Collided;
import com.github.hanyaeger.api.entities.Collider;
import nl.han.serdaraydemir.zilvervisjes.entities.silverfish.Silverfish;
import java.util.List;

public abstract class Projectile extends DynamicRectangleEntity implements SceneBorderCrossingWatcher, Collided {

    private final int damage;

    protected Projectile(Coordinate2D location, double speed, double angle, int damage ) {
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
            if (other instanceof Silverfish silverfish) {
                silverfish.takeDamage(damage);
                return;
            }
        }
    }
}
