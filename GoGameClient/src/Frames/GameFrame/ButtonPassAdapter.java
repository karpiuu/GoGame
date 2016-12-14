package Frames.GameFrame;

import Connection.SocketClient;
import GameEngine.GameEngine;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonPassAdapter implements ActionListener {

    private SocketClient client;
    private GameEngine gameEngine;

    public ButtonPassAdapter(SocketClient newclient, GameEngine gameEngine){
        client = newclient;
        this.gameEngine = gameEngine;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        client.sendMessage("Pass;");

        String line;
        line = client.readFromInput();

        if(line.equals("OK")) {
            gameEngine.changeTurn();
        }
        else {
            JOptionPane.showMessageDialog(null, line);
        }
    }
}
