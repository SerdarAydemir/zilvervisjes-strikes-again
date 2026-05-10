package nl.han.serdaraydemir.zilvervisjes.entities.silverfish;

import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.Size;
import com.github.hanyaeger.api.Timer;
import nl.han.serdaraydemir.zilvervisjes.entities.documents.Document;

import java.util.List;

/**
 * Gestreepte zilvervis: beweegt in een zigzagpatroon naar zijn doelwit,
 * waardoor hij lastiger te raken is dan de gewone zilvervis. Sterft net
 * als de gewone zilvervis bij één treffer en levert vijftien punten op.
 */
public class StripedSilverfish extends Silverfish {

    private static final String SPRITE_PATH = "sprites/striped-silverfish.png";
    private static final double WIDTH = 20;
    private static final double HEIGHT = 40;

    private static final double SPEED = 1.2;
    private static final int HEALTH = 1;
    private static final int POINTS = 15;

    private static final int ZIGZAG_INTERVAL_MS = 350;
    private static final double ZIGZAG_OFFSET_DEGREES = 18;

    private double targetAngle = 0;
    private boolean zigzagFlip = false;

    /**
     * Maakt een nieuwe gestreepte zilvervis aan op de gegeven locatie en
     * richt deze direct naar het dichtstbijzijnde document.
     *
     * @param location startpositie van de zilvervis
     * @param targets lijst van documenten die als doelwit kunnen dienen
     */
    public StripedSilverfish(Coordinate2D location, List<Document> targets) {
        super(SPRITE_PATH, new Size(WIDTH, HEIGHT), location, targets, HEALTH, POINTS);

        Document closest = findClosestDocumentTo(location);
        if (closest != null) {
            targetAngle = location.angleTo(closest.getAnchorLocation());
            aimAt(targetAngle);
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
        double newAngle = targetAngle + offset;
        setDirection(newAngle);
        setRotate(newAngle + 180);
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