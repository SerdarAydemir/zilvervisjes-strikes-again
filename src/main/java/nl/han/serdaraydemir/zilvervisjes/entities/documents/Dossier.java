package nl.han.serdaraydemir.zilvervisjes.entities.documents;

import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.Size;

public class Dossier extends Document {

    private static final String SPRITE_PATH = "sprites/dossier.png";
    private static final int SPRITE_ROWS = 1;
    private static final int SPRITE_COLUMNS = 3;

    private static final int FRAME_PRISTINE = 0;
    private static final int FRAME_DAMAGED = 1;
    private static final int FRAME_RUINED = 2;

    private static final int MAX_HEALTH = 10;
    private static final double WIDTH = 36;
    private static final double HEIGHT = 48;

    public Dossier(Coordinate2D location) {
        super(SPRITE_PATH, new Size(WIDTH, HEIGHT), SPRITE_ROWS, SPRITE_COLUMNS,
                location, MAX_HEALTH);
    }

    @Override
    protected void updateAppearance() {
        int hp = getHealth();
        if (hp >= 7) {
            setCurrentFrameIndex(FRAME_PRISTINE);
        } else if (hp >= 3) {
            setCurrentFrameIndex(FRAME_DAMAGED);
        } else {
            setCurrentFrameIndex(FRAME_RUINED);
        }
    }
}