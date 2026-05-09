package nl.han.serdaraydemir.zilvervisjes.entities.menu;

import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.entities.SceneBorderTouchingWatcher;
import com.github.hanyaeger.api.entities.impl.DynamicRectangleEntity;
import com.github.hanyaeger.api.scenes.SceneBorder;
import javafx.scene.paint.Color;

public class MenuSilverfish extends DynamicRectangleEntity implements SceneBorderTouchingWatcher {

    private static final double WIDTH = 18;
    private static final double HEIGHT = 7;
    private static final Color COLOR = Color.web("#3a3530");
    private static final Color STROKE_COLOR = Color.web("#1f1c19");

    private final double speed;

    public MenuSilverfish(Coordinate2D location, double speed, double angle) {
        super(location);
        this.speed = speed;
        setWidth(WIDTH);
        setHeight(HEIGHT);
        setFill(COLOR);
        setStrokeColor(STROKE_COLOR);
        setStrokeWidth(1);
        setOpacity(0.45);
        setMotion(speed, angle);
    }

    @Override
    public void notifyBoundaryTouching(SceneBorder border) {
        switch (border) {
            case TOP -> setMotion(speed, 180 - getDirection() + 360);
            case BOTTOM -> setMotion(speed, 180 - getDirection() + 360);
            case LEFT -> setMotion(speed, 360 - getDirection());
            case RIGHT -> setMotion(speed, 360 - getDirection());
        }
        ensureInsideBounds(border);
    }

    private void ensureInsideBounds(SceneBorder border) {
        Coordinate2D loc = getAnchorLocation();
        double x = loc.getX();
        double y = loc.getY();
        switch (border) {
            case TOP -> y = 1;
            case BOTTOM -> y = getSceneHeight() - HEIGHT - 1;
            case LEFT -> x = 1;
            case RIGHT -> x = getSceneWidth() - WIDTH - 1;
        }
        setAnchorLocation(new Coordinate2D(x, y));
    }
}