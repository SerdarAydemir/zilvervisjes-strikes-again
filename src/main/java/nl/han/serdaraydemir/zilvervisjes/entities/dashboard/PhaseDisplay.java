package nl.han.serdaraydemir.zilvervisjes.entities.dashboard;

import com.github.hanyaeger.api.AnchorPoint;
import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.entities.impl.TextEntity;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import nl.han.serdaraydemir.zilvervisjes.game.Phase;

/**
 * Fase-element van het dashboard. Toont de huidige spelfase centraal
 * bovenin het speelscherm met de bijbehorende fase-kleur (groen voor
 * Kalm, oranje voor Gemiddeld, rood voor Extreem). GameScene roept
 * update() aan bij elke automatische fasewisseling.
 */
public class PhaseDisplay extends TextEntity {

    private static final double FONT_SIZE = 22;

    /**
     * Maakt een nieuw fase-element aan op de gegeven positie en
     * toont direct de beginfase.
     *
     * @param location positie van het fase-element op het scherm
     * @param initialPhase fase waarmee het spel start
     */
    public PhaseDisplay(Coordinate2D location, Phase initialPhase) {
        super(location);
        setFont(Font.font("Verdana", FontWeight.BOLD, FONT_SIZE));
        setAnchorPoint(AnchorPoint.TOP_CENTER);
        update(initialPhase);
    }

    /**
     * Werkt de weergave bij naar de meegegeven fase: zowel de tekst
     * als de kleur worden onmiddellijk aangepast.
     *
     * @param phase de nieuwe spelfase
     */
    public void update(Phase phase) {
        setText(phase.getDisplayName());
        setFill(phase.getDisplayColor());
    }
}