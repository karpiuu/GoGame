package Frames.GameFrame;

import Connection.SocketClient;
import Frames.LobbyFrame.LobbyFrame;
import GameEngine.*;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {
    private SocketClient client;

    private JPanel panel1;
    private JButton startButton;
    private JButton surrenderButton;
    private JButton passButton;
    private JLabel turn1;
    private JLabel turn2;
    private JLabel color;
    private JLabel colorLabel;
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

        startButton.addActionListener( new StartButtonAdapter(client, gameEngine, startButton, surrenderButton,
                                                              passButton, turn1, turn2, colorLabel, color) );
        passButton.addActionListener(new ButtonPassAdapter(client,this));
    }

    public void notifyGame(String line) {
        if( line.contains("Pass") || line.contains("ChangeTurn")) {
            changeTurn();
        }
        else if( line.contains("Stone")) {
            gameViewPanel.getGameEngine().place(line);
            changeTurn();

            gameViewPanel.repaint();
        }
        else if(line.contains("StartGame")) {

            gameEngine.startGame();

            if(line.contains("Black")) {
                gameEngine.setPlayerStone( Stone.BLACK );
                color.setText("Black");
                gameEngine.changeTurn();
            }
            else {
                gameEngine.setPlayerStone( Stone.WHITE );
                color.setText("White");
            }

            surrenderButton.setVisible(true);
            surrenderButton.setEnabled(true);
            passButton.setVisible(true);
            passButton.setEnabled(true);
            startButton.setVisible(false);
            turn1.setVisible(true);
            turn2.setVisible(true);
            colorLabel.setVisible(true);
            color.setVisible(true);
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