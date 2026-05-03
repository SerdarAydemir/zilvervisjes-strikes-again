package nl.han.serdaraydemir.zilvervisjes;

import com.github.hanyaeger.api.YaegerGame;
import com.github.hanyaeger.api.Size;

public class ZilvervisjesStrikesAgain extends YaegerGame {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void setupGame() {
        setGameTitle("Zilvervisjes: Strikes Again");
        setSize(new Size(1280, 720));
    }

    @Override
    public void setupScenes() {
        // Scenes will be added here later
    }
}
