package nl.han.serdaraydemir.zilvervisjes.entities.silverfish;

import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.Size;
import nl.han.serdaraydemir.zilvervisjes.entities.documents.Document;

import java.util.List;

/**
 * Ovenvisje: tank-soort die drie treffers nodig heeft om te sterven.
 * Beweegt traag, maar wisselt visueel van frame bij elke ontvangen treffer
 * (healthy, damaged, critical). Levert veertig punten op — de hoogste
 * puntenwaarde van alle vier de soorten.
 */
public class Firebrat extends Silverfish {

    private static final String SPRITE_PATH = "sprites/firebrat.png";
    private static final int SPRITE_ROWS = 1;
    private static final int SPRITE_COLUMNS = 3;

    private static final int FRAME_HEALTHY = 0;
    private static final int FRAME_DAMAGED = 1;
    private static final int FRAME_CRITICAL = 2;

    private static final double WIDTH = 26;
    private static final double HEIGHT = 52;

    private static final double SPEED = 0.8;
    private static final int HEALTH = 3;
    private static final int POINTS = 40;

    /**
     * Maakt een nieuw ovenvisje aan op de gegeven locatie en richt deze
     * direct naar het dichtstbijzijnde document.
     *
     * @param location startpositie van het ovenvisje
     * @param targets lijst van documenten die als doelwit kunnen dienen
     */
    public Firebrat(Coordinate2D location, List<Document> targets) {
        super(SPRITE_PATH, new Size(WIDTH, HEIGHT), SPRITE_ROWS, SPRITE_COLUMNS,
                location, targets, HEALTH, POINTS);

        Document closest = findClosestDocumentTo(location);
        if (closest != null) {
            double angle = location.angleTo(closest.getAnchorLocation());
            aimAt(angle);
        }
    }

    /**
     * Vermindert de health en werkt het sprite-frame bij om de toestand
     * (healthy, damaged, critical) visueel weer te geven.
     *
     * @param amount hoeveelheid schade die wordt toegebracht
     */
    @Override
    public void takeDamage(int amount) {
        super.takeDamage(amount);
        updateFrameByHealth();
    }

    @Override
    protected double getSpeedValue() {
        return SPEED;
    }

    private void updateFrameByHealth() {
        int hp = getHealth();
        if (hp >= 3) {
            setCurrentFrameIndex(FRAME_HEALTHY);
        } else if (hp == 2) {
            setCurrentFrameIndex(FRAME_DAMAGED);
        } else if (hp == 1) {
            setCurrentFrameIndex(FRAME_CRITICAL);
        }
    }
}