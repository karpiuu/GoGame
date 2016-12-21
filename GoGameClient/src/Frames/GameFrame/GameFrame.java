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
    private JLabel blackPoints;
    private JLabel blackLabel;
    private JLabel whiteLabel;
    private JLabel whitePoints;
    private JLabel actualTurn;
    private JLabel ourColor;
    private JLabel opponentColor;
    private JPanel gameButtonsPanel;
    private JPanel startGamePanel;
    private JPanel passGamePanel;
    private JButton commitButton;
    private JButton returnToGameButton;
    private JPanel opponentIsSelectingPanel;
    private JButton yesButton;
    private JButton noButton;
    private JButton returnToGameButton1;
    private JPanel acceptPanel;
    private JPanel scorePanel;
    private JPanel movePanel;
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

        setSize(1000,830);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
        // surrenderButton.addActionListener(new ButtonSurenderAdapter(client));
    }

    public void init() {
        gameViewPanel = new GameViewPanel(client, 1000,730, gameEngine, actualTurn, blackPoints, whitePoints);
        panel1.add( gameViewPanel );

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

            blackPoints.setText( gameEngine.getPointsBlack().toString() );
            whitePoints.setText( gameEngine.getPointsWhite().toString() );

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
            ourColor.setText("Black");
            gameEngine.changeTurn();
        }
        else {
            gameEngine.setPlayerStone( Stone.WHITE );
            ourColor.setText("White");
        }

        startGamePanel.setVisible(false);
        gameButtonsPanel.setVisible(true);
        scorePanel.setVisible(true);
        movePanel.setVisible(true);
    }

    public void setEndGame() {
        gameEngine.setGameEnd(true);

        gameButtonsPanel.setVisible(false);

        if( gameEngine.getPlayerStone().equals(Stone.BLACK)){
            passGamePanel.setVisible(true);
            gameEngine.setYouSelect(true);
        }
        else{
            opponentIsSelectingPanel.setVisible(true);
            gameEngine.setYouSelect(false);
        }

        JOptionPane.showMessageDialog(null, "Game end");
    }

    public void changeTurn() {
        if(actualTurn.getText().equals("Black")) {
            actualTurn.setText("White");
        }
        else {
            actualTurn.setText("Black");
        }

        gameEngine.changeTurn();
    }
}