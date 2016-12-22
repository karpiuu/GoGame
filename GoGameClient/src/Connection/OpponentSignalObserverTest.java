package Connection;

import Frames.GameFrame.GameFrame;
import Frames.LobbyFrame.LobbyFrame;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by SZYMON on 22.12.2016.
 */
public class OpponentSignalObserverTest {
    @Test
    public void checkLineTest() throws Exception {


        OpponentSignalObserver opponentSignalObserver = new OpponentSignalObserver();
        SocketClient a = new SocketClient(opponentSignalObserver);
        String string = "";
        LobbyFrame b = new LobbyFrame(a, opponentSignalObserver,string);
        GameFrame gameFrame = new GameFrame(a,b,string);
        String line;
        line = "Stone";

        assertTrue(opponentSignalObserver.checkLine(line));
    }



}