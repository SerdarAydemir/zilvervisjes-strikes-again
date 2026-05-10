package nl.han.serdaraydemir.zilvervisjes.entities;

import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.Size;
import com.github.hanyaeger.api.entities.SceneBorderTouchingWatcher;
import com.github.hanyaeger.api.entities.YaegerEntity;
import com.github.hanyaeger.api.entities.impl.DynamicSpriteEntity;
import com.github.hanyaeger.api.scenes.SceneBorder;
import com.github.hanyaeger.api.userinput.KeyListener;
import javafx.scene.input.KeyCode;
import nl.han.serdaraydemir.zilvervisjes.entities.projectiles.Projectile;
import nl.han.serdaraydemir.zilvervisjes.entities.weapons.InsectSpray;
import nl.han.serdaraydemir.zilvervisjes.entities.weapons.UVLamp;
import nl.han.serdaraydemir.zilvervisjes.entities.weapons.Weapon;

import java.util.Set;
import java.util.function.Consumer;

public class Archivist extends DynamicSpriteEntity implements KeyListener, SceneBorderTouchingWatcher {

    private static final String SPRITE_PATH = "sprites/archivist.png";
    private static final double SPRITE_WIDTH = 40;
    private static final double SPRITE_HEIGHT = 40;
    private static final double MOVEMENT_SPEED = 4;

    private final Weapon insectSpray;
    private final UVLamp uvLamp;
    private final Consumer<YaegerEntity> projectileSpawner;
    private double facingAngle = 0d;

    public Archivist(Coordinate2D location, Consumer<YaegerEntity> projectileSpawner) {
        super(SPRITE_PATH, location, new Size(SPRITE_WIDTH, SPRITE_HEIGHT));
        this.insectSpray = new InsectSpray();
        this.uvLamp = new UVLamp();
        this.projectileSpawner = projectileSpawner;
    }

    @Override
    public void onPressedKeysChange(Set<KeyCode> pressedKeys) {
        if (pressedKeys.contains(KeyCode.UP)) {
            setMotion(MOVEMENT_SPEED, 180d);
            facingAngle = 180d;
            setRotate(180);
        } else if (pressedKeys.contains(KeyCode.DOWN)) {
            setMotion(MOVEMENT_SPEED, 0d);
            facingAngle = 0d;
            setRotate(0);
        } else if (pressedKeys.contains(KeyCode.LEFT)) {
            setMotion(MOVEMENT_SPEED, 270d);
            facingAngle = 270d;
            setRotate(270);
        } else if (pressedKeys.contains(KeyCode.RIGHT)) {
            setMotion(MOVEMENT_SPEED, 90d);
            facingAngle = 90d;
            setRotate(90);
        } else {
            setSpeed(0);
        }

        if (pressedKeys.contains(KeyCode.SPACE)) {
            fireInsectSpray();
        }
        if (pressedKeys.contains(KeyCode.X)) {
            fireUVLamp();
        }
    }

    @Override
    public void notifyBoundaryTouching(SceneBorder border) {
        setSpeed(0);
        switch (border) {
            case TOP -> setAnchorLocationY(1);
            case BOTTOM -> setAnchorLocationY(getSceneHeight() - SPRITE_HEIGHT - 1);
            case LEFT -> setAnchorLocationX(1);
            case RIGHT -> setAnchorLocationX(getSceneWidth() - SPRITE_WIDTH - 1);
        }
    }

    private void fireInsectSpray() {
        long now = System.nanoTime();
        Projectile projectile = insectSpray.fire(getAnchorLocation(), facingAngle, now);
        if (projectile != null) {
            projectileSpawner.accept(projectile);
        }
    }

    private void fireUVLamp() {
        long now = System.nanoTime();
        Projectile beam = uvLamp.fire(getAnchorLocation(), facingAngle, now);
        if (beam != null) {
            projectileSpawner.accept(beam);
        }
    }
}