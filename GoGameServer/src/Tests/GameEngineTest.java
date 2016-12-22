package Tests;

import Source.Game.GameEngine;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GameEngineTest {

    GameEngine gameEngine = new GameEngine(19);

    @Test
    public void userPass() throws Exception {
        gameEngine.userPass();
        assertEquals(gameEngine.getUserPass(), 1);
    }

    @Test
    public void getUserPass() throws Exception {
        gameEngine.userPass();
        assertEquals(gameEngine.getUserPass(), 1);
    }

    @Test
    public void convertMove() throws Exception {
        assertEquals(gameEngine.convertMove(20)[0], 1);
    }

}