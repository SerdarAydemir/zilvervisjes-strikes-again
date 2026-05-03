package nl.han.serdaraydemir.zilvervisjes.entities.documents;

import com.github.hanyaeger.api.Coordinate2D;
import javafx.scene.paint.Color;

public class Book extends Document{

    private static final int MAX_HEALTH = 20;
    private static final double WIDTH = 50;
    private static final double HEIGHT = 35;

    private boolean damaged = false;

    public Book(Coordinate2D location) {
        super(location, MAX_HEALTH);
        setWidth(WIDTH);
        setHeight(HEIGHT);
        setFill(Color.web("5d2a1a"));
        setFill(Color.web("3a1810"));
        setStrokeWidth(1);

    }

    @Override
    protected void updateAppearance() {
        if (!damaged && getHealth() <= MAX_HEALTH / 2) {
            damaged = true;
            setFill(Color.web("8b4a3a"));
        }
    }
}
