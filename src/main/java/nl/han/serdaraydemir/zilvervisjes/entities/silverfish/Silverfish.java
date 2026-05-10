package nl.han.serdaraydemir.zilvervisjes.entities.silverfish;

import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.Size;
import com.github.hanyaeger.api.Timer;
import com.github.hanyaeger.api.TimerContainer;
import com.github.hanyaeger.api.entities.Collided;
import com.github.hanyaeger.api.entities.Collider;
import com.github.hanyaeger.api.entities.SceneBorderCrossingWatcher;
import com.github.hanyaeger.api.entities.impl.DynamicSpriteEntity;
import com.github.hanyaeger.api.scenes.SceneBorder;
import nl.han.serdaraydemir.zilvervisjes.entities.documents.Document;
import com.github.hanyaeger.api.AnchorPoint;

import java.util.List;
import java.util.function.Consumer;

public abstract class Silverfish extends DynamicSpriteEntity
        implements SceneBorderCrossingWatcher, Collided, Collider, TimerContainer {

    private static final int TARGET_REFRESH_INTERVAL_MS = 500;
    private static final int ATTACK_INTERVAL_MS = 3000;

    private final List<Document> targets;
    private final int pointsValue;
    private int health;
    private Document currentTarget = null;
    private Document attackingTarget = null;
    private Consumer<Silverfish> deathListener = silverfish -> {};

    protected Silverfish(String spritePath, Size size, Coordinate2D location,
                         List<Document> targets, int health, int pointsValue) {
        this(spritePath, size, 1, 1, location, targets, health, pointsValue);
    }

    protected Silverfish(String spritePath, Size size, int rows, int columns,
                         Coordinate2D location, List<Document> targets,
                         int health, int pointsValue) {
        super(spritePath, location, size, rows, columns);
        setAnchorPoint(AnchorPoint.CENTER_CENTER);
        this.targets = targets;
        this.health = health;
        this.pointsValue = pointsValue;
    }

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
        if (health <= 0) {
            return;
        }
        health -= amount;
        if (health <= 0) {
            deathListener.accept(this);
            remove();
        }
    }

    public int getPointsValue() {
        return pointsValue;
    }

    public int getHealth() {
        return health;
    }

    public void setDeathListener(Consumer<Silverfish> listener) {
        if (listener != null) {
            this.deathListener = listener;
        }
    }

    protected abstract double getSpeedValue();

    protected int getAttackDamage() {
        return 1;
    }

    protected void onTargetAngleUpdated(double angle) {
        // Subclasses can override to track changes in target heading
    }

    protected List<Document> getTargets() {
        return targets;
    }

    protected void aimAt(double angle) {
        setMotion(getSpeedValue(), angle);
        setRotate(angle + 180);
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
        aimAt(angle);
    }

    private void attackTick() {
        if (attackingTarget != null && !attackingTarget.isDestroyed()) {
            attackingTarget.takeDamage(getAttackDamage());
        }
    }

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