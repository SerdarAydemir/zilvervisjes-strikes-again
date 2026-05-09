package nl.han.serdaraydemir.zilvervisjes.scenes;

import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.EntitySpawnerContainer;
import com.github.hanyaeger.api.scenes.DynamicScene;
import javafx.scene.paint.Color;
import nl.han.serdaraydemir.zilvervisjes.entities.Archivist;
import nl.han.serdaraydemir.zilvervisjes.entities.Hole;
import nl.han.serdaraydemir.zilvervisjes.entities.documents.Book;
import nl.han.serdaraydemir.zilvervisjes.entities.documents.Document;
import nl.han.serdaraydemir.zilvervisjes.entities.documents.Dossier;
import nl.han.serdaraydemir.zilvervisjes.spawners.SilverfishSpawner;

import java.util.ArrayList;
import java.util.List;

public class GameScene extends DynamicScene implements EntitySpawnerContainer {

    private static final long SPAWN_INTERVAL_MS = 2000;

    private final List<Hole> holes = new ArrayList<>();
    private final List<Document> documents = new ArrayList<>();


    @Override
    public void setupScene() {
        setBackgroundColor(Color.web("#1a1612"));
    }

    @Override
    public void setupEntities() {
        holes.add(new Hole(new Coordinate2D(250, 20)));
        holes.add(new Hole(new Coordinate2D(500, 20)));
        holes.add(new Hole(new Coordinate2D(780, 20)));
        holes.add(new Hole(new Coordinate2D(1030, 20)));
        holes.add(new Hole(new Coordinate2D(20, 360)));
        holes.add(new Hole(new Coordinate2D(1240, 360)));
        for (Hole hole : holes) {
            addEntity(hole);
        }

        documents.add(new Dossier(new Coordinate2D(200, 200)));
        documents.add(new Dossier(new Coordinate2D(1050, 200)));
        documents.add(new Book(new Coordinate2D(300, 500)));
        documents.add(new Book(new Coordinate2D(900, 500)));
        for (Document doc : documents) {
            addEntity(doc);
        }

        var archivist = new Archivist(new Coordinate2D(getWidth() / 2, getHeight() / 2), this::addEntity);
        addEntity(archivist);
    }

    @Override
    public void setupEntitySpawners() {
        addEntitySpawner(new SilverfishSpawner(SPAWN_INTERVAL_MS, holes, documents));
    }
}