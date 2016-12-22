package Frames.GameFrame;

import Connection.SocketClient;
import GameEngine.GameEngine;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Adapter to the commit button
 */
public class CommitDeadStoneAdapter implements ActionListener {

    private SocketClient client;
    private GameFrame gameFrame;
    private GameEngine gameEngine;

    public CommitDeadStoneAdapter(SocketClient newclient, GameFrame gameFrame, GameEngine gameEngine){
        client = newclient;
        this.gameFrame = gameFrame;
        this.gameEngine = gameEngine;
    }

    /**
     * Function sets game to state of the commiting by users.
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        client.sendMessage( "DeadStone;" + gameEngine.getAllYoursDeadStones() );
        gameEngine.setYouSelect(false);

        String line;
        line = client.readFromInput();

        if(line.equals("OK")) {
            gameFrame.setWaitForRespond();
        }
        else {
            JOptionPane.showMessageDialog(null, line);
        }
    }
}
