package Frames.TableFrame;

import Connection.SocketClient;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import Exception.PartUserNameException;

/**
 * Created by SZYMON on 28.11.2016.
 */
public class ButtonRefreshAdapter implements ActionListener {

    TableFrame frame;
    SocketClient client;

    public ButtonRefreshAdapter( TableFrame newframe, SocketClient newclient) {
        frame=newframe;
        client=newclient;
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
