package nl.han.serdaraydemir.zilvervisjes.entities.dashboard;

import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.entities.impl.TextEntity;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class ScoreDisplay extends TextEntity {

    private static final Color TEXT_COLOR = Color.web("#e8e0c8");
    private static final double FONT_SIZE = 22;

    private int score = 0;

    public ScoreDisplay(Coordinate2D location) {
        super(location);
        setFill(TEXT_COLOR);
        setFont(Font.font("Verdana", FontWeight.BOLD, FONT_SIZE));
        refresh();
    }

    public void addPoints(int amount) {
        score += amount;
        refresh();
    }

    public int getScore() {
        return score;
    }

    private void refresh() {
        setText("Score: " + score);
    }
}