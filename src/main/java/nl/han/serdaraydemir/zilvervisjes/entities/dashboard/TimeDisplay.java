package nl.han.serdaraydemir.zilvervisjes.entities.dashboard;

import com.github.hanyaeger.api.AnchorPoint;
import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.entities.impl.TextEntity;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Tijd-element van het dashboard. Toont de verstreken speeltijd
 * rechtsboven in het speelscherm in het formaat MM:SS. GameScene
 * roept tick() aan via een seconde-timer om de teller op te hogen.
 */
public class TimeDisplay extends TextEntity {

    private static final Color TEXT_COLOR = Color.web("#e8e0c8");
    private static final double FONT_SIZE = 22;

    private int elapsedSeconds = 0;

    /**
     * Maakt een nieuw tijd-element aan op de gegeven positie en
     * toont direct de starttijd 00:00.
     *
     * @param location positie van het tijd-element op het scherm
     */
    public TimeDisplay(Coordinate2D location) {
        super(location);
        setFill(TEXT_COLOR);
        setFont(Font.font("Verdana", FontWeight.BOLD, FONT_SIZE));
        setAnchorPoint(AnchorPoint.TOP_RIGHT);
        refresh();
    }

    /**
     * Hoogt de teller met één seconde op en werkt de tekstweergave bij.
     * Wordt door GameScene elke seconde aangeroepen via een timer.
     */
    public void tick() {
        elapsedSeconds++;
        refresh();
    }

    /**
     * Geeft het aantal verstreken seconden terug. Wordt door GameScene
     * gebruikt om de overleefde tijd door te geven aan het game-overscherm.
     *
     * @return aantal seconden sinds het einde van de openingssequentie
     */
    public int getElapsedSeconds() {
        return elapsedSeconds;
    }

    private void refresh() {
        int minutes = elapsedSeconds / 60;
        int seconds = elapsedSeconds % 60;
        setText(String.format("Tijd: %02d:%02d", minutes, seconds));
    }
}