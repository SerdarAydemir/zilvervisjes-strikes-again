package nl.han.serdaraydemir.zilvervisjes.entities.projectiles;

import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.Size;

/**
 * UV-straal: het projectiel van de UVLamp. Beweegt snel, heeft een
 * lange dracht en verdwijnt direct na de eerste treffer (geen area
 * effect). Brengt twee eenheden schade per treffer toe.
 */
public class UVBeam extends Projectile {

    private static final String SPRITE_PATH = "sprites/uv-beam.png";
    private static final double WIDTH = 300;
    private static final double HEIGHT = 24;

    private static final double SPEED = 9;
    private static final int DAMAGE = 2;

    /**
     * Maakt een nieuwe UV-straal aan op de gegeven locatie en stelt
     * direct zijn motion en rotatie in. De rotatie krijgt een correctie
     * van -90 graden omdat de sprite van nature horizontaal getekend is.
     *
     * @param location startpositie van de UV-straal
     * @param angle richting waarin de straal beweegt, in graden
     */
    public UVBeam(Coordinate2D location, double angle) {
        super(SPRITE_PATH, new Size(WIDTH, HEIGHT), location, SPEED, angle, DAMAGE);
        setRotate(angle - 90);
    }
}