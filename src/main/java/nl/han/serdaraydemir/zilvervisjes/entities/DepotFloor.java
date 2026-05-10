package nl.han.serdaraydemir.zilvervisjes.entities;

import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.Size;
import com.github.hanyaeger.api.entities.impl.SpriteEntity;

public class DepotFloor extends SpriteEntity {

    private static final String SPRITE_PATH = "sprites/depot-floor.png";
    private static final int VIEW_ORDER = 100;

    public DepotFloor(double width, double height) {
        super(SPRITE_PATH, new Coordinate2D(0, 0), new Size(width, height));
        setViewOrder(VIEW_ORDER);
    }
}