package nl.han.serdaraydemir.zilvervisjes.entities.silverfish;

import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.Size;
import nl.han.serdaraydemir.zilvervisjes.entities.documents.Document;

import java.util.List;

public class CommonSilverfish extends Silverfish {

    private static final String SPRITE_PATH = "sprites/common-silverfish.png";
    private static final double WIDTH = 18;
    private static final double HEIGHT = 36;

    private static final double SPEED = 1.5;
    private static final int HEALTH = 1;
    private static final int POINTS = 10;

    public CommonSilverfish(Coordinate2D location, List<Document> targets) {
        super(SPRITE_PATH, new Size(WIDTH, HEIGHT), location, targets, HEALTH, POINTS);

        Document closest = findClosestDocumentTo(location);
        if (closest != null) {
            double angle = location.angleTo(closest.getAnchorLocation());
            aimAt(angle);
        }
    }

    @Override
    protected double getSpeedValue() {
        return SPEED;
    }
}