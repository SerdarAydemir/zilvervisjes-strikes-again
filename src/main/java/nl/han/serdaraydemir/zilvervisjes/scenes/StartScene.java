package nl.han.serdaraydemir.zilvervisjes.scenes;

import com.github.hanyaeger.api.AnchorPoint;
import com.github.hanyaeger.api.Coordinate2D;
import com.github.hanyaeger.api.entities.impl.TextEntity;
import com.github.hanyaeger.api.scenes.StaticScene;
import com.github.hanyaeger.api.userinput.KeyListener;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import nl.han.serdaraydemir.zilvervisjes.ZilvervisjesStrikesAgain;

import java.util.Set;

public class StartScene extends StaticScene implements KeyListener {

    private final ZilvervisjesStrikesAgain game;

    public StartScene(ZilvervisjesStrikesAgain game) {
        this.game = game;
    }

    @Override
    public void setupScene() {
        setBackgroundColor(Color.web("#FAFAF7"));
    }

    @Override
    public void setupEntities() {
        var title = new TextEntity(
                new Coordinate2D(getWidth() / 2, getHeight() / 2 - 80),
                "ZILVERVISJES"
        );
        title.setFont(Font.font("Arial", 64));
        title.setFill(Color.web("#1a1a1a"));
        title.setAnchorPoint(AnchorPoint.CENTER_CENTER);
        addEntity(title);

        var subtitle = new TextEntity(
                new Coordinate2D(getWidth() / 2, getHeight() / 2 - 20),
                "Strikes Again!"
        );
        subtitle.setFont(Font.font("Arial", 32));
        subtitle.setFill(Color.web("#444444"));
        subtitle.setAnchorPoint(AnchorPoint.CENTER_CENTER);
        addEntity(subtitle);

        var prompt = new TextEntity(
                new Coordinate2D(getWidth() / 2, getHeight() / 2 + 120),
                "Druk op ENTER om te starten"
        );
        prompt.setFont(Font.font("Arial", 22));
        prompt.setFill(Color.web("#1a1a1a"));
        prompt.setAnchorPoint(AnchorPoint.CENTER_CENTER);
        addEntity(prompt);
    }

    @Override
    public void onPressedKeysChange(Set<KeyCode> pressedKeys) {
        if (pressedKeys.contains(KeyCode.ENTER)) {
            game.startNewGame(false);
        }
    }
}