package nl.han.serdaraydemir.zilvervisjes.game;

import javafx.scene.paint.Color;

/**
 * Definieert de drie spelfases en bundelt per fase de bijbehorende
 * spawn-frequentie, indicatorkleur en weergavenaam. Wordt gebruikt
 * door GameScene voor de automatische fasewisselingen en door
 * SilverfishSpawner en PhaseDisplay voor het aanpassen van hun
 * gedrag en weergave.
 */
public enum Phase {

    KALM(5000, Color.web("#3a8a3a"), "Kalm"),
    GEMIDDELD(3000, Color.web("#d68a2a"), "Gemiddeld"),
    EXTREEM(2000, Color.web("#c0392b"), "Extreem");

    private final long spawnIntervalMs;
    private final Color displayColor;
    private final String displayName;

    Phase(long spawnIntervalMs, Color displayColor, String displayName) {
        this.spawnIntervalMs = spawnIntervalMs;
        this.displayColor = displayColor;
        this.displayName = displayName;
    }

    /**
     * Geeft het spawn-interval voor deze fase terug.
     *
     * @return tijd in milliseconden tussen twee spawns
     */
    public long getSpawnIntervalMs() {
        return spawnIntervalMs;
    }

    /**
     * Geeft de indicatorkleur voor deze fase terug. Wordt door
     * PhaseDisplay gebruikt om de fase visueel weer te geven
     * (groen voor Kalm, oranje voor Gemiddeld, rood voor Extreem).
     *
     * @return JavaFX-kleur voor de fase-indicator
     */
    public Color getDisplayColor() {
        return displayColor;
    }

    /**
     * Geeft de weergavenaam van deze fase terug.
     *
     * @return naam zoals deze in het dashboard wordt getoond
     */
    public String getDisplayName() {
        return displayName;
    }
}