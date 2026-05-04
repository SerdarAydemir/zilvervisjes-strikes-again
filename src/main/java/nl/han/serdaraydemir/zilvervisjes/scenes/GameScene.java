package nl.han.serdaraydemir.zilvervisjes.scenes;

import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.scenes.DynamicScene;
import javafx.scene.paint.Color;
import nl.han.serdaraydemir.zilvervisjes.entities.Archivist;
import nl.han.serdaraydemir.zilvervisjes.entities.Hole;
import nl.han.serdaraydemir.zilvervisjes.entities.documents.Book;
import nl.han.serdaraydemir.zilvervisjes.entities.documents.Document;
import nl.han.serdaraydemir.zilvervisjes.entities.documents.Dossier;
import nl.han.serdaraydemir.zilvervisjes.entities.silverfish.CommonSilverfish;

import java.util.ArrayList;
import java.util.List;

public class GameScene extends DynamicScene {

    @Override
    public void setupScene() {
        setBackgroundColor(Color.web("#1a1612"));
    }

    @Override
    public void setupEntities() {
        // Holes: 4 on the top wall, 1 on each side wall
        addEntity(new Hole(new Coordinate2D(250, 20)));
        addEntity(new Hole(new Coordinate2D(500, 20)));
        addEntity(new Hole(new Coordinate2D(780, 20)));
        addEntity(new Hole(new Coordinate2D(1030, 20)));
        addEntity(new Hole(new Coordinate2D(20, 360)));
        addEntity(new Hole(new Coordinate2D(1240, 360)));

        // Documents: 2 Dossiers and 2 Books
        List<Document> documents = new ArrayList<>();
        documents.add(new Dossier(new Coordinate2D(200, 200)));
        documents.add(new Dossier(new Coordinate2D(1050, 200)));
        documents.add(new Book(new Coordinate2D(300, 500)));
        documents.add(new Book(new Coordinate2D(900, 500)));
        for (Document doc : documents) {
            addEntity(doc);
        }

        // Test silverfish from top-left hole
        addEntity(new CommonSilverfish(new Coordinate2D(250, 30), documents));

        // Archivist in the center
        var archivist = new Archivist(new Coordinate2D(getWidth() / 2, getHeight() / 2), this::addEntity);
        addEntity(archivist);
    }
}