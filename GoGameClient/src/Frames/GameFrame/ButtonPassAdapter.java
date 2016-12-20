package Frames.GameFrame;

import Connection.SocketClient;
import GameEngine.GameEngine;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonPassAdapter implements ActionListener {

    private SocketClient client;
    private GameFrame gameFrame;
    private GameEngine gameEngine;

    public ButtonPassAdapter(SocketClient newclient, GameFrame gameFrame, GameEngine gameEngine){
        client = newclient;
        this.gameFrame = gameFrame;
        this.gameEngine = gameEngine;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        client.sendMessage("Pass;");

        String line;
        line = client.readFromInput();

        if(line.equals("OK")) {
            gameFrame.changeTurn();
        }
        else if( line.contains("GameEnd") ) {
            gameEngine.setGameEnd(true);
            JOptionPane.showMessageDialog(null, "Game end adapter");
        }
        else {
            JOptionPane.showMessageDialog(null, line);
        }
    }
}
