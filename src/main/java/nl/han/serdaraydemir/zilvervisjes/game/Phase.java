package nl.han.serdaraydemir.zilvervisjes.game;

import javafx.scene.paint.Color;

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

    public long getSpawnIntervalMs() {
        return spawnIntervalMs;
    }

    public Color getDisplayColor() {
        return displayColor;
    }

    public String getDisplayName() {
        return displayName;
    }
}