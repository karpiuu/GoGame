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

        client.sendMessage("Refresh;");

        String line;
        line = client.readFromInput();

        frame.refreshUserList(line.substring( 0, line.indexOf("Tables") ));
        frame.refreshTableList(line.substring( line.indexOf("Tables") ));
    }
}