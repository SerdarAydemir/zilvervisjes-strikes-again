package nl.han.serdaraydemir.zilvervisjes.spawners;

import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.entities.EntitySpawner;
import nl.han.serdaraydemir.zilvervisjes.entities.Hole;
import nl.han.serdaraydemir.zilvervisjes.entities.documents.Document;
import nl.han.serdaraydemir.zilvervisjes.entities.silverfish.CommonSilverfish;
import nl.han.serdaraydemir.zilvervisjes.entities.silverfish.Firebrat;
import nl.han.serdaraydemir.zilvervisjes.entities.silverfish.Paperfish;
import nl.han.serdaraydemir.zilvervisjes.entities.silverfish.Silverfish;
import nl.han.serdaraydemir.zilvervisjes.entities.silverfish.StripedSilverfish;
import nl.han.serdaraydemir.zilvervisjes.game.Phase;
import java.util.function.Consumer;

import java.util.List;
import java.util.Random;

public class SilverfishSpawner extends EntitySpawner {

    private final List<Hole> holes;
    private final List<Document> documents;
    private final Random random = new Random();
    private final Consumer<Silverfish> deathListener;
    private Phase currentPhase = Phase.KALM;

    public SilverfishSpawner(List<Hole> holes, List<Document> documents, Consumer<Silverfish> deathListener) {
        super(Phase.KALM.getSpawnIntervalMs());
        this.holes = holes;
        this.documents = documents;
        this.deathListener = deathListener;
    }

    public void setPhase(Phase phase) {
        this.currentPhase = phase;
        setIntervalInMs(phase.getSpawnIntervalMs());
    }

    @Override
    protected void spawnEntities() {
        if (holes.isEmpty()) {
            return;
        }
        Hole hole = holes.get(random.nextInt(holes.size()));
        Coordinate2D location = hole.getSpawnLocation();
        Silverfish silverfish = createSilverfish(location);
        silverfish.setDeathListener(deathListener);
        spawn(silverfish);
    }

    private Silverfish createSilverfish(Coordinate2D location) {
        int roll = random.nextInt(100);
        return switch (currentPhase) {
            case KALM -> spawnForKalm(roll, location);
            case GEMIDDELD -> spawnForGemiddeld(roll, location);
            case EXTREEM -> spawnForExtreem(roll, location);
        };
    }

    private Silverfish spawnForKalm(int roll, Coordinate2D location) {
        if (roll < 30) {
            return new StripedSilverfish(location, documents);
        }
        return new CommonSilverfish(location, documents);
    }

    private Silverfish spawnForGemiddeld(int roll, Coordinate2D location) {
        if (roll < 25) {
            return new Paperfish(location, documents);
        }
        if (roll < 55) {
            return new StripedSilverfish(location, documents);
        }
        return new CommonSilverfish(location, documents);
    }

    private Silverfish spawnForExtreem(int roll, Coordinate2D location) {
        if (roll < 15) {
            return new Firebrat(location, documents);
        }
        if (roll < 40) {
            return new Paperfish(location, documents);
        }
        if (roll < 65) {
            return new StripedSilverfish(location, documents);
        }
        return new CommonSilverfish(location, documents);
    }
}