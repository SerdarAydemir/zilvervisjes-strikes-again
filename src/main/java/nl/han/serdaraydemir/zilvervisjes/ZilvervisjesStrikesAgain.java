package nl.han.serdaraydemir.zilvervisjes;

import com.github.hanyaeger.api.Size;
import com.github.hanyaeger.api.YaegerGame;
import nl.han.serdaraydemir.zilvervisjes.scenes.GameOverScene;
import nl.han.serdaraydemir.zilvervisjes.scenes.GameScene;
import nl.han.serdaraydemir.zilvervisjes.scenes.StartScene;

public class ZilvervisjesStrikesAgain extends YaegerGame {

    public static final int SCENE_START = 0;
    public static final int SCENE_GAME = 1;
    public static final int SCENE_GAME_OVER = 2;

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
        addScene(SCENE_START, new StartScene(this));
        addScene(SCENE_GAME, new GameScene(this));
    }

    public void startNewGame() {
        startNewGame(true);
    }

    public void startNewGame(boolean skipOpening) {
        addScene(SCENE_GAME, new GameScene(this, skipOpening));
        setActiveScene(SCENE_GAME);
    }

    public void triggerGameOver(int finalScore, String finalTime) {
        addScene(SCENE_GAME_OVER, new GameOverScene(this, finalScore, finalTime));
        setActiveScene(SCENE_GAME_OVER);
    }
}