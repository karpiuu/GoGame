package Frames.GameFrame;

import Connection.SocketClient;
import Frames.LobbyFrame.ListJoinToTableAdapter;
import Frames.LobbyFrame.LobbyFrame;
import Frames.LoginFrame.LoginFrame;

import javax.swing.*;

public class GameFrame extends JFrame {
    private SocketClient client;

    private JPanel panel1;
    private JButton passButton;
    private JButton concedeButton;
    private JTable table1;
    private JLabel user1;
    private JLabel user2;
    private LobbyFrame lobbyFrame;

    public GameFrame(SocketClient newClient, LobbyFrame newlobbyFrame) {
        super("Go game");

        client = newClient;
        lobbyFrame = newlobbyFrame;

        setContentPane(panel1);

        //user1.setText( );

        addWindowListener(new GameFrameClosingAdapter(client, lobbyFrame));


        pack();
        //setDefaultCloseOperation();
        setSize(800,800);
        setResizable(false);
        setVisible(true);



    }
}
