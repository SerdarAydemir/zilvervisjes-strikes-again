package nl.han.serdaraydemir.zilvervisjes.entities.silverfish;

import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.Timer;
import javafx.scene.paint.Color;
import nl.han.serdaraydemir.zilvervisjes.entities.documents.Document;

import java.util.List;

public class StripedSilverfish extends Silverfish {

    private static final double SPEED = 1.2;
    private static final int HEALTH = 1;
    private static final int POINTS = 15;
    private static final double WIDTH = 20;
    private static final double HEIGHT = 8;

    private static final int ZIGZAG_INTERVAL_MS = 350;
    private static final double ZIGZAG_OFFSET_DEGREES = 18;

    private double targetAngle = 0;
    private boolean zigzagFlip = false;

    public StripedSilverfish(Coordinate2D location, List<Document> targets) {
        super(location, targets, HEALTH, POINTS);
        setWidth(WIDTH);
        setHeight(HEIGHT);
        setFill(Color.web("#5a5d6e"));
        setStrokeColor(Color.web("#2b2d38"));
        setStrokeWidth(2);

        Document closest = findClosestDocumentTo(location);
        if (closest != null) {
            targetAngle = location.angleTo(closest.getAnchorLocation());
            setMotion(SPEED, targetAngle);
        }
    }

    @Override
    public void setupTimers() {
        super.setupTimers();
        addTimer(new ZigzagTimer(ZIGZAG_INTERVAL_MS));
    }

    @Override
    protected double getSpeedValue() {
        return SPEED;
    }

    @Override
    protected void onTargetAngleUpdated(double angle) {
        targetAngle = angle;
    }

    private void zigzagTick() {
        if (getSpeed() == 0) {
            return;
        }
        zigzagFlip = !zigzagFlip;
        double offset = zigzagFlip ? ZIGZAG_OFFSET_DEGREES : -ZIGZAG_OFFSET_DEGREES;
        setDirection(targetAngle + offset);
    }

    private class ZigzagTimer extends Timer {
        protected ZigzagTimer(int intervalInMs) {
            super(intervalInMs);
        }

        @Override
        public void onAnimationUpdate(long timestamp) {
            zigzagTick();
        }
    }
}