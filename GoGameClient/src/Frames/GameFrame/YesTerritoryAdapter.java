package Frames.GameFrame;

import Connection.SocketClient;
import GameEngine.GameEngine;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Adapter to the answer yes : territory statment
 */
public class YesTerritoryAdapter implements ActionListener {
    private SocketClient client;
    private GameFrame gameFrame;
    private GameEngine gameEngine;

    public YesTerritoryAdapter(SocketClient newclient, GameFrame gameFrame, GameEngine gameEngine){
        client = newclient;
        this.gameEngine = gameEngine;
        this.gameFrame = gameFrame;
    }


    /**
     * Clicking on the yes button
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        client.sendMessage("YesTerritory;");
        gameFrame.setWaitForRespond();

        String line = client.readFromInput();

        if(!line.equals("OK")) {
            JOptionPane.showMessageDialog(null, line);
        }
    }
}
