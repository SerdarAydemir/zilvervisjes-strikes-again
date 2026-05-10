package nl.han.serdaraydemir.zilvervisjes.entities.menu;

import com.github.hanyaeger.api.AnchorPoint;
import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.Size;
import com.github.hanyaeger.api.entities.SceneBorderTouchingWatcher;
import com.github.hanyaeger.api.entities.impl.DynamicSpriteEntity;
import com.github.hanyaeger.api.scenes.SceneBorder;

public class MenuSilverfish extends DynamicSpriteEntity implements SceneBorderTouchingWatcher {

    private static final String SPRITE_PATH = "sprites/paperfish.png";
    private static final double WIDTH = 33;
    private static final double HEIGHT = 66;

    private final double speed;

    public MenuSilverfish(Coordinate2D location, double speed, double angle) {
        super(SPRITE_PATH, location, new Size(WIDTH, HEIGHT));
        this.speed = speed;
        setAnchorPoint(AnchorPoint.CENTER_CENTER);
        setOpacity(0.6);
        aimAt(angle);
    }

    @Override
    public void notifyBoundaryTouching(SceneBorder border) {
        ensureInsideBounds(border);
        aimAt(pickAngleAwayFrom(border));
    }

    private double pickAngleAwayFrom(SceneBorder border) {
        double base = switch (border) {
            case TOP -> 0;
            case BOTTOM -> 180;
            case LEFT -> 90;
            case RIGHT -> 270;
        };
        double spread = (Math.random() - 0.5) * 90;
        return (base + spread + 360) % 360;
    }

    private void aimAt(double angle) {
        setMotion(speed, angle);
        setRotate(angle + 180);
    }

    private void ensureInsideBounds(SceneBorder border) {
        Coordinate2D loc = getAnchorLocation();
        double x = loc.getX();
        double y = loc.getY();
        switch (border) {
            case TOP -> y = HEIGHT;
            case BOTTOM -> y = getSceneHeight() - HEIGHT;
            case LEFT -> x = WIDTH;
            case RIGHT -> x = getSceneWidth() - WIDTH;
        }
        setAnchorLocation(new Coordinate2D(x, y));
    }
}