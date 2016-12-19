package Frames.GameFrame;

import Connection.SocketClient;
import GameEngine.GameEngine;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonPassAdapter implements ActionListener {

    private SocketClient client;
    private GameFrame gameFrame;

    public ButtonPassAdapter(SocketClient newclient, GameFrame gameFrame){
        client = newclient;
        this.gameFrame = gameFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        client.sendMessage("Pass;");

        String line;
        line = client.readFromInput();

        if(line.equals("OK")) {
            gameFrame.changeTurn();
        }
        else {
            JOptionPane.showMessageDialog(null, line);
        }
    }
}
