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
    private JLabel turn1;
    private JLabel turn2;
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
    }

    public void init() {
        gameViewPanel = new GameViewPanel(client, 800,750, gameEngine, turn2);
        panel1.add( gameViewPanel, BorderLayout.CENTER );

        startButton.addActionListener( new StartButtonAdapter(client, gameEngine, startButton, surrenderButton, passButton, turn1, turn2) );
        passButton.addActionListener(new ButtonPassAdapter(client,this));
    }

    public void notifyGame(String line) {
        if( line.contains("Pass") || line.contains("ChangeTurn")) {
            changeTurn();
        }

        if( line.contains("Stone")) {
            gameViewPanel.getGameEngine().place(line);
            changeTurn();

            gameViewPanel.repaint();
        }
    }

    public void changeTurn() {
        if(turn2.getText().equals("Black")) {
            turn2.setText("White");
        }
        else {
            turn2.setText("Black");
        }

        gameEngine.changeTurn();
    }
}