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
    private JLabel blackP;
    private JLabel whiteP;
    private JLabel bLabelPoints;
    private JLabel wLabelPoints;
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
        gameViewPanel = new GameViewPanel(client, 800,750, gameEngine, turn2, blackP, whiteP);
        panel1.add( gameViewPanel, BorderLayout.CENTER );

        startButton.addActionListener( new StartButtonAdapter(client, this, gameEngine) );
        passButton.addActionListener(new ButtonPassAdapter(client, this, gameEngine));
    }

    public void notifyGame(String line) {
        if( line.equals("Pass;") || line.equals("ChangeTurn;")) {
            changeTurn();
        }
        else if( line.contains("Stone")) {
            gameViewPanel.getGameEngine().place(line.substring( 0, line.indexOf("Points") ));
            gameEngine.changePoints(line.substring(line.indexOf("Points")));

            blackP.setText( gameEngine.getPointsBlack().toString() );
            whiteP.setText( gameEngine.getPointsWhite().toString() );

            changeTurn();

            gameViewPanel.repaint();
        }
        else if(line.contains("StartGame;")) {
            setStartGame(line);
        }
        else if(line.equals("GameEnd;")) {
            setEndGame();
        }
    }

    public void setStartGame(String line) {

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
        passButton.setVisible(true);
        startButton.setVisible(false);
        turn1.setVisible(true);
        turn2.setVisible(true);
        colorLabel.setVisible(true);
        color.setVisible(true);
        bLabelPoints.setVisible(true);
        wLabelPoints.setVisible(true);
        blackP.setVisible(true);
        whiteP.setVisible(true);
    }

    public void setEndGame() {
        gameEngine.setGameEnd(true);
        JOptionPane.showMessageDialog(null, "Game end");
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