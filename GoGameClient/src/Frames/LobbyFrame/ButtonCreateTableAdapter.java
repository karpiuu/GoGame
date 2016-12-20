package Frames.LobbyFrame;

import Connection.OpponentSignalObserver;
import Connection.SocketClient;
import Frames.GameFrame.GameFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ButtonCreateTableAdapter implements ActionListener {

    private SocketClient client;
    private LobbyFrame lobbyFrame;
    private OpponentSignalObserver opponentObserver;

    public ButtonCreateTableAdapter(SocketClient newClient, LobbyFrame newlobbyFrame, OpponentSignalObserver opponentObserver) {
        client = newClient;
        lobbyFrame = newlobbyFrame;
        this.opponentObserver = opponentObserver;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        client.sendMessage("CreateTable;");

        String line;

        line = client.readFromInput();

        if(line.equals("OK")) {

            GameFrame gameFrame = new GameFrame(client, lobbyFrame);
            gameFrame.init();

            opponentObserver.setObserver(gameFrame);

            lobbyFrame.setVisible(false);
        }
    }
}
