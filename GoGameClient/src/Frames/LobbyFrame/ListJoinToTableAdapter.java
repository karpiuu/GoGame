package Frames.LobbyFrame;

import Connection.SocketClient;
import Frames.GameFrame.GameFrame;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

/**
 * Created by SZYMON on 07.12.2016.
 */
public class ListJoinToTableAdapter implements MouseListener {

    SocketClient client;
    LobbyFrame lobbyFrame;
    JList tableList;

    public ListJoinToTableAdapter(SocketClient newClient, LobbyFrame newlobbyFrame, JList newtableList){
        client = newClient;
        lobbyFrame = newlobbyFrame;
        tableList = newtableList;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        if (e.getClickCount() == 2 )
        {
            int index = tableList.locationToIndex(e.getPoint());

            client.out.println("SitDown;" + index + ";");

            String line;

            try {
                line = client.in.readLine();
            } catch (IOException e2) {
                e2.printStackTrace();
                return;
            }

            if(line.equals("OK")) {
                GameFrame gameFrame = new GameFrame(client, lobbyFrame);
                lobbyFrame.setVisible(false);
            }

        }
    }


    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
