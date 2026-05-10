package nl.han.serdaraydemir.zilvervisjes.entities.dashboard;

import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.entities.impl.TextEntity;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Score-element van het dashboard. Toont de huidige score linksboven
 * in het speelscherm en biedt methoden om punten toe te voegen.
 * GameScene roept addPoints() aan zodra een zilvervis is uitgeschakeld.
 */
public class ScoreDisplay extends TextEntity {

    private static final Color TEXT_COLOR = Color.web("#e8e0c8");
    private static final double FONT_SIZE = 22;

    private int score = 0;

    /**
     * Maakt een nieuw score-element aan op de gegeven positie en
     * toont direct de beginscore.
     *
     * @param location positie van het score-element op het scherm
     */
    public ScoreDisplay(Coordinate2D location) {
        super(location);
        setFill(TEXT_COLOR);
        setFont(Font.font("Verdana", FontWeight.BOLD, FONT_SIZE));
        refresh();
    }

    /**
     * Voegt het meegegeven aantal punten toe aan de huidige score
     * en werkt de tekstweergave bij.
     *
     * @param amount aantal punten om toe te voegen
     */
    public void addPoints(int amount) {
        score += amount;
        refresh();
    }

    /**
     * Geeft de huidige score terug. Wordt door GameScene gebruikt
     * om de eindscore door te geven aan het game-overscherm.
     *
     * @return de huidige score
     */
    public int getScore() {
        return score;
    }

    private void refresh() {
        setText("Score: " + score);
    }
}