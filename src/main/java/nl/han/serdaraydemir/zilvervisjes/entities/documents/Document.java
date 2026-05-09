package nl.han.serdaraydemir.zilvervisjes.entities.documents;

import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.entities.Collider;
import com.github.hanyaeger.api.entities.impl.RectangleEntity;

import java.util.function.Consumer;

public abstract class Document extends RectangleEntity implements Collider {

    private final int maxHealth;
    private int health;
    private Consumer<Document> destructionListener = doc -> {};

    protected Document(Coordinate2D location, int maxHealth) {
        super(location);
        this.maxHealth = maxHealth;
        this.health = maxHealth;
    }

    public void takeDamage(int amount) {
        if (health <= 0) {
            return;
        }
        health -= amount;
        if (health <= 0) {
            health = 0;
            destructionListener.accept(this);
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

    public void setDestructionListener(Consumer<Document> listener) {
        if (listener != null) {
            this.destructionListener = listener;
        }
    }

    protected abstract void updateAppearance();
}