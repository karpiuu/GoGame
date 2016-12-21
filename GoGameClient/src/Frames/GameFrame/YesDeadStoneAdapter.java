package Frames.GameFrame;

import Connection.SocketClient;
import GameEngine.GameEngine;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class YesDeadStoneAdapter implements ActionListener {

    private SocketClient client;
    private GameFrame gameFrame;
    private GameEngine gameEngine;

    public YesDeadStoneAdapter(SocketClient newclient, GameFrame gameFrame, GameEngine gameEngine){
        client = newclient;
        this.gameEngine = gameEngine;
        this.gameFrame = gameFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        client.sendMessage("YesDeadStone;" + gameEngine.getAllDeadStones());
        gameFrame.setSelect();
    }
}
