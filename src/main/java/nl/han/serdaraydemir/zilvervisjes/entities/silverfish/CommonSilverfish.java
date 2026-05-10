package nl.han.serdaraydemir.zilvervisjes.entities.silverfish;

import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.Size;
import nl.han.serdaraydemir.zilvervisjes.entities.documents.Document;

import java.util.List;

/**
 * Gewone zilvervis: de referentie-soort in het spel. Beweegt met
 * gemiddelde snelheid in een rechte lijn naar het dichtstbijzijnde
 * document en sterft bij één treffer.
 */
public class CommonSilverfish extends Silverfish {

    private static final String SPRITE_PATH = "sprites/common-silverfish.png";
    private static final double WIDTH = 18;
    private static final double HEIGHT = 36;

    private static final double SPEED = 1.5;
    private static final int HEALTH = 1;
    private static final int POINTS = 10;

    /**
     * Maakt een nieuwe gewone zilvervis aan op de gegeven locatie en
     * richt deze direct naar het dichtstbijzijnde document.
     *
     * @param location startpositie van de zilvervis
     * @param targets lijst van documenten die als doelwit kunnen dienen
     */
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