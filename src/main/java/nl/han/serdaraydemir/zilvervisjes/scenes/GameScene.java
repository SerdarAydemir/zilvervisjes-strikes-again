package nl.han.serdaraydemir.zilvervisjes.scenes;
//First Step Creating Game Scene
import com.github.hanyaeger.api.scenes.DynamicScene;
//SecondStep adding Archivist
import com.github.hanyaeger.api.Coordinate2D;
import javafx.scene.paint.Color;
import nl.han.serdaraydemir.zilvervisjes.entities.Archivist;
// Third step adding Dossier and Book
import nl.han.serdaraydemir.zilvervisjes.entities.documents.Book;
import nl.han.serdaraydemir.zilvervisjes.entities.documents.Dossier;
// Fourth step adding Holes
import nl.han.serdaraydemir.zilvervisjes.entities.Hole;


public class GameScene extends DynamicScene {

    @Override
    public void setupScene() {
        setBackgroundColor(javafx.scene.paint.Color.web("#1a1612"));
    }

    @Override
    public void setupEntities() {
        // Holes 4 on the top wall, 1 on each side wall
        addEntity(new Hole(new Coordinate2D(250, 20)));
        addEntity(new Hole(new Coordinate2D(500, 20)));
        addEntity(new Hole(new Coordinate2D(780, 20)));
        addEntity(new Hole(new Coordinate2D(1030, 20)));
        addEntity(new Hole(new Coordinate2D(20, 360)));
        addEntity(new Hole(new Coordinate2D(1240, 360)));

        //Documents: 2 Dossiers and 2 Books
        addEntity(new Dossier(new Coordinate2D(200,200)));
        addEntity(new Dossier(new Coordinate2D(1050,200)));
        addEntity(new Book(new Coordinate2D(300,500)));
        addEntity(new Book(new Coordinate2D(900,500)));

        //Archivist in the center
        var archivist = new Archivist(new Coordinate2D(getWidth() / 2, getHeight() / 2));
        addEntity(archivist);
    }
}
