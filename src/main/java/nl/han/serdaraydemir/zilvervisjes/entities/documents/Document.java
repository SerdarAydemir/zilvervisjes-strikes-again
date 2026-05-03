package nl.han.serdaraydemir.zilvervisjes.entities.documents;

import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.entities.impl.RectangleEntity;
import javafx.scene.paint.Color;

public abstract class Document extends RectangleEntity {

    private int health;
    private final int maxHealth;

    protected Document(Coordinate2D location, int maxHealth) {
        super(location);
        this.maxHealth = maxHealth;
        this.health = maxHealth;
    }

    public void takeDamage(int amount) {
        health -= amount;
        if (health <= 0) {
            health = 0;
            remove();
        } else {
            updateAppearance();
        }
    }


    public int getHealth() {
        return health;
    }

    public boolean isDestroyed() {
        return health <= 0;
    }

    protected abstract void updateAppearance();

}