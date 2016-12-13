package Frames.LobbyFrame;

import Connection.SocketClient;
import Frames.GameFrame.GameFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ButtonCreateTableAdapter implements ActionListener {

    SocketClient client;
    LobbyFrame lobbyFrame;

    public ButtonCreateTableAdapter(SocketClient newClient, LobbyFrame newlobbyFrame) {
        client = newClient;
        lobbyFrame = newlobbyFrame;

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        client.sendMessage("CreateTable;");

        String line;

        line = client.readFromInput();

        if(line.equals("OK")) {
<<<<<<< HEAD
            GameFrame gameFrame = new GameFrame(client, lobbyFrame );
            gameFrame.init();
=======
                GameFrame gameFrame = new GameFrame(client, lobbyFrame );

>>>>>>> origin/master
            lobbyFrame.setVisible(false);
        }
    }
}
