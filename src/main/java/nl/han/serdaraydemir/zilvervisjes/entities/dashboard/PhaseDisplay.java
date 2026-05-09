package nl.han.serdaraydemir.zilvervisjes.entities.dashboard;

import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.entities.impl.TextEntity;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import nl.han.serdaraydemir.zilvervisjes.game.Phase;


public class PhaseDisplay extends TextEntity {

    private static final double FONT_SIZE = 22;

    public PhaseDisplay(Coordinate2D location, Phase initialPhase) {
        super(location);
        setFont(Font.font("Verdana", FontWeight.BOLD, FONT_SIZE));
        setAnchorPoint(com.github.hanyaeger.api.AnchorPoint.TOP_CENTER);
        update(initialPhase);
    }

    public void update(Phase phase) {
        setText(phase.getDisplayName());
        setFill(phase.getDisplayColor());
    }
}