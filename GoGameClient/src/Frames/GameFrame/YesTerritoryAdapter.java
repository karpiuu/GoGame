package Frames.GameFrame;

import Connection.SocketClient;
import GameEngine.GameEngine;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class YesTerritoryAdapter implements ActionListener {
    private SocketClient client;
    private GameFrame gameFrame;
    private GameEngine gameEngine;

    public YesTerritoryAdapter(SocketClient newclient, GameFrame gameFrame, GameEngine gameEngine){
        client = newclient;
        this.gameEngine = gameEngine;
        this.gameFrame = gameFrame;
    }

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
