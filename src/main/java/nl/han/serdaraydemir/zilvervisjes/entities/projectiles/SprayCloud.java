package nl.han.serdaraydemir.zilvervisjes.entities.projectiles;

import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.Timer;
import com.github.hanyaeger.api.TimerContainer;
import javafx.scene.paint.Color;

public class SprayCloud extends Projectile implements TimerContainer {

    private static final double SPEED = 6;
    private static final int DAMAGE = 1;
    private static final double SIZE = 30;
    private static final int LIFETIME_MS = 250;

    public SprayCloud(Coordinate2D location, double angle) {
        super(location, SPEED, angle, DAMAGE);
        setWidth(SIZE);
        setHeight(SIZE);
        setFill(Color.web("#a8d49d"));
        setOpacity(0.6);
    }

    @Override
    public void setupTimers() {
        addTimer(new LifetimeTimer(LIFETIME_MS));
    }

    @Override
    protected boolean isAreaEffect() {
        return true;
    }

    private class LifetimeTimer extends Timer {
        protected LifetimeTimer(int intervalInMs) {
            super(intervalInMs);
        }

        @Override
        public void onAnimationUpdate(long timestamp) {
            setVisible(false);
            setSpeed(0);
            remove();
        }
    }
}