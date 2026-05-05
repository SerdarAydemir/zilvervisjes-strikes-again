package nl.han.serdaraydemir.zilvervisjes.entities.silverfish;
// Step 1 Creating Silverfish
import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.entities.SceneBorderCrossingWatcher;
import com.github.hanyaeger.api.entities.impl.DynamicRectangleEntity;
import com.github.hanyaeger.api.scenes.SceneBorder;
import nl.han.serdaraydemir.zilvervisjes.entities.documents.Document;
//Step 2 Adding Collided
import com.github.hanyaeger.api.entities.Collided;
import com.github.hanyaeger.api.entities.Collider;
// Step 3 Adding Timer
import com.github.hanyaeger.api.Timer;
import com.github.hanyaeger.api.TimerContainer;

import java.util.List;

public abstract class Silverfish extends DynamicRectangleEntity
        implements SceneBorderCrossingWatcher, Collided, Collider, TimerContainer {

    // 1. STATIC CONSTANTS
    private static final int TARGET_REFRESH_INTERVAL_MS = 500;
    private static final int ATTACK_INTERVAL_MS = 3000;

    // 2. INSTANCE FIELDS
    private final List<Document> targets;
    private final int pointsValue;
    private int health;
    private Document currentTarget = null;
    private Document attackingTarget = null;

    // 3. CONSTRUCTOR
    protected Silverfish(Coordinate2D location, List<Document> targets, int health, int pointsValue) {
        super(location);
        this.targets = targets;
        this.health = health;
        this.pointsValue = pointsValue;
    }

    // 4. PUBLIC METHODS — API: For Yaeger and other classes
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

    public void takeDamage(int amount) {
        health -= amount;
        if (health <= 0) {
            remove();
        }
    }

    public int getPointsValue() {
        return pointsValue;
    }

    public int getHealth() {
        return health;
    }

    // 5. PROTECTED METHODS — For subclasses
    protected abstract double getSpeedValue();

    protected int getAttackDamage() {
        return 1;
    }

    protected void onTargetAngleUpdated(double angle) {

    }

    protected List<Document> getTargets() {
        return targets;
    }

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

    // 6. PRIVATE METHODS — The details that only this class knows
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
        setMotion(getSpeedValue(), angle);
    }

    private void attackTick() {
        if (attackingTarget != null && !attackingTarget.isDestroyed()) {
            attackingTarget.takeDamage(getAttackDamage());
        }
    }

    // 7. INNER CLASSES —
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

