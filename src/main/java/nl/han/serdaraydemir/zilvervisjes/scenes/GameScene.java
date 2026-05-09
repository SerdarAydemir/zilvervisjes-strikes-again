package nl.han.serdaraydemir.zilvervisjes.scenes;

import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.EntitySpawnerContainer;
import com.github.hanyaeger.api.Timer;
import com.github.hanyaeger.api.TimerContainer;
import com.github.hanyaeger.api.scenes.DynamicScene;
import javafx.scene.paint.Color;
import nl.han.serdaraydemir.zilvervisjes.ZilvervisjesStrikesAgain;
import nl.han.serdaraydemir.zilvervisjes.entities.Archivist;
import nl.han.serdaraydemir.zilvervisjes.entities.Hole;
import nl.han.serdaraydemir.zilvervisjes.entities.dashboard.PhaseDisplay;
import nl.han.serdaraydemir.zilvervisjes.entities.dashboard.ScoreDisplay;
import nl.han.serdaraydemir.zilvervisjes.entities.dashboard.TimeDisplay;
import nl.han.serdaraydemir.zilvervisjes.entities.documents.Book;
import nl.han.serdaraydemir.zilvervisjes.entities.documents.Document;
import nl.han.serdaraydemir.zilvervisjes.entities.documents.Dossier;
import nl.han.serdaraydemir.zilvervisjes.entities.silverfish.Silverfish;
import nl.han.serdaraydemir.zilvervisjes.game.Phase;
import nl.han.serdaraydemir.zilvervisjes.spawners.SilverfishSpawner;

import java.util.ArrayList;
import java.util.List;

public class GameScene extends DynamicScene implements EntitySpawnerContainer, TimerContainer {

    private static final int GEMIDDELD_START_MS = 15_000;
    private static final int EXTREEM_START_MS = 30_000;

    private final ZilvervisjesStrikesAgain game;
    private final List<Hole> holes = new ArrayList<>();
    private final List<Document> documents = new ArrayList<>();

    private SilverfishSpawner spawner;
    private ScoreDisplay scoreDisplay;
    private PhaseDisplay phaseDisplay;
    private TimeDisplay timeDisplay;
    private Phase currentPhase = Phase.KALM;
    private boolean gameOverTriggered = false;

    public GameScene(ZilvervisjesStrikesAgain game) {
        this.game = game;
    }

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
            doc.setDestructionListener(this::onDocumentDestroyed);
            addEntity(doc);
        }

        var archivist = new Archivist(new Coordinate2D(getWidth() / 2, getHeight() / 2), this::addEntity);
        addEntity(archivist);

        scoreDisplay = new ScoreDisplay(new Coordinate2D(20, 15));
        addEntity(scoreDisplay);

        phaseDisplay = new PhaseDisplay(new Coordinate2D(getWidth() / 2, 15), currentPhase);
        addEntity(phaseDisplay);

        timeDisplay = new TimeDisplay(new Coordinate2D(getWidth() - 20, 15));
        addEntity(timeDisplay);
    }

    @Override
    public void setupEntitySpawners() {
        spawner = new SilverfishSpawner(holes, documents, this::onSilverfishKilled);
        addEntitySpawner(spawner);
    }

    @Override
    public void setupTimers() {
        addTimer(new PhaseTransitionTimer(GEMIDDELD_START_MS, Phase.GEMIDDELD));
        addTimer(new PhaseTransitionTimer(EXTREEM_START_MS, Phase.EXTREEM));
        addTimer(new TimeTickTimer());
    }

    public Phase getCurrentPhase() {
        return currentPhase;
    }

    public void addScore(int points) {
        if (scoreDisplay != null) {
            scoreDisplay.addPoints(points);
        }
    }

    private void onSilverfishKilled(Silverfish silverfish) {
        addScore(silverfish.getPointsValue());
    }

    private void onDocumentDestroyed(Document document) {
        if (gameOverTriggered) {
            return;
        }
        if (allDocumentsDestroyed()) {
            gameOverTriggered = true;
            int finalScore = scoreDisplay != null ? scoreDisplay.getScore() : 0;
            String finalTime = timeDisplay != null ? formatTime(timeDisplay.getElapsedSeconds()) : "00:00";
            game.triggerGameOver(finalScore, finalTime);
        }
    }

    private boolean allDocumentsDestroyed() {
        for (Document doc : documents) {
            if (!doc.isDestroyed()) {
                return false;
            }
        }
        return true;
    }

    private String formatTime(int totalSeconds) {
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    private void transitionTo(Phase phase) {
        if (currentPhase == phase) {
            return;
        }
        currentPhase = phase;
        spawner.setPhase(phase);
        if (phaseDisplay != null) {
            phaseDisplay.update(phase);
        }
    }

    private class PhaseTransitionTimer extends Timer {

        private final Phase targetPhase;
        private boolean fired = false;

        protected PhaseTransitionTimer(int delayMs, Phase targetPhase) {
            super(delayMs);
            this.targetPhase = targetPhase;
        }

        @Override
        public void onAnimationUpdate(long timestamp) {
            if (fired) {
                return;
            }
            fired = true;
            transitionTo(targetPhase);
        }
    }

    private class TimeTickTimer extends Timer {

        protected TimeTickTimer() {
            super(1000);
        }

        @Override
        public void onAnimationUpdate(long timestamp) {
            if (timeDisplay != null) {
                timeDisplay.tick();
            }
        }
    }
}