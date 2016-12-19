package GameEngine;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Created by SZYMON on 19.12.2016.
 */
public class GameEngineTest {

    @Test
    public void place() throws Exception {

        GameEngine gameEngine = new GameEngine(19);

        assertEquals(true, gameEngine.place(5,5, Stone.BLACK));
        assertEquals(false, gameEngine.place(5,-1, Stone.BLACK));
    }

    @Test
    public void place1() throws Exception {

        GameEngine gameEngine = new GameEngine(19);
        String line;
        line = "Stone;B;1;";
        gameEngine.place(line);

        assertEquals(Stone.EMPTY, gameEngine.getStone(0,1));
    }

    @Test
    public void getStone() throws Exception {

        GameEngine gameEngine = new GameEngine(19);
        gameEngine.place(1,1, Stone.BLACK);

        assertEquals(Stone.BLACK, gameEngine.getStone(1,1) );
        assertEquals(Stone.EMPTY, gameEngine.getStone(1,2) );
    }

    @Test
    public void getPlayerStone() throws Exception {

    }

    @Test
    public void setPlayerStone() throws Exception {

    }

    @Test
    public void startGame() throws Exception {

    }

    @Test
    public void endGame() throws Exception {

    }

    @Test
    public void changeTurn() throws Exception {

    }

    @Test
    public void getGameStart() throws Exception {

    }

    @Test
    public void getYourTurn() throws Exception {

    }

}