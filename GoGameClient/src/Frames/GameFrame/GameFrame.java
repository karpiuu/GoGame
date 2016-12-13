package Frames.GameFrame;

import Connection.SocketClient;
import Frames.LobbyFrame.LobbyFrame;
import GameEngine.GameEngine;
<<<<<<< HEAD
import Game.Field;
=======

>>>>>>> origin/master
import javax.swing.*;
import java.awt.*;
import GameEngine.Stone;

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
<<<<<<< HEAD
    private JButton concedeButton;
    private JLabel user1;
    private JLabel user2;
    private JLabel board;
    private LobbyFrame lobbyFrame;

    public GameFrame(SocketClient newClient, LobbyFrame newlobbyFrame){
>>>>>>> origin/master
=======
>>>>>>> origin/master
        super("Go game");

        client = newClient;
        lobbyFrame = newlobbyFrame;

        gameEngine = new GameEngine(19);
        gameEngine.place( 10, 10, Stone.BLACK );
        gameEngine.place( 5, 10, Stone.BLACK );
        gameEngine.place( 12, 10, Stone.WHITE );
        gameEngine.place( 15, 7, Stone.BLACK );
        gameEngine.place( 10, 2, Stone.WHITE );
        gameEngine.place( 1, 6, Stone.WHITE );
        gameEngine.place( 10, 11, Stone.BLACK );
        gameEngine.place( 11, 12, Stone.WHITE );
        gameEngine.place( 13, 13, Stone.WHITE );

        setContentPane(panel1);

        addWindowListener(new GameFrameClosingAdapter(client, lobbyFrame));

        pack();

        setSize(800,800);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    public void init() {
        gameViewPanel = new GameViewPanel(800,750, gameEngine);
        panel1.add( gameViewPanel, BorderLayout.CENTER );

        startButton.addActionListener( new StartButtonAdapter(client, gameEngine, startButton, surrenderButton, passButton) );
    }
}