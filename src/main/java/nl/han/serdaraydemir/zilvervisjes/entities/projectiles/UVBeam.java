package nl.han.serdaraydemir.zilvervisjes.entities.projectiles;

import com.github.hanyaeger.api.Coordinate2D;
import javafx.scene.paint.Color;

public class UVBeam extends Projectile {

    private static final double SPEED = 9;
    private static final int DAMAGE = 2;
    private static final double WIDTH = 8;
    private static final double HEIGHT = 32;

    public UVBeam(Coordinate2D location, double angle) {
        super(location, SPEED, angle, DAMAGE);
        setWidth(WIDTH);
        setHeight(HEIGHT);
        setFill(Color.web("#9b4dff"));
        setRotate(angle);
    }
}
