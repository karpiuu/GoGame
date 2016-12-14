package Frames.GameFrame;

import Connection.SocketClient;
import GameEngine.GameEngine;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import GameEngine.Stone;

public class FieldClickAdapter implements MouseListener {

    private Integer x;
    private Integer y;
    private GameEngine gameEngine;
    private JPanel gamePanel;
    SocketClient client;

    public FieldClickAdapter(SocketClient newClient, int x, int y, GameEngine gameEngine, GameViewPanel gameViewPanel) {
        client = newClient;
        this.x = x;
        this.y = y;
        this.gameEngine = gameEngine;
        gamePanel = gameViewPanel;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

        if( gameEngine.getYourTurn() ) {
            client.sendMessage( "Stone;" + gameEngine.getPlayerStone().toString() + ";" + Integer.toString(getMove()) + ";");

            String line = client.readFromInput();

            if(line.contains("Stone")) {
                gameEngine.changeTurn();
                gameEngine.place(line);
                gamePanel.repaint();
            }
            else {
                JOptionPane.showMessageDialog(null, line);
            }
        }
        //JOptionPane.showMessageDialog(null, "You click on x: " + x.toString() + " y: " + y.toString());
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    private int getMove() {
        int value = 0;

        value += x;
        value += 19*y;

        return value;
    }
}
