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

public class HelpScene extends StaticScene implements KeyListener {

    private static final Color BACKGROUND_COLOR = Color.web("#14110e");
    private static final Color HEADING_COLOR = Color.web("#e8d8a8");
    private static final Color SECTION_COLOR = Color.web("#c0392b");
    private static final Color TEXT_COLOR = Color.web("#d8c8a0");
    private static final Color HINT_COLOR = Color.web("#9a8c5a");

    private final ZilvervisjesStrikesAgain game;

    public HelpScene(ZilvervisjesStrikesAgain game) {
        this.game = game;
    }

    @Override
    public void setupScene() {
        setBackgroundColor(BACKGROUND_COLOR);
    }

    @Override
    public void setupEntities() {
        addEntity(buildHeading());

        addEntity(sectionHeader("DOEL", 130));
        addEntity(text("Bescherm de 2 Boeken en 2 Dossiers van Het Depot zo lang mogelijk", 165));
        addEntity(text("tegen de invasie van zilvervisjes.", 195));

        addEntity(sectionHeader("BESTURING", 250));
        addEntity(text("Pijltjes   ·   Bewegen", 285));
        addEntity(text("Spatie     ·   Insecticide spray  (gebied)", 315));
        addEntity(text("X          ·   UV-lamp  (één doelwit)", 345));

        addEntity(sectionHeader("VIJANDEN", 400));
        addEntity(text("Gewone zilvervis      ·   10 punten", 435));
        addEntity(text("Gestreepte zilvervis  ·   15 punten  (zigzag)", 465));
        addEntity(text("Papiervisje           ·   20 punten  (richt op dossiers)", 495));
        addEntity(text("Vuurvisje             ·   40 punten  (3 hp, taai)", 525));

        addEntity(buildEnterHint());
        addEntity(buildMenuHint());
    }

    @Override
    public void onPressedKeysChange(Set<KeyCode> pressedKeys) {
        if (pressedKeys.contains(KeyCode.ENTER)) {
            Platform.runLater(() -> game.startNewGame(false));
        } else if (pressedKeys.contains(KeyCode.M) || pressedKeys.contains(KeyCode.ESCAPE)) {
            Platform.runLater(() -> game.setActiveScene(ZilvervisjesStrikesAgain.SCENE_START));
        }
    }

    private TextEntity buildHeading() {
        TextEntity heading = new TextEntity(new Coordinate2D(getWidth() / 2, 50), "HOE TE SPELEN");
        heading.setAnchorPoint(AnchorPoint.TOP_CENTER);
        heading.setFill(HEADING_COLOR);
        heading.setFont(Font.font("Verdana", FontWeight.BOLD, 48));
        return heading;
    }

    private TextEntity sectionHeader(String label, double y) {
        TextEntity header = new TextEntity(new Coordinate2D(getWidth() / 2, y), label);
        header.setAnchorPoint(AnchorPoint.TOP_CENTER);
        header.setFill(SECTION_COLOR);
        header.setFont(Font.font("Verdana", FontWeight.BOLD, 22));
        return header;
    }

    private TextEntity text(String content, double y) {
        TextEntity entry = new TextEntity(new Coordinate2D(getWidth() / 2, y), content);
        entry.setAnchorPoint(AnchorPoint.TOP_CENTER);
        entry.setFill(TEXT_COLOR);
        entry.setFont(Font.font("Verdana", FontWeight.NORMAL, 18));
        return entry;
    }

    private TextEntity buildEnterHint() {
        TextEntity hint = new TextEntity(new Coordinate2D(getWidth() / 2, getHeight() - 90),
                "Druk op ENTER om te starten");
        hint.setAnchorPoint(AnchorPoint.TOP_CENTER);
        hint.setFill(HINT_COLOR);
        hint.setFont(Font.font("Verdana", FontWeight.BOLD, 18));
        return hint;
    }

    private TextEntity buildMenuHint() {
        TextEntity hint = new TextEntity(new Coordinate2D(getWidth() / 2, getHeight() - 60),
                "Druk op M of ESC voor het hoofdmenu");
        hint.setAnchorPoint(AnchorPoint.TOP_CENTER);
        hint.setFill(HINT_COLOR);
        hint.setFont(Font.font("Verdana", FontWeight.NORMAL, 16));
        return hint;
    }
}