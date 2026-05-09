package nl.han.serdaraydemir.zilvervisjes.entities.dashboard;

import com.github.hanyaeger.api.AnchorPoint;
import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.entities.impl.TextEntity;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class TimeDisplay extends TextEntity {

    private static final Color TEXT_COLOR = Color.web("#e8e0c8");
    private static final double FONT_SIZE = 22;

    private int elapsedSeconds = 0;

    public TimeDisplay(Coordinate2D location) {
        super(location);
        setFill(TEXT_COLOR);
        setFont(Font.font("Verdana", FontWeight.BOLD, FONT_SIZE));
        setAnchorPoint(AnchorPoint.TOP_RIGHT);
        refresh();
    }

    public void tick() {
        elapsedSeconds++;
        refresh();
    }

    public int getElapsedSeconds() {
        return elapsedSeconds;
    }

    private void refresh() {
        int minutes = elapsedSeconds / 60;
        int seconds = elapsedSeconds % 60;
        setText(String.format("Tijd: %02d:%02d", minutes, seconds));
    }
}