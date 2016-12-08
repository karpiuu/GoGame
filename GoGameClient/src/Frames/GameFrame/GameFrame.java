package Frames.GameFrame;

import Connection.SocketClient;
import Frames.LobbyFrame.LobbyFrame;
import Game.Field;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GameFrame extends JFrame {
    private SocketClient client;

    private JPanel panel1;
    private JButton passButton;
    private JButton concedeButton;
    private JLabel user1;
    private JLabel user2;
    private JLabel board;
    private LobbyFrame lobbyFrame;

    public GameFrame(SocketClient newClient, LobbyFrame newlobbyFrame){
        super("Go game");

        client = newClient;
        lobbyFrame = newlobbyFrame;

        setContentPane(panel1);

        //user1.setText( );

        addWindowListener(new GameFrameClosingAdapter(client, lobbyFrame));

        Icon icon = new ImageIcon("img/board.png");
        JLabel newLabel = new JLabel( "", icon, JLabel.CENTER );
        panel1.add(newLabel);


        //drawBoard();

        pack();
        //setDefaultCloseOperation();
        setSize(400,400);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);

    }

    public void drawBoard(){

        Field[][] field = new Field[19][19];

        for(int i=0; i<19; i++)
            for(int j=0; j<19; j++)
                field[i][j] = new Field();

        for(int i=0; i<19; i++)
            for(int j=0; j<19; j++)
                board.add(field[i][j].image);

    }
}
