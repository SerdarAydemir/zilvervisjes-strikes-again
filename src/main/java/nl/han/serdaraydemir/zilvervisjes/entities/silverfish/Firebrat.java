package nl.han.serdaraydemir.zilvervisjes.entities.silverfish;

import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.Size;
import nl.han.serdaraydemir.zilvervisjes.entities.documents.Document;

import java.util.List;

public class Firebrat extends Silverfish {

    private static final String SPRITE_PATH = "sprites/firebrat.png";
    private static final int SPRITE_ROWS = 1;
    private static final int SPRITE_COLUMNS = 3;

    private static final int FRAME_HEALTHY = 0;
    private static final int FRAME_DAMAGED = 1;
    private static final int FRAME_CRITICAL = 2;

    private static final double WIDTH = 26;
    private static final double HEIGHT = 52;

    private static final double SPEED = 0.8;
    private static final int HEALTH = 3;
    private static final int POINTS = 40;

    public Firebrat(Coordinate2D location, List<Document> targets) {
        super(SPRITE_PATH, new Size(WIDTH, HEIGHT), SPRITE_ROWS, SPRITE_COLUMNS,
                location, targets, HEALTH, POINTS);

        Document closest = findClosestDocumentTo(location);
        if (closest != null) {
            double angle = location.angleTo(closest.getAnchorLocation());
            aimAt(angle);
        }
    }

    @Override
    public void takeDamage(int amount) {
        super.takeDamage(amount);
        updateFrameByHealth();
    }

    @Override
    protected double getSpeedValue() {
        return SPEED;
    }

    private void updateFrameByHealth() {
        int hp = getHealth();
        if (hp >= 3) {
            setCurrentFrameIndex(FRAME_HEALTHY);
        } else if (hp == 2) {
            setCurrentFrameIndex(FRAME_DAMAGED);
        } else if (hp == 1) {
            setCurrentFrameIndex(FRAME_CRITICAL);
        }
    }
}