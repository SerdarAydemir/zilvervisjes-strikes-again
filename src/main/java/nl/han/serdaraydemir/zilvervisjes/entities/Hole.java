package nl.han.serdaraydemir.zilvervisjes.entities;

import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.entities.impl.RectangleEntity;
import javafx.scene.paint.Color;

public class Hole extends RectangleEntity {

    private static final double SIZE = 16;
    private static final Color FILL_COLOR = Color.web("#0a0807");
    private static final Color STROKE_COLOR = Color.web("#3a322a");

    public Hole(Coordinate2D location) {
        super(location);
        setWidth(SIZE);
        setHeight(SIZE);
        setFill(FILL_COLOR);
        setStrokeColor(STROKE_COLOR);
        setStrokeWidth(2);
    }

    public Coordinate2D getSpawnLocation() {
        Coordinate2D loc = getAnchorLocation();
        double x = loc.getX();
        double y = loc.getY();

        if (y < 50) {
            y += SIZE + 10;
        } else if (y > 600) {
            y -= 30;
        } else if (x < 50) {
            x += SIZE + 10;
        } else {
            x -= 30;
        }

        return new Coordinate2D(x, y);
    }

    public double getEntryAngle() {
        Coordinate2D loc = getAnchorLocation();
        if (loc.getY() < 50) {
            return 0;
        }
        if (loc.getY() > 600) {
            return 180;
        }
        if (loc.getX() < 50) {
            return 90;
        }
        return 270;
    }
}