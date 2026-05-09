package nl.han.serdaraydemir.zilvervisjes.spawners;

import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.entities.EntitySpawner;
import nl.han.serdaraydemir.zilvervisjes.entities.Hole;
import nl.han.serdaraydemir.zilvervisjes.entities.documents.Document;
import nl.han.serdaraydemir.zilvervisjes.entities.silverfish.CommonSilverfish;
import nl.han.serdaraydemir.zilvervisjes.entities.silverfish.Silverfish;
import nl.han.serdaraydemir.zilvervisjes.entities.silverfish.StripedSilverfish;

import java.util.List;
import java.util.Random;

public class SilverfishSpawner extends EntitySpawner {

    private final List<Hole> holes;
    private final List<Document> documents;
    private final Random random = new Random();

    public SilverfishSpawner(long intervalInMs, List<Hole> holes, List<Document> documents) {
        super(intervalInMs);
        this.holes = holes;
        this.documents = documents;
    }

    @Override
    protected void spawnEntities() {
        if (holes.isEmpty()) {
            return;
        }
        Hole hole = holes.get(random.nextInt(holes.size()));
        Coordinate2D location = hole.getSpawnLocation();
        spawn(createSilverfish(location));
    }

    private Silverfish createSilverfish(Coordinate2D location) {
        if (random.nextInt(100) < 30) {
            return new StripedSilverfish(location, documents);
        }
        return new CommonSilverfish(location, documents);
    }
}