package nl.han.serdaraydemir.zilvervisjes.entities.projectiles;

import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.Size;

public class UVBeam extends Projectile {

    private static final String SPRITE_PATH = "sprites/uv-beam.png";
    private static final double WIDTH = 300;
    private static final double HEIGHT = 24;

    private static final double SPEED = 9;
    private static final int DAMAGE = 2;

    public UVBeam(Coordinate2D location, double angle) {
        super(SPRITE_PATH, new Size(WIDTH, HEIGHT), location, SPEED, angle, DAMAGE);
        setRotate(angle - 90);
    }
}