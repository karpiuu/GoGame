package Frames.LobbyFrame;

import Connection.OpponentSignalObserver;
import Connection.SocketClient;
import Frames.GameFrame.GameFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Creating new game frame
 */
public class ButtonCreateTableAdapter implements ActionListener {

    private SocketClient client;
    private LobbyFrame lobbyFrame;
    private OpponentSignalObserver opponentObserver;
    private String yourName;

    public ButtonCreateTableAdapter(SocketClient newClient, LobbyFrame newlobbyFrame, OpponentSignalObserver opponentObserver, String yourName) {
        client = newClient;
        lobbyFrame = newlobbyFrame;
        this.opponentObserver = opponentObserver;
        this.yourName = yourName;
    }

    /**
     * After click on this button, new game is creating
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        client.sendMessage("CreateTable;");

        String line;

        line = client.readFromInput();

        if(line.equals("OK")) {

            GameFrame gameFrame = new GameFrame(client, lobbyFrame, yourName);
            gameFrame.init();

            opponentObserver.setObserver(gameFrame);

            lobbyFrame.setVisible(false);
        }
    }
}
