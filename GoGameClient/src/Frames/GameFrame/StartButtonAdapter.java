package Frames.GameFrame;

import Connection.SocketClient;
import GameEngine.GameEngine;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import GameEngine.Stone;

/**
 * Adapter to the start button
 */
public class StartButtonAdapter implements ActionListener {

    private SocketClient client;
    private GameEngine gameEngine;
    private GameFrame gameFrame;

    public StartButtonAdapter(SocketClient client, GameFrame gameFrame, GameEngine gameEngine) {
        this.client = client;
        this.gameEngine = gameEngine;
        this.gameFrame = gameFrame;
    }

    /**
     * Users has to click on the start to start game
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String line;

        client.sendMessage("StartGame;");

        line = client.readFromInput();

        if(line.equals("OK")) {
            line = client.readFromInput();

            gameFrame.setStartGame(line);
        }
        else {
            JOptionPane.showMessageDialog(null, line, "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }


    }
}
