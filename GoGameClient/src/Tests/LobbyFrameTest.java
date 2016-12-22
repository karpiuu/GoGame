package Tests;

import GameEngine.SignalOperation;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class LobbyFrameTest {
    @org.junit.Test
    public void parseString() throws Exception {
        ArrayList<String> text = SignalOperation.parseString("Tables;#0;Tomasz;;#1;;Szymon;");
        assertEquals("#0", text.get(0));
    }
}