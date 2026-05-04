package nl.han.serdaraydemir.zilvervisjes.entities;

// First step; Building the character with basic shapes
import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.entities.impl.DynamicRectangleEntity;
import com.github.hanyaeger.api.userinput.KeyListener;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import com.github.hanyaeger.api.entities.SceneBorderTouchingWatcher;
import com.github.hanyaeger.api.scenes.SceneBorder;
// Second Step Adding Weapom
import com.github.hanyaeger.api.entities.YaegerEntity;
import nl.han.serdaraydemir.zilvervisjes.entities.projectiles.Projectile;
import nl.han.serdaraydemir.zilvervisjes.entities.weapons.InsectSpray;
import nl.han.serdaraydemir.zilvervisjes.entities.weapons.Weapon;
import java.util.function.Consumer;

import java.util.Set;

public class Archivist extends DynamicRectangleEntity implements KeyListener, SceneBorderTouchingWatcher {

    private static final double MOVEMENT_SPEED = 4;
    private final Weapon insectSpray;
    private final Consumer<YaegerEntity> projectileSpawner;
    private double facingAngle = 0d;

    public Archivist(Coordinate2D location, Consumer<YaegerEntity> projectileSpawner) {
        super(location);
        setWidth(30);
        setHeight(30);
        setFill(Color.web("#3a6e8a"));
        this.insectSpray = new InsectSpray();
        this.projectileSpawner = projectileSpawner;
    }

    @Override
    public void onPressedKeysChange(Set<KeyCode> pressedKeys) {
        if (pressedKeys.contains(KeyCode.UP)) {
            setMotion(MOVEMENT_SPEED, 180d);
            facingAngle = 180d;
        } else if (pressedKeys.contains(KeyCode.DOWN)) {
            setMotion(MOVEMENT_SPEED, 0d);
            facingAngle = 0d;
        } else if (pressedKeys.contains(KeyCode.LEFT)) {
            setMotion(MOVEMENT_SPEED, 270d);
            facingAngle = 270d;
        } else if (pressedKeys.contains(KeyCode.RIGHT)) {
            setMotion(MOVEMENT_SPEED, 90d);
            facingAngle = 90d;
        } else {
            setSpeed(0);
        }

        if (pressedKeys.contains(KeyCode.SPACE)) {
            fireInsectSpray();
        }
    }

    private void fireInsectSpray() {
        long now = System.nanoTime();
        Projectile projectile = insectSpray.fire(getAnchorLocation(), facingAngle, now);
        if (projectile != null) {
            projectileSpawner.accept(projectile);
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
