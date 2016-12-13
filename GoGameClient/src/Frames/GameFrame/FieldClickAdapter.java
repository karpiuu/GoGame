package Frames.GameFrame;

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

    public FieldClickAdapter(int x, int y, GameEngine gameEngine, GameViewPanel gameViewPanel) {
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
            gameEngine.place(x, y, gameEngine.getPlayerStone());
            gamePanel.repaint();
        }
        //JOptionPane.showMessageDialog(null, "You click on x: " + x.toString() + " y: " + y.toString());
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
