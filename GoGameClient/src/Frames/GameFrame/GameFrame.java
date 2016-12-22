package Frames.GameFrame;

import Connection.SocketClient;
import Frames.LobbyFrame.LobbyFrame;
import GameEngine.*;

import javax.swing.*;
import java.util.ArrayList;

public class GameFrame extends JFrame {
    private SocketClient client;

    private JPanel panel1;

    private JPanel gameButtonsPanel;
    private JPanel startGamePanel;
    private JPanel commitGamePanel;
    private JPanel opponentIsSelectingPanel;
    private JPanel acceptPanel;
    private JPanel scorePanel;
    private JPanel movePanel;
    private JPanel territoryPanel;

    private JButton startButton;
    private JButton surrenderButton;
    private JButton passButton;
    private JButton commitButton;
    private JButton returnToGameButton;
    private JButton yesButton;
    private JButton noButton;
    private JButton returnToGameButton1;
    private JButton noTerritoryButton;
    private JButton returnToGameButton2;
    private JButton yesTerritoryButton;

    private JLabel blackPoints;
    private JLabel blackLabel;
    private JLabel whiteLabel;
    private JLabel whitePoints;
    private JLabel actualTurn;
    private JLabel whiteName;
    private JLabel blackName;

    private LobbyFrame lobbyFrame;
    private GameViewPanel gameViewPanel;
    private GameEngine gameEngine;

    private String yourName;
    private String opponentName;

    public GameFrame(SocketClient newClient, LobbyFrame newlobbyFrame, String yourName) {
        super("Go game");

        this.yourName = yourName;

        client = newClient;
        lobbyFrame = newlobbyFrame;

        gameEngine = new GameEngine(19);
        setContentPane(panel1);

        addWindowListener(new GameFrameClosingAdapter(client, lobbyFrame, gameEngine));

        pack();

        setSize(800,830);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    public void init() {
        gameViewPanel = new GameViewPanel(client, 800,730, gameEngine, blackPoints, whitePoints, actualTurn, gameEngine.getSize());
        panel1.add( gameViewPanel );

        startButton.addActionListener( new StartButtonAdapter(client, this, gameEngine) );
        passButton.addActionListener(new ButtonPassAdapter(client, this, gameEngine));
        commitButton.addActionListener(new CommitDeadStoneAdapter(client, this, gameEngine));
        yesButton.addActionListener(new YesDeadStoneAdapter(client,this,gameEngine));
        noButton.addActionListener(new NoDeadStoneAdapter(client,this,gameEngine));

        yesTerritoryButton.addActionListener(new YesTerritoryAdapter(client,this,gameEngine));
        noTerritoryButton.addActionListener(new NoTerritoryAdapter(client,this,gameEngine));
        returnToGameButton.addActionListener(new ReturnToGameAdapter(client,this,gameEngine));
        returnToGameButton1.addActionListener(new ReturnToGameAdapter(client,this,gameEngine));
        returnToGameButton2.addActionListener(new ReturnToGameAdapter(client,this,gameEngine));
        surrenderButton.addActionListener(new ButtonSurenderAdapter(client,this,gameEngine));


    }

    public void notifyGame(String line) {
        if( line.equals("Pass;") || line.equals("ChangeTurn;")) {
            setFooterText("Your opponent passed turn.");
            gameEngine.changeTurn();
            gameEngine.clearLastMove();
            gameViewPanel.repaint();
        }
        else if( line.substring(0, line.indexOf(";")).equals("Stone") ) {
            gameViewPanel.getGameEngine().place(line.substring( 0, line.indexOf("Points") ));
            gameEngine.changePoints(line.substring(line.indexOf("Points")));

            blackPoints.setText( gameEngine.getPointsBlack().toString() );
            whitePoints.setText( gameEngine.getPointsWhite().toString() );

            setFooterText("Your opponent place stone.");
            gameEngine.changeTurn();

            gameViewPanel.repaint();
        }
        else if(line.contains("StartGame;")) {
            setStartGame(line);
        }
        else if(line.contains("OpponentJoinTable;")) {

            ArrayList<String> name = SignalOperation.parseString(line);

            opponentName = name.get(0);

            JOptionPane.showMessageDialog(null, opponentName + " joins table.");
        }
        else if(line.equals("GameEnd;")) {
            setEndGame();
        }
        else if(line.equals("ReturnToGame;")) {
            gameEngine.setYourTurn(true);
            setReturnToGame();
        }
        else if(line.contains("YesDeadStone;")) {
            setWaitForRespond();
        }
        else if(line.contains("NoDeadStone;")) {
            setSelect();
        }
        else if(line.contains("DeadStone;")) {
            setRespond(line);
        }
        else if(line.contains("TerritoryCheck;")) {
            gameEngine.setTerritory(line);
            setTerritoryCheck();
        }
        else if(line.contains("GameResult")) {
            JOptionPane.showMessageDialog(null, line.substring( line.indexOf(";")+1 ));
            setToStart();
        }
    }

    public void setToStart() {
        gameButtonsPanel.setVisible(false);
        startGamePanel.setVisible(true);
        opponentIsSelectingPanel.setVisible(false);
        scorePanel.setVisible(false);
        gameEngine.setGameEnd(false);
        gameEngine.setStartGame(false);
    }

    public void setStartGame(String line) {

        gameEngine.setStartGame(true);

        if(line.contains("Black")) {
            gameEngine.setPlayerStone( Stone.BLACK );
            gameEngine.setTurn(true);
            blackName.setText(yourName);
            whiteName.setText(opponentName);
            setFooterText("You start game, make move.");
        }
        else {
            gameEngine.setPlayerStone( Stone.WHITE );
            gameEngine.setTurn(false);
            whiteName.setText(yourName);
            blackName.setText(opponentName);
            setFooterText("Your opponent starts game, please wait for move.");
        }

        startGamePanel.setVisible(false);
        gameButtonsPanel.setVisible(true);
        scorePanel.setVisible(true);
        movePanel.setVisible(true);
    }

    public void setEndGame() {
        gameEngine.setGameEnd(true);
        gameEngine.clearLastMove();
        gameViewPanel.repaint();

        gameButtonsPanel.setVisible(false);
        territoryPanel.setVisible(false);
        gameEngine.setTerritoryCheck(false);

        if( gameEngine.getPlayerStone().equals(Stone.BLACK)){
            setSelect();
        }
        else{
            opponentIsSelectingPanel.setVisible(true);
            gameEngine.setYouSelect(false);
        }
    }

    public void setSelect() {
        opponentIsSelectingPanel.setVisible(false);
        acceptPanel.setVisible(false);
        commitGamePanel.setVisible(true);
        gameEngine.setYouSelect(true);
    }

    public void setWaitForRespond() {
        territoryPanel.setVisible(false);
        acceptPanel.setVisible(false);
        commitGamePanel.setVisible(false);
        opponentIsSelectingPanel.setVisible(true);
    }

    public void setRespond(String line) {
        acceptPanel.setVisible(true);
        opponentIsSelectingPanel.setVisible(false);
        gameEngine.setYouCheck(true);
        gameEngine.setOpponentDeadStone(line);
        gameViewPanel.repaint();
    }

    public void setTerritoryCheck() {
        commitGamePanel.setVisible(false);
        acceptPanel.setVisible(false);
        territoryPanel.setVisible(true);
        opponentIsSelectingPanel.setVisible(false);
        gameEngine.setTerritoryCheck(true);
        gameViewPanel.repaint();
    }

    public void setReturnToGame() {
        gameButtonsPanel.setVisible(true);
        commitGamePanel.setVisible(false);
        opponentIsSelectingPanel.setVisible(false);
        acceptPanel.setVisible(false);
        scorePanel.setVisible(true);
        movePanel.setVisible(true);
        territoryPanel.setVisible(false);

        gameEngine.setTerritoryCheck(false);
        gameEngine.setYouSelect(false);
        gameEngine.setReturnToGame();
        gameEngine.setGameEnd(false);
    }

    public void setFooterText(String text) {
        actualTurn.setText(text);
    }

    public void repaintPanel() {
        gameViewPanel.repaint();
    }

    public void setOpponentName(String name) {
        opponentName = name;
    }
}