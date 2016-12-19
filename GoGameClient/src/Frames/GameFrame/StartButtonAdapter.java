package Frames.GameFrame;

import Connection.SocketClient;
import GameEngine.GameEngine;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import GameEngine.Stone;

public class StartButtonAdapter implements ActionListener {

    private SocketClient client;
    private JButton startButton;
    private JButton surrenderButton;
    private JButton passButton;
    private GameEngine gameEngine;
    private JLabel turn1;
    private JLabel turn2;

    public StartButtonAdapter(SocketClient client, GameEngine gameEngine, JButton startButton, JButton surrenderButton, JButton passButton, JLabel turn1, JLabel turn2) {
        this.client = client;
        this.gameEngine = gameEngine;
        this.startButton = startButton;
        this.surrenderButton = surrenderButton;
        this.passButton = passButton;
        this.turn1 = turn1;
        this.turn2 = turn2;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String line;

        client.sendMessage("StartGame;");

        line = client.readFromInput();

        if(line.equals("OK")) {
            line = client.readFromInput();

            gameEngine.startGame();

            if(line.equals("White")) {
                gameEngine.setPlayerStone( Stone.WHITE );
            }
            else {
                gameEngine.setPlayerStone( Stone.BLACK );
                gameEngine.changeTurn();
            }
        }
        else {
            JOptionPane.showMessageDialog(null, line, "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        surrenderButton.setVisible(true);
        surrenderButton.setEnabled(true);
        passButton.setVisible(true);
        passButton.setEnabled(true);
        startButton.setVisible(false);
        turn1.setVisible(true);
        turn2.setVisible(true);
    }
}
