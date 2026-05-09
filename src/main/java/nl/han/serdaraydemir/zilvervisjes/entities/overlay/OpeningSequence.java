package nl.han.serdaraydemir.zilvervisjes.entities.overlay;

import com.github.hanyaeger.api.AnchorPoint;
import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.Timer;
import com.github.hanyaeger.api.entities.YaegerEntity;
import com.github.hanyaeger.api.entities.impl.RectangleEntity;
import com.github.hanyaeger.api.entities.impl.TextEntity;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class OpeningSequence {

    private static final int TICK_INTERVAL_MS = 50;
    private static final int FADE_TICKS = 10;

    private static final int PHASE1_HOLD_TICKS = 200;
    private static final int PHASE2_HOLD_TICKS = 50;
    private static final int COUNTDOWN_HOLD_TICKS = 20;

    private static final Color BLACKOUT_COLOR = Color.web("#0a0807");
    private static final Color STORY_COLOR = Color.web("#e8e0c8");
    private static final Color HIGHLIGHT_COLOR = Color.web("#f4ead0");
    private static final Color COUNTDOWN_COLOR = Color.web("#c0392b");
    private static final Color HINT_COLOR = Color.web("#c9b87a");

    private static final String[] STORY_LINES = {
            "Het is laat in de avond. Diep onder het Regionaal Archief Nijmegen — in het Depot,",
            "waar honderden jaren aan dossiers en boeken bewaard worden — gaat het licht aan.",
            "",
            "Een archivaris is opgeroepen voor een spoedklus: de zilvervisjes zijn terug,",
            "in grotere getale dan ooit. Eén voor één kruipen ze door de spleten in de muren naar binnen,",
            "op zoek naar papier om aan te knagen.",
            "",
            "Aan jou de taak om Het Depot te beschermen…"
    };

    private static final String SLOT_LINE = "…zo lang mogelijk.";
    private static final String CONTROLS_HINT = "Pijltjes = bewegen  -  Spatie = spray  -  X = UV-lamp";

    private final double sceneWidth;
    private final double sceneHeight;
    private final Consumer<YaegerEntity> sceneAddEntity;
    private final Runnable onComplete;

    private final List<TextEntity> storyTextEntities = new ArrayList<>();
    private BlackoutOverlay blackoutOverlay;
    private TextEntity slotText;
    private TextEntity countdownText;
    private TextEntity controlsHint;

    private Phase phase = Phase.PHASE1_FADE_IN;
    private int ticksInPhase = 0;

    public OpeningSequence(double sceneWidth, double sceneHeight,
                           Consumer<YaegerEntity> sceneAddEntity,
                           Runnable onComplete) {
        this.sceneWidth = sceneWidth;
        this.sceneHeight = sceneHeight;
        this.sceneAddEntity = sceneAddEntity;
        this.onComplete = onComplete;
    }

    public void install() {
        blackoutOverlay = new BlackoutOverlay(new Coordinate2D(0, 0));
        blackoutOverlay.setWidth(sceneWidth);
        blackoutOverlay.setHeight(sceneHeight);
        blackoutOverlay.setFill(BLACKOUT_COLOR);
        blackoutOverlay.setOpacity(1.0);
        blackoutOverlay.setViewOrder(-50);
        sceneAddEntity.accept(blackoutOverlay);

        double lineHeight = 36;
        double startY = sceneHeight / 2 - (STORY_LINES.length * lineHeight) / 2;
        for (int i = 0; i < STORY_LINES.length; i++) {
            String content = STORY_LINES[i];
            if (content.isEmpty()) {
                continue;
            }
            boolean isFinalLine = i == STORY_LINES.length - 1;
            TextEntity line = new TextEntity(new Coordinate2D(sceneWidth / 2, startY + i * lineHeight), content);
            line.setAnchorPoint(AnchorPoint.TOP_CENTER);
            line.setFill(isFinalLine ? HIGHLIGHT_COLOR : STORY_COLOR);
            line.setFont(Font.font("Verdana",
                    isFinalLine ? FontWeight.BOLD : FontWeight.NORMAL,
                    FontPosture.ITALIC,
                    isFinalLine ? 24 : 20));
            line.setOpacity(0);
            line.setViewOrder(-60);
            storyTextEntities.add(line);
            sceneAddEntity.accept(line);
        }

        slotText = new TextEntity(new Coordinate2D(sceneWidth / 2, sceneHeight / 2), SLOT_LINE);
        slotText.setAnchorPoint(AnchorPoint.CENTER_CENTER);
        slotText.setFill(HIGHLIGHT_COLOR);
        slotText.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.ITALIC, 48));
        slotText.setOpacity(0);
        slotText.setViewOrder(-60);
        sceneAddEntity.accept(slotText);

        countdownText = new TextEntity(new Coordinate2D(sceneWidth / 2, sceneHeight / 2 - 20), "");
        countdownText.setAnchorPoint(AnchorPoint.CENTER_CENTER);
        countdownText.setFill(COUNTDOWN_COLOR);
        countdownText.setFont(Font.font("Verdana", FontWeight.BOLD, 96));
        countdownText.setViewOrder(-60);
        sceneAddEntity.accept(countdownText);

        controlsHint = new TextEntity(new Coordinate2D(sceneWidth / 2, sceneHeight / 2 + 80), "");
        controlsHint.setAnchorPoint(AnchorPoint.CENTER_CENTER);
        controlsHint.setFill(HINT_COLOR);
        controlsHint.setFont(Font.font("Verdana", FontWeight.BOLD, 18));
        controlsHint.setViewOrder(-60);
        sceneAddEntity.accept(controlsHint);
    }

    public Timer createDriver() {
        return new DriverTimer();
    }

    private void advance() {
        ticksInPhase++;
        switch (phase) {
            case PHASE1_FADE_IN -> handlePhase1FadeIn();
            case PHASE1_HOLD -> handlePhase1Hold();
            case PHASE1_FADE_OUT -> handlePhase1FadeOut();
            case PHASE2_FADE_IN -> handlePhase2FadeIn();
            case PHASE2_HOLD -> handlePhase2Hold();
            case PHASE2_FADE_OUT -> handlePhase2FadeOut();
            case COUNTDOWN_3, COUNTDOWN_2, COUNTDOWN_1 -> handleCountdown();
            case COMPLETE -> {
            }
        }
    }

    private void handlePhase1FadeIn() {
        double opacity = Math.min(1.0, (double) ticksInPhase / FADE_TICKS);
        setStoryOpacity(opacity);
        if (ticksInPhase >= FADE_TICKS) {
            transitionTo(Phase.PHASE1_HOLD);
        }
    }

    private void handlePhase1Hold() {
        if (ticksInPhase >= PHASE1_HOLD_TICKS) {
            transitionTo(Phase.PHASE1_FADE_OUT);
        }
    }

    private void handlePhase1FadeOut() {
        double opacity = Math.max(0.0, 1.0 - (double) ticksInPhase / FADE_TICKS);
        setStoryOpacity(opacity);
        if (ticksInPhase >= FADE_TICKS) {
            for (TextEntity line : storyTextEntities) {
                line.remove();
            }
            transitionTo(Phase.PHASE2_FADE_IN);
        }
    }

    private void handlePhase2FadeIn() {
        double opacity = Math.min(1.0, (double) ticksInPhase / FADE_TICKS);
        slotText.setOpacity(opacity);
        if (ticksInPhase >= FADE_TICKS) {
            transitionTo(Phase.PHASE2_HOLD);
        }
    }

    private void handlePhase2Hold() {
        if (ticksInPhase >= PHASE2_HOLD_TICKS) {
            transitionTo(Phase.PHASE2_FADE_OUT);
        }
    }

    private void handlePhase2FadeOut() {
        double opacity = Math.max(0.0, 1.0 - (double) ticksInPhase / FADE_TICKS);
        slotText.setOpacity(opacity);
        blackoutOverlay.setOpacity(opacity);
        if (ticksInPhase >= FADE_TICKS) {
            slotText.remove();
            blackoutOverlay.remove();
            controlsHint.setText(CONTROLS_HINT);
            controlsHint.setAnchorPoint(AnchorPoint.CENTER_CENTER);
            countdownText.setText("3");
            countdownText.setAnchorPoint(AnchorPoint.CENTER_CENTER);
            transitionTo(Phase.COUNTDOWN_3);
        }
    }

    private void handleCountdown() {
        if (ticksInPhase < COUNTDOWN_HOLD_TICKS) {
            return;
        }
        switch (phase) {
            case COUNTDOWN_3 -> {
                countdownText.setText("2");
                transitionTo(Phase.COUNTDOWN_2);
            }
            case COUNTDOWN_2 -> {
                countdownText.setText("1");
                transitionTo(Phase.COUNTDOWN_1);
            }
            case COUNTDOWN_1 -> {
                countdownText.remove();
                controlsHint.remove();
                transitionTo(Phase.COMPLETE);
                onComplete.run();
            }
            default -> {
            }
        }
    }

    private void setStoryOpacity(double opacity) {
        for (TextEntity line : storyTextEntities) {
            line.setOpacity(opacity);
        }
    }

    private void transitionTo(Phase next) {
        phase = next;
        ticksInPhase = 0;
    }

    private enum Phase {
        PHASE1_FADE_IN,
        PHASE1_HOLD,
        PHASE1_FADE_OUT,
        PHASE2_FADE_IN,
        PHASE2_HOLD,
        PHASE2_FADE_OUT,
        COUNTDOWN_3,
        COUNTDOWN_2,
        COUNTDOWN_1,
        COMPLETE
    }

    private class DriverTimer extends Timer {

        protected DriverTimer() {
            super(TICK_INTERVAL_MS);
        }

        @Override
        public void onAnimationUpdate(long timestamp) {
            if (phase == Phase.COMPLETE) {
                return;
            }
            advance();
        }
    }

    private static class BlackoutOverlay extends RectangleEntity {

        protected BlackoutOverlay(Coordinate2D location) {
            super(location);
        }
    }
}