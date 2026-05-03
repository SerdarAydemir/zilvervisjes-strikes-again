package nl.han.serdaraydemir.zilvervisjes.entities;

// First step; Building the character with basic shapes
import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.entities.impl.DynamicRectangleEntity;
import com.github.hanyaeger.api.userinput.KeyListener;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import com.github.hanyaeger.api.entities.SceneBorderTouchingWatcher;
import com.github.hanyaeger.api.scenes.SceneBorder;

import java.util.Set;

public class Archivist extends DynamicRectangleEntity implements KeyListener, SceneBorderTouchingWatcher {

    private static final double MOVEMENT_SPEED = 4;

    public Archivist(Coordinate2D location) {
        super(location);
        setWidth(30);
        setHeight(30);
        setFill(Color.web("3a6e8a"));
    }

    @Override
    public void onPressedKeysChange(Set<KeyCode> pressedKeys) {
        if (pressedKeys.contains(KeyCode.UP)) {
            setMotion(MOVEMENT_SPEED, 180d);}
        else if (pressedKeys.contains(KeyCode.DOWN)) {
            setMotion(MOVEMENT_SPEED, 0d);}
        else if (pressedKeys.contains(KeyCode.LEFT)) {
            setMotion(MOVEMENT_SPEED, 270d);}
        else if (pressedKeys.contains(KeyCode.RIGHT)) {
            setMotion(MOVEMENT_SPEED, 90d);}
        else {
            setSpeed(0);
        }
    }

    @Override
    public void notifyBoundaryTouching(SceneBorder border) {
        setSpeed(0);
        switch (border) {
            case TOP -> setAnchorLocationY(1);
            case BOTTOM -> setAnchorLocationY(getSceneHeight() - getHeight() - 1);
            case LEFT -> setAnchorLocationX(1);
            case RIGHT -> setAnchorLocationX(getSceneWidth() - getWidth() - 1);
        }
    }
}
