package nl.han.serdaraydemir.zilvervisjes.entities;

import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.entities.impl.RectangleEntity;
import javafx.scene.paint.Color;

public class Hole extends RectangleEntity {

    private static final double SIZE = 14;

    public Hole(Coordinate2D location) {
        super(location);
        setWidth(SIZE);
        setHeight(SIZE);
        setFill(Color.WHITE);
    }

    public Coordinate2D getSpawnLocation() {
        Coordinate2D loc = getAnchorLocation();
        double x = loc.getX();
        double y = loc.getY();

        if (y < 50) {
            y += SIZE + 10;
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
            return 0;       // top wall, silverfish moves down
        }
        if (loc.getX() < 50) {
            return 90;      // left wall, silverfish moves right
        }
        return 270;          // right wall, silverfish moves left
    }
}
