package mx.itesm.meteorlag;

/**
 * Created by Adrian on 3/10/15.
 */
public interface GameMechanics
{
    public enum GameStatus {
        GAME_STARTING,
        GAME_PLAYING,
        GAME_PAUSED,
        GAME_ENDING,
        GAME_OVER,
        GAME_WON,
        TRANSITION_STATUS,
    }

    void loadLevel(Level level); //Loads Every Asset of the level

    void startGameplay();
    void pauseGameplay();
    void resumeGameplay();
    void endGameplay();

    void winGame();
    void gameOver();

}
