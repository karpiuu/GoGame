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
    private SocketClient client;
    private JLabel blackP;
    private JLabel whiteP;
    private JLabel actualTurn;

    public FieldClickAdapter(SocketClient newClient, int x, int y, GameEngine gameEngine, GameViewPanel gameViewPanel,
                             JLabel blackP, JLabel whiteP, JLabel actualTurn) {
        this.blackP = blackP;
        this.whiteP = whiteP;

        client = newClient;
        this.x = x;
        this.y = y;
        this.gameEngine = gameEngine;
        gamePanel = gameViewPanel;
        this.actualTurn = actualTurn;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

        if( gameEngine.getGameEnd() ) {
            if( gameEngine.isYouSelect() ) {
                if( gameEngine.selectDeadStone( x, y, gameEngine.getOpponentStone() ) ) {
                    gamePanel.repaint();
                }
            }
        }
        else if(gameEngine.getGameStart() ) {
            if (gameEngine.getYourTurn()) {

                client.sendMessage("Stone;" + gameEngine.getPlayerStone().toString() + ";" + Integer.toString(getMove()) + ";");

                actualTurn.setText("You place stone. Now your opponent makes move.");

                String line = client.readFromInput();

                if (line.contains("YourMove")) {
                    gameEngine.changeTurn();

                    gameEngine.place(line.substring(0, line.indexOf("Points")));
                    gameEngine.changePoints(line.substring(line.indexOf("Points")));

                    blackP.setText(gameEngine.getPointsBlack().toString());
                    whiteP.setText(gameEngine.getPointsWhite().toString());

                    gamePanel.repaint();
                } else {
                    JOptionPane.showMessageDialog(null, line);
                }
            }
            else {
                JOptionPane.showMessageDialog(null, "Wait for your turn");
            }
        }
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
