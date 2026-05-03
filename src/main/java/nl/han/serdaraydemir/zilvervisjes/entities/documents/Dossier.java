package nl.han.serdaraydemir.zilvervisjes.entities.documents;

import com.github.hanyaeger.api.Coordinate2D;
import javafx.scene.paint.Color;

public class Dossier extends Document {
    private static final int MAX_HEALTH = 10;
    private static final double WIDTH = 30;
    private static final double HEIGHT = 40;

    public Dossier(Coordinate2D location) {
        super(location, MAX_HEALTH);
        setWidth(WIDTH);
        setHeight(HEIGHT);
        setFill(Color.web("#f4ead0"));
        setStrokeColor(Color.web("#9a8c5a"));
        setStrokeWidth(1);
    }

    @Override
    protected void updateAppearance() {
        // The file does not yet change visually in the event of damage.
        // (can change color or opacity later)
    }
}
