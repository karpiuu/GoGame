package Tests;

import Source.Connection.BotConnection;
import Source.Connection.Server;
import Source.Game.GameEngine;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BotConnectionTest {
    BotConnection bot;

    @Before
    public void setUp() throws Exception {
        Server server = new Server();
        GameEngine gameEngine = new GameEngine(19);

        bot = new BotConnection(server, 0);
        bot.setGameEngine(gameEngine);
    }

    @Test
    public void sendMessageToUser1() throws Exception {
        bot.sendMessageToUser("Stone;W;20");

        String line = bot.readBot();

        assertEquals(line.substring(0, line.indexOf(";")), "Stone");
    }

    @Test
    public void sendMessageToUser2() throws Exception {
        bot.sendMessageToUser("Pass;");

        String line = bot.readBot();

        assertEquals(line.substring(0, line.indexOf(";")), "Pass");
    }

    @Test
    public void sendMessageToUser4() throws Exception {
        bot.sendMessageToUser("NoDeadStone;");

        String line = bot.readBot();

        assertEquals(line.substring(0, line.indexOf(";")), "DeadStone");
    }

    @Test
    public void sendMessageToUser5() throws Exception {
        bot.sendMessageToUser("TerritoryCheck;");

        String line = bot.readBot();

        assertEquals(line.substring(0, line.indexOf(";")), "YesTerritory");
    }

}