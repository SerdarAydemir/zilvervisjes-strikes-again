package nl.han.serdaraydemir.zilvervisjes.entities;

import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.entities.impl.RectangleEntity;
import javafx.scene.paint.Color;

/**
 * Een gat in de muur waaruit zilvervisjes het speelveld binnenkomen.
 * Wordt visueel weergegeven als een kleine donkere rechthoek en biedt
 * een hulpmethode voor de spawner om de exacte spawn-locatie net
 * binnen de muur te bepalen.
 */
public class Hole extends RectangleEntity {

    private static final double SIZE = 16;
    private static final Color FILL_COLOR = Color.web("#0a0807");
    private static final Color STROKE_COLOR = Color.web("#3a322a");

    /**
     * Maakt een nieuw gat aan op de gegeven positie tegen de muur.
     *
     * @param location vaste positie van het gat in de speelruimte
     */
    public Hole(Coordinate2D location) {
        super(location);
        setWidth(SIZE);
        setHeight(SIZE);
        setFill(FILL_COLOR);
        setStrokeColor(STROKE_COLOR);
        setStrokeWidth(2);
    }

    /**
     * Bepaalt op basis van de positie van het gat een spawn-locatie
     * die net binnen de muur ligt, zodat de zilvervis niet meteen
     * tegen de schermrand vastloopt.
     *
     * @return coördinaat net binnen het speelveld
     */
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
}