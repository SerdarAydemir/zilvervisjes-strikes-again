package nl.han.serdaraydemir.zilvervisjes.entities;

import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.Size;
import com.github.hanyaeger.api.entities.impl.SpriteEntity;

/**
 * De achtergrondvloer van het depot. Toont een 1280x720 sprite met
 * stenen muren, archiefkasten en een houten vloer, en wordt achter
 * alle andere entiteiten getekend (hoge viewOrder = verder naar
 * achteren in de render-volgorde).
 */
public class DepotFloor extends SpriteEntity {

    private static final String SPRITE_PATH = "sprites/depot-floor.png";
    private static final int VIEW_ORDER = 100;

    /**
     * Maakt de achtergrondvloer aan, schaalt deze naar de afmetingen
     * van de scene en plaatst hem helemaal achteraan in de renderlaag.
     *
     * @param width breedte van de scene in pixels
     * @param height hoogte van de scene in pixels
     */
    public DepotFloor(double width, double height) {
        super(SPRITE_PATH, new Coordinate2D(0, 0), new Size(width, height));
        setViewOrder(VIEW_ORDER);
    }
}