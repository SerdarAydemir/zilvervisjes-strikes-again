package nl.han.serdaraydemir.zilvervisjes.entities.projectiles;

import com.github.hanyaeger.api.Coordinate2D;
import javafx.scene.paint.Color;

public class SprayCloud extends Projectile {

    private static final double SPEED = 6;
    private static final int DAMAGE = 1;
    private static final double SIZE = 30;

    public SprayCloud(Coordinate2D location, double angle) {
        super(location, SPEED, angle, DAMAGE);
        setWidth(SIZE);
        setHeight(SIZE);
        setFill(Color.web("a8d49d"));
        setOpacity(0.6);
    }

    @Override
    protected boolean isAreaEffect() {
        return true;
    }
}
