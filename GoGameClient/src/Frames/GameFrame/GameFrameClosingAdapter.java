package Frames.GameFrame;

import Connection.SocketClient;
import Frames.LobbyFrame.LobbyFrame;
import GameEngine.GameEngine;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * Created by SZYMON on 07.12.2016.
 */

/**
 * Adapter to the closing game frame
 */
public class GameFrameClosingAdapter implements WindowListener
{
    private SocketClient client;
    private LobbyFrame lobbyFrame;
    private GameEngine gameEngine;

    public GameFrameClosingAdapter(SocketClient newclient, LobbyFrame newlobbyFrame, GameEngine gameEngine){
        client = newclient;
        lobbyFrame = newlobbyFrame;
        this.gameEngine = gameEngine;
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    /**
     * Function sets frame to the closing by user.
     * @param windowEvent
     */
    @Override
    public void windowClosing(java.awt.event.WindowEvent windowEvent) {

        if (JOptionPane.showConfirmDialog(null, "Are you sure to close this window?", "Really Closing?",
           JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION)
        {
            if(gameEngine.getGameStart()) {
                client.sendMessage("Surrender;");

                String line;
                line = client.readFromInput();

                if(!line.equals("OK")) {
                    JOptionPane.showMessageDialog(null, line);
                }
            }

            client.sendMessage("StandUp;");

            client.sendMessage("Refresh;");

            String line;
            line = client.readFromInput();

            lobbyFrame.refreshUserList(line.substring( 0, line.indexOf("Tables") ));
            lobbyFrame.refreshTableList(line.substring( line.indexOf("Tables") ));

            lobbyFrame.setVisible(true);
        }


    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
