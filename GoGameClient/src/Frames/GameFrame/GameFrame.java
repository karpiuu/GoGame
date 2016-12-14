package Frames.GameFrame;

import Connection.SocketClient;
import Frames.LobbyFrame.LobbyFrame;
import GameEngine.GameEngine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameFrame extends JFrame {
    private SocketClient client;

    private JPanel panel1;
    private JButton startButton;
    private JButton surrenderButton;
    private JButton passButton;
    private LobbyFrame lobbyFrame;
    private GameViewPanel gameViewPanel;
    private GameEngine gameEngine;

    public GameFrame(SocketClient newClient, LobbyFrame newlobbyFrame) {
        super("Go game");

        client = newClient;
        lobbyFrame = newlobbyFrame;

        gameEngine = new GameEngine(19);
        setContentPane(panel1);

        addWindowListener(new GameFrameClosingAdapter(client, lobbyFrame));

        pack();

        setSize(800,800);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
        // surrenderButton.addActionListener(new ButtonSurenderAdapter(client));
        passButton.addActionListener(new ButtonPassAdapter(client,gameEngine));
    }

    public void init() {
        gameViewPanel = new GameViewPanel(client, 800,750, gameEngine);
        panel1.add( gameViewPanel, BorderLayout.CENTER );

        startButton.addActionListener( new StartButtonAdapter(client, gameEngine, startButton, surrenderButton, passButton) );
    }

    public void notifyGame(String line) {
        if( line.contains("Pass")) {
            gameEngine.changeTurn();
        }
        if( line.contains("Stone")) {
            gameViewPanel.getGameEngine().place(line);
            gameEngine.changeTurn();
            gameViewPanel.repaint();
        }
    }
}