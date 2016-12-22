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
            gameFrame.setFooterText("You passed. Now your opponent makes move.");
            gameEngine.changeTurn();
            gameEngine.clearLastMove();
            gameFrame.repaintPanel();
        }
        else if( line.contains("GamePassEnd") ) {
            gameFrame.setEndGame();
        }
    }
}
