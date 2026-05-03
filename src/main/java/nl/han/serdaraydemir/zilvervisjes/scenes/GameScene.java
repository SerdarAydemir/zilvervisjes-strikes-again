package nl.han.serdaraydemir.zilvervisjes.scenes;
//First Step Creating Game Scene
import com.github.hanyaeger.api.scenes.DynamicScene;
//SecondStep adding Archivist
import com.github.hanyaeger.api.Coordinate2D;
import javafx.scene.paint.Color;
import nl.han.serdaraydemir.zilvervisjes.entities.Archivist;

public class GameScene extends DynamicScene {

    @Override
    public void setupScene() {
        setBackgroundColor(javafx.scene.paint.Color.web("#1a1612"));
    }

    @Override
    public void setupEntities() {
        var archivist = new Archivist(new Coordinate2D(getWidth() / 2, getHeight() / 2));
        addEntity(archivist);
    }
}
