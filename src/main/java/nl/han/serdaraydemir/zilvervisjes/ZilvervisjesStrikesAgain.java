package nl.han.serdaraydemir.zilvervisjes;

import com.github.hanyaeger.api.Size;
import com.github.hanyaeger.api.YaegerGame;
import nl.han.serdaraydemir.zilvervisjes.scenes.GameOverScene;
import nl.han.serdaraydemir.zilvervisjes.scenes.GameScene;
import nl.han.serdaraydemir.zilvervisjes.scenes.HelpScene;
import nl.han.serdaraydemir.zilvervisjes.scenes.StartScene;

/**
 * Hoofdklasse en startpunt van de applicatie. Erft van Yaeger's
 * YaegerGame en registreert de vier scenes (start, hulp, spel,
 * game-over). Biedt methoden om vanuit andere scenes te schakelen
 * naar het hoofdspel of het game-overscherm.
 */
public class ZilvervisjesStrikesAgain extends YaegerGame {

    public static final int SCENE_START = 0;
    public static final int SCENE_GAME = 1;
    public static final int SCENE_GAME_OVER = 2;
    public static final int SCENE_HELP = 3;

    /**
     * Opstartpunt van de applicatie. Start de Yaeger-runtime.
     *
     * @param args command line argumenten (worden niet gebruikt)
     */
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
        addScene(SCENE_HELP, new HelpScene(this));
    }

    /**
     * Start een nieuwe spelsessie en slaat daarbij de openingssequentie
     * over. Wordt aangeroepen bij een herstart vanuit het game-overscherm.
     */
    public void startNewGame() {
        startNewGame(true);
    }

    /**
     * Start een nieuwe spelsessie waarbij de aanroeper expliciet kan
     * kiezen of de openingssequentie wordt overgeslagen.
     *
     * @param skipOpening true om de openingssequentie over te slaan,
     *                    false om deze volledig af te spelen
     */
    public void startNewGame(boolean skipOpening) {
        addScene(SCENE_GAME, new GameScene(this, skipOpening));
        setActiveScene(SCENE_GAME);
    }

    /**
     * Schakelt over naar het game-overscherm met de eindresultaten
     * van de afgelopen spelsessie.
     *
     * @param finalScore eindscore van de speler
     * @param finalTime overleefde tijd, geformatteerd als MM:SS
     */
    public void triggerGameOver(int finalScore, String finalTime) {
        addScene(SCENE_GAME_OVER, new GameOverScene(this, finalScore, finalTime));
        setActiveScene(SCENE_GAME_OVER);
    }
}