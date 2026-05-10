package nl.han.serdaraydemir.zilvervisjes.entities.projectiles;

import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.Size;
import com.github.hanyaeger.api.Timer;
import com.github.hanyaeger.api.TimerContainer;

/**
 * Spuitwolk: het projectiel van de InsectSpray. Beweegt langzaam,
 * heeft een korte levensduur (250 ms) en kan meerdere zilvervisjes
 * tegelijk raken die zich op zijn pad bevinden. Brengt één eenheid
 * schade per treffer toe.
 */
public class SprayCloud extends Projectile implements TimerContainer {

    private static final String SPRITE_PATH = "sprites/spray-cloud.png";
    private static final double SIZE = 50;

    private static final double SPEED = 6;
    private static final int DAMAGE = 1;
    private static final int LIFETIME_MS = 250;

    /**
     * Maakt een nieuwe spuitwolk aan op de gegeven locatie en stelt
     * direct zijn motion in.
     *
     * @param location startpositie van de spuitwolk
     * @param angle richting waarin de wolk beweegt, in graden
     */
    public SprayCloud(Coordinate2D location, double angle) {
        super(SPRITE_PATH, new Size(SIZE, SIZE), location, SPEED, angle, DAMAGE);
        setOpacity(0.85);
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
            markExpired();
            setVisible(false);
            setSpeed(0);
            remove();
        }
    }
}