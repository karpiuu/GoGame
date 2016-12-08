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
        client.out.println("CreateTable;");

        String line;

        try {
            line = client.in.readLine();
        } catch (IOException e2) {
            e2.printStackTrace();
            return;
        }

        if(line.equals("OK")) {
                GameFrame gameFrame = new GameFrame(client, lobbyFrame );

            lobbyFrame.setVisible(false);
        }
    }
}
