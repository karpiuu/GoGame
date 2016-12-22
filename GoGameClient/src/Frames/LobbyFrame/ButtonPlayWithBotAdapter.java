package Frames.LobbyFrame;

import Connection.OpponentSignalObserver;
import Connection.SocketClient;
import Frames.GameFrame.GameFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Creating new game frame
 */
public class ButtonPlayWithBotAdapter implements ActionListener {

    private SocketClient client;
    private LobbyFrame lobbyFrame;
    private OpponentSignalObserver opponentObserver;
    private String yourName;

    public ButtonPlayWithBotAdapter(SocketClient newClient, LobbyFrame newlobbyFrame, OpponentSignalObserver opponentObserver, String yourName) {
        client = newClient;
        lobbyFrame = newlobbyFrame;
        this.opponentObserver = opponentObserver;
        this.yourName = yourName;
    }

    /**
     * Creating new game with the bot
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        client.sendMessage("CreateBotTable;");

        String line;

        line = client.readFromInput();

        if(line.equals("OK")) {

            GameFrame gameFrame = new GameFrame(client, lobbyFrame, yourName);
            gameFrame.init();
            opponentObserver.setObserver(gameFrame);

            gameFrame.setStartGame("StartGame;Black;");

            lobbyFrame.setVisible(false);
        }
    }
}
