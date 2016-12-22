package Frames.GameFrame;

import Connection.SocketClient;
import GameEngine.GameEngine;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by SZYMON on 14.12.2016.
 */

/**
 * Adapter to the surrender button
 */
public class ButtonSurenderAdapter implements ActionListener {
    private SocketClient client;
    private GameFrame gameFrame;
    private GameEngine gameEngine;

    public ButtonSurenderAdapter(SocketClient newclient, GameFrame gameFrame, GameEngine gameEngine){
        client = newclient;
        this.gameFrame = gameFrame;
        this.gameEngine = gameEngine;
    }

    /**
     * Function sets game to state of the surrender by users.
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        client.sendMessage("Surrender;");

        String line;
        line = client.readFromInput();

        if(!line.equals("OK")) {
            JOptionPane.showMessageDialog(null, line);
        }

        gameFrame.setToStart();
    }
}
