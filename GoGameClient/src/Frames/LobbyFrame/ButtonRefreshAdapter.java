package Frames.LobbyFrame;

import Connection.SocketClient;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


/**
 * Created by SZYMON on 28.11.2016.
 */
public class ButtonRefreshAdapter implements ActionListener {

    private LobbyFrame frame;
    private SocketClient client;

    public ButtonRefreshAdapter(SocketClient newclient, LobbyFrame newFrame) {
        frame = newFrame;
        client = newclient;
    }

    public void actionPerformed(ActionEvent e) {

        client.out.println("Refresh;");

        String line;

        try {
            line = client.in.readLine();
        } catch (IOException e2) {
            e2.printStackTrace();
            return;
        }

        frame.refreshUserList(line);
    }

}
