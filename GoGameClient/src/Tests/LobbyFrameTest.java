package Tests;

import Frames.LobbyFrame.LobbyFrame;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class LobbyFrameTest {
    @org.junit.Test
    public void parseString() throws Exception {
        LobbyFrame lobbyFrame = new LobbyFrame(null);
        ArrayList<String> text = lobbyFrame.parseString("Tables;#0;Tomasz;;#1;;Szymon;");
        assertEquals("#0", text.get(0));
    }
}