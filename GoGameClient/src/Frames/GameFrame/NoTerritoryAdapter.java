package Frames.GameFrame;

import Connection.SocketClient;
import GameEngine.GameEngine;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Adapter to the answer no : territory statment
 */
public class NoTerritoryAdapter implements ActionListener{
    private SocketClient client;
    private GameFrame gameFrame;
    private GameEngine gameEngine;

    public NoTerritoryAdapter(SocketClient newclient, GameFrame gameFrame, GameEngine gameEngine){
        client = newclient;
        this.gameEngine = gameEngine;
        this.gameFrame = gameFrame;
    }

    /**
     * Clicking on the no button
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        client.sendMessage("NoTerritory;");
        gameFrame.setWaitForRespond();

        String line = client.readFromInput();

        if( line.contains("OK")) {
            gameFrame.setEndGame();
        }
        else {
            JOptionPane.showMessageDialog(null, line);
        }
    }
}
