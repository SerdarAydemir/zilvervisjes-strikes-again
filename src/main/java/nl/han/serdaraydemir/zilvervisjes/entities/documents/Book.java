package nl.han.serdaraydemir.zilvervisjes.entities.documents;

import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.Size;

/**
 * Boek: gebonden naslagwerk met grote hitbox en hoge health (20).
 * Verdraagt aanzienlijk meer schade dan een dossier en wisselt visueel
 * van frame (pristine, damaged, ruined) op basis van de resterende health.
 */
public class Book extends Document {

    private static final String SPRITE_PATH = "sprites/book.png";
    private static final int SPRITE_ROWS = 1;
    private static final int SPRITE_COLUMNS = 3;

    private static final int FRAME_PRISTINE = 0;
    private static final int FRAME_DAMAGED = 1;
    private static final int FRAME_RUINED = 2;

    private static final int MAX_HEALTH = 20;
    private static final double WIDTH = 60;
    private static final double HEIGHT = 80;

    /**
     * Maakt een nieuw boek aan op de gegeven vaste positie.
     *
     * @param location positie van het boek in de speelruimte
     */
    public Book(Coordinate2D location) {
        super(SPRITE_PATH, new Size(WIDTH, HEIGHT), SPRITE_ROWS, SPRITE_COLUMNS,
                location, MAX_HEALTH);
    }

    @Override
    protected void updateAppearance() {
        int hp = getHealth();
        if (hp >= 14) {
            setCurrentFrameIndex(FRAME_PRISTINE);
        } else if (hp >= 7) {
            setCurrentFrameIndex(FRAME_DAMAGED);
        } else {
            setCurrentFrameIndex(FRAME_RUINED);
        }
    }
}