package Frames.LobbyFrame;

import Connection.OpponentSignalObserver;
import Connection.SocketClient;
import Frames.GameFrame.GameFrame;
import GameEngine.SignalOperation;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by SZYMON on 07.12.2016.
 */

/**
 * User wants to join to the table which is made
 */

public class ListJoinToTableAdapter implements MouseListener {

    private SocketClient client;
    private LobbyFrame lobbyFrame;
    private JList tableList;
    private OpponentSignalObserver opponentObserver;
    private String yourName;

    public ListJoinToTableAdapter(SocketClient newClient, LobbyFrame newlobbyFrame, JList newtableList, OpponentSignalObserver opponentObserver, String yourName){
        client = newClient;
        lobbyFrame = newlobbyFrame;
        tableList = newtableList;
        this.opponentObserver = opponentObserver;
        this.yourName = yourName;
    }

    /**
     * After double clicking on the table, server gives respond and then game frame is made for user.
     * @param e
     */
    @Override
    public void mouseClicked(MouseEvent e) {

        if (e.getClickCount() == 2 )
        {
            int index = tableList.locationToIndex(e.getPoint());
            String value = tableList.getModel().getElementAt(index).toString();
            value = value.substring( value.indexOf("#")+1, value.indexOf(" "));

            client.sendMessage("SitDown;" + value + ";");

            String line;

            line = client.readFromInput();

            if(line.equals("OK") || line.contains("OpponentName")) {
                ArrayList<String> name = SignalOperation.parseString(line);

                GameFrame gameFrame = new GameFrame(client, lobbyFrame, yourName);
                gameFrame.init();
                gameFrame.setOpponentName(name.get(0));

                opponentObserver.setObserver(gameFrame);
                lobbyFrame.setVisible(false);
            }
            else {
                JOptionPane.showMessageDialog(null, line);
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
