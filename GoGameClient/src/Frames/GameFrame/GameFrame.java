package Frames.GameFrame;

import Connection.SocketClient;

import javax.swing.*;

public class GameFrame extends JFrame {
    private SocketClient client;

    private JPanel panel1;
    private JButton passButton;
    private JButton concedeButton;
    private JTable table1;

    public GameFrame(SocketClient newClient) {
        super("Go game");

        client = newClient;

        setContentPane(panel1);

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800,800);
        setResizable(false);

        setVisible(true);
    }
}
