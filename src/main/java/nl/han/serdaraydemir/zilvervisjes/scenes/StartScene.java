package nl.han.serdaraydemir.zilvervisjes.scenes;

import com.github.hanyaeger.api.AnchorPoint;
import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.Timer;
import com.github.hanyaeger.api.TimerContainer;
import com.github.hanyaeger.api.entities.impl.TextEntity;
import com.github.hanyaeger.api.scenes.DynamicScene;
import com.github.hanyaeger.api.userinput.KeyListener;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import nl.han.serdaraydemir.zilvervisjes.ZilvervisjesStrikesAgain;
import nl.han.serdaraydemir.zilvervisjes.entities.menu.MenuSilverfish;
import javafx.application.Platform;

import java.util.Random;
import java.util.Set;

public class StartScene extends DynamicScene implements KeyListener, TimerContainer {

    private static final Color BACKGROUND_COLOR = Color.web("#14110e");
    private static final Color TITLE_COLOR = Color.web("#e8d8a8");
    private static final Color SUBTITLE_COLOR = Color.web("#c0392b");
    private static final Color PROMPT_COLOR = Color.web("#9a8c5a");
    private static final Color HINT_COLOR = Color.web("#e8d8a8");

    private static final int MENU_SILVERFISH_COUNT = 10;
    private static final int PROMPT_PULSE_INTERVAL_MS = 50;

    private final ZilvervisjesStrikesAgain game;
    private final Random random = new Random();

    private TextEntity prompt;
    private double promptOpacityPhase = 0;

    public StartScene(ZilvervisjesStrikesAgain game) {
        this.game = game;
    }

    @Override
    public void setupScene() {
        setBackgroundColor(BACKGROUND_COLOR);
    }

    @Override
    public void setupEntities() {
        spawnAtmosphereSilverfish();
        addEntity(buildTitle());
        addEntity(buildSubtitle());
        prompt = buildPrompt();
        addEntity(prompt);
        addEntity(buildControlsHint());
        addEntity(buildCreditsLine());
    }

    @Override
    public void setupTimers() {
        addTimer(new PromptPulseTimer());
    }

    @Override
    public void onPressedKeysChange(Set<KeyCode> pressedKeys) {
        if (pressedKeys.contains(KeyCode.ENTER)) {
            Platform.runLater(() -> game.startNewGame(false));
        } else if (pressedKeys.contains(KeyCode.H)) {
            Platform.runLater(() -> game.setActiveScene(ZilvervisjesStrikesAgain.SCENE_HELP));
        }
    }

    private void spawnAtmosphereSilverfish() {
        for (int i = 0; i < MENU_SILVERFISH_COUNT; i++) {
            double x = 100 + random.nextDouble() * (getWidth() - 200);
            double y = 100 + random.nextDouble() * (getHeight() - 200);
            double speed = 0.6 + random.nextDouble() * 0.6;
            double angle = random.nextDouble() * 360;
            addEntity(new MenuSilverfish(new Coordinate2D(x, y), speed, angle));
        }
    }

    private TextEntity buildTitle() {
        TextEntity title = new TextEntity(
                new Coordinate2D(getWidth() / 2, getHeight() / 2 - 100),
                "ZILVERVISJES"
        );
        title.setAnchorPoint(AnchorPoint.CENTER_CENTER);
        title.setFill(TITLE_COLOR);
        title.setFont(Font.font("Verdana", FontWeight.BOLD, 84));
        return title;
    }

    private TextEntity buildSubtitle() {
        TextEntity subtitle = new TextEntity(
                new Coordinate2D(getWidth() / 2, getHeight() / 2 - 30),
                "Strikes Again!"
        );
        subtitle.setAnchorPoint(AnchorPoint.CENTER_CENTER);
        subtitle.setFill(SUBTITLE_COLOR);
        subtitle.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.ITALIC, 38));
        return subtitle;
    }

    private TextEntity buildPrompt() {
        TextEntity p = new TextEntity(
                new Coordinate2D(getWidth() / 2, getHeight() / 2 + 80),
                "Druk op ENTER om te starten"
        );
        p.setAnchorPoint(AnchorPoint.CENTER_CENTER);
        p.setFill(PROMPT_COLOR);
        p.setFont(Font.font("Verdana", FontWeight.BOLD, 24));
        return p;
    }

    private TextEntity buildControlsHint() {
        TextEntity hint = new TextEntity(
                new Coordinate2D(getWidth() / 2, getHeight() - 80),
                "Druk op H voor hoe te spelen"
        );
        hint.setAnchorPoint(AnchorPoint.CENTER_CENTER);
        hint.setFill(HINT_COLOR);
        hint.setFont(Font.font("Verdana", FontWeight.BOLD, 16));
        return hint;
    }

    private TextEntity buildCreditsLine() {
        TextEntity credits = new TextEntity(
                new Coordinate2D(getWidth() / 2, getHeight() - 40),
                "Serdar Aydemir  -  HAN 2026"
        );
        credits.setAnchorPoint(AnchorPoint.CENTER_CENTER);
        credits.setFill(HINT_COLOR);
        credits.setFont(Font.font("Verdana", FontPosture.ITALIC, 14));
        return credits;
    }

    private void pulsePrompt() {
        promptOpacityPhase += 0.05;
        double opacity = 0.55 + 0.45 * Math.sin(promptOpacityPhase);
        prompt.setOpacity(opacity);
    }

    private class PromptPulseTimer extends Timer {

        protected PromptPulseTimer() {
            super(PROMPT_PULSE_INTERVAL_MS);
        }

        @Override
        public void onAnimationUpdate(long timestamp) {
            if (prompt != null) {
                pulsePrompt();
            }
        }
    }
}