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
        return new Coordinate2D(getAnchorLocation().getX(), getAnchorLocation().getY());
    }
}
