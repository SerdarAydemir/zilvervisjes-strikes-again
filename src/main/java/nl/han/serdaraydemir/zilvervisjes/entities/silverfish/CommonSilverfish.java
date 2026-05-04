package nl.han.serdaraydemir.zilvervisjes.entities.silverfish;

import com.github.hanyaeger.api.Coordinate2D;
import javafx.scene.paint.Color;
import nl.han.serdaraydemir.zilvervisjes.entities.documents.Document;

import java.util.List;

public class CommonSilverfish extends Silverfish {

    private static final double SPEED = 1.5;
    private static final int HEALTH = 1;
    private static final int POINTS = 10;
    private static final double WIDTH = 18;
    private static final double HEIGHT = 7;

    public CommonSilverfish(Coordinate2D location, List<Document> targets) {
        super(location, targets, HEALTH, POINTS);
        setWidth(WIDTH);
        setHeight(HEIGHT);
        setFill(Color.web("#c0c4cc"));

        // Initial movement: aim at the closest document right away
        // (the timer will keep refreshing the target every 500ms)
        Document closest = findClosestDocumentTo(location);
        if (closest != null) {
            double angle = location.angleTo(closest.getAnchorLocation());
            setMotion(SPEED, angle);
        }
    }
    // Returns the movement speed used by Silverfish.refreshTarget()
    @Override
    protected double getSpeedValue() {
        return SPEED;
    }
}