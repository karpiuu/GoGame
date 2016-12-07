package Frames.GameFrame;

import Connection.SocketClient;
import Frames.LobbyFrame.LobbyFrame;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * Created by SZYMON on 07.12.2016.
 */
public class GameFrameClosingAdapter implements WindowListener
{
    SocketClient client;
    LobbyFrame lobbyFrame;

    public GameFrameClosingAdapter( SocketClient newclient, LobbyFrame newlobbyFrame){
        client = newclient;
        lobbyFrame = newlobbyFrame;
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(java.awt.event.WindowEvent windowEvent) {

        client.out.println("StandUp" + ";");

//        if (JOptionPane.showConfirmDialog(frame,
//        "Are you sure to close this window?", "Really Closing?",
//        JOptionPane.YES_NO_OPTION,
//        JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION)
//        {
//        System.exit(0);
//        }
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
