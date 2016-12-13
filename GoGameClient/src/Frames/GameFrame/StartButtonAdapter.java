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

    public StartButtonAdapter(SocketClient client, GameEngine gameEngine, JButton startButton, JButton surrenderButton, JButton passButton) {
        this.client = client;
        this.gameEngine = gameEngine;
        this.startButton = startButton;
        this.surrenderButton = surrenderButton;
        this.passButton = passButton;
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
        passButton.setVisible(true);
        startButton.setVisible(false);
    }
}
