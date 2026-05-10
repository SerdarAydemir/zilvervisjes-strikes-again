package nl.han.serdaraydemir.zilvervisjes.scenes;

import com.github.hanyaeger.api.AnchorPoint;
import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.entities.impl.TextEntity;
import com.github.hanyaeger.api.scenes.StaticScene;
import com.github.hanyaeger.api.userinput.KeyListener;
import javafx.application.Platform;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import nl.han.serdaraydemir.zilvervisjes.ZilvervisjesStrikesAgain;

import java.util.Set;

/**
 * Het eindscherm dat verschijnt zodra alle documenten zijn vernietigd.
 * Toont de eindscore en de overleefde tijd, en biedt de speler de
 * keuze om opnieuw te spelen (Enter — slaat de openingssequentie over)
 * of terug te keren naar het hoofdmenu (M).
 */
public class GameOverScene extends StaticScene implements KeyListener {

    private static final Color HEADING_COLOR = Color.web("#c0392b");
    private static final Color TEXT_COLOR = Color.web("#e8e0c8");
    private static final Color HINT_COLOR = Color.web("#9a8c5a");

    private final ZilvervisjesStrikesAgain game;
    private final int finalScore;
    private final String finalTime;

    /**
     * Maakt een nieuw game-overscherm aan met de eindresultaten van
     * de afgelopen spelsessie.
     *
     * @param game referentie naar de hoofdklasse voor scene-overgangen
     * @param finalScore eindscore van de speler
     * @param finalTime overleefde tijd, geformatteerd als MM:SS
     */
    public GameOverScene(ZilvervisjesStrikesAgain game, int finalScore, String finalTime) {
        this.game = game;
        this.finalScore = finalScore;
        this.finalTime = finalTime;
    }

    @Override
    public void setupScene() {
        setBackgroundColor(Color.web("#1a1612"));
    }

    @Override
    public void setupEntities() {
        addEntity(buildHeading());
        addEntity(buildScoreLine());
        addEntity(buildTimeLine());
        addEntity(buildRestartHint());
        addEntity(buildMenuHint());
    }

    /**
     * Verwerkt toetsindrukken op het game-overscherm. Enter start een
     * nieuwe spelsessie zonder openingssequentie; M keert terug naar
     * het hoofdmenu.
     *
     * @param pressedKeys de op dit moment ingedrukte toetsen
     */
    @Override
    public void onPressedKeysChange(Set<KeyCode> pressedKeys) {
        if (pressedKeys.contains(KeyCode.ENTER)) {
            Platform.runLater(game::startNewGame);
        } else if (pressedKeys.contains(KeyCode.M)) {
            Platform.runLater(() -> game.setActiveScene(ZilvervisjesStrikesAgain.SCENE_START));
        }
    }

    private TextEntity buildHeading() {
        TextEntity heading = new TextEntity(new Coordinate2D(getWidth() / 2, 200), "Game Over");
        heading.setAnchorPoint(AnchorPoint.TOP_CENTER);
        heading.setFill(HEADING_COLOR);
        heading.setFont(Font.font("Verdana", FontWeight.BOLD, 64));
        return heading;
    }

    private TextEntity buildScoreLine() {
        TextEntity line = new TextEntity(new Coordinate2D(getWidth() / 2, 320), "Score: " + finalScore);
        line.setAnchorPoint(AnchorPoint.TOP_CENTER);
        line.setFill(TEXT_COLOR);
        line.setFont(Font.font("Verdana", FontWeight.NORMAL, 32));
        return line;
    }

    private TextEntity buildTimeLine() {
        TextEntity line = new TextEntity(new Coordinate2D(getWidth() / 2, 370), "Tijd: " + finalTime);
        line.setAnchorPoint(AnchorPoint.TOP_CENTER);
        line.setFill(TEXT_COLOR);
        line.setFont(Font.font("Verdana", FontWeight.NORMAL, 32));
        return line;
    }

    private TextEntity buildRestartHint() {
        TextEntity hint = new TextEntity(new Coordinate2D(getWidth() / 2, 500), "Druk op ENTER om opnieuw te spelen");
        hint.setAnchorPoint(AnchorPoint.TOP_CENTER);
        hint.setFill(HINT_COLOR);
        hint.setFont(Font.font("Verdana", FontWeight.NORMAL, 20));
        return hint;
    }

    private TextEntity buildMenuHint() {
        TextEntity hint = new TextEntity(new Coordinate2D(getWidth() / 2, 540), "Druk op M voor het hoofdmenu");
        hint.setAnchorPoint(AnchorPoint.TOP_CENTER);
        hint.setFill(HINT_COLOR);
        hint.setFont(Font.font("Verdana", FontWeight.NORMAL, 20));
        return hint;
    }
}