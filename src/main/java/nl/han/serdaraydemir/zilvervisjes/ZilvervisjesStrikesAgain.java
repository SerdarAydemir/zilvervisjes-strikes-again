package nl.han.serdaraydemir.zilvervisjes;

import com.github.hanyaeger.api.YaegerGame;
import com.github.hanyaeger.api.Size;
import nl.han.serdaraydemir.zilvervisjes.scenes.GameScene;
import nl.han.serdaraydemir.zilvervisjes.scenes.StartScene;

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
        addScene(0, new StartScene(this));
        addScene(1, new GameScene());
    }
}
