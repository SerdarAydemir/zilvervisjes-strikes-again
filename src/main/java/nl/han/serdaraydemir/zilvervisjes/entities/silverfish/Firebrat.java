package nl.han.serdaraydemir.zilvervisjes.entities.silverfish;

import com.github.hanyaeger.api.Coordinate2D;
import javafx.scene.paint.Color;
import nl.han.serdaraydemir.zilvervisjes.entities.documents.Document;

import java.util.List;

public class Firebrat extends Silverfish {

    private static final double SPEED = 0.8;
    private static final int HEALTH = 3;
    private static final int POINTS = 40;
    private static final double WIDTH = 26;
    private static final double HEIGHT = 11;

    private static final Color COLOR_FULL = Color.web("#7a1f1a");
    private static final Color COLOR_DAMAGED = Color.web("#c14a1a");
    private static final Color COLOR_CRITICAL = Color.web("#f08c2a");

    public Firebrat(Coordinate2D location, List<Document> targets) {
        super(location, targets, HEALTH, POINTS);
        setWidth(WIDTH);
        setHeight(HEIGHT);
        setFill(COLOR_FULL);
        setStrokeColor(Color.web("#3a0d08"));
        setStrokeWidth(2);

        Document closest = findClosestDocumentTo(location);
        if (closest != null) {
            double angle = location.angleTo(closest.getAnchorLocation());
            setMotion(SPEED, angle);
        }
    }

    @Override
    public void takeDamage(int amount) {
        super.takeDamage(amount);
        updateColorByHealth();
    }

    @Override
    protected double getSpeedValue() {
        return SPEED;
    }

    private void updateColorByHealth() {
        int hp = getHealth();
        if (hp >= 3) {
            setFill(COLOR_FULL);
        } else if (hp == 2) {
            setFill(COLOR_DAMAGED);
        } else if (hp == 1) {
            setFill(COLOR_CRITICAL);
        }
    }
}