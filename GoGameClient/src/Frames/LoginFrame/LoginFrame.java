package Frames.LoginFrame;

import Connection.OpponentSignalObserver;
import Connection.SocketClient;

import javax.swing.*;

public class LoginFrame extends JFrame {
    private JPanel panel1;
    private JTextField logTextField;
    private JButton logInButton;

    private SocketClient client;
    private OpponentSignalObserver opponentObserver;

    public LoginFrame(SocketClient newClient, OpponentSignalObserver opponentObserver) {
        super("Login window");

        this.opponentObserver = opponentObserver;

        client = newClient;

        setContentPane(panel1);

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    public void init(){
        logInButton.addActionListener( new LoginButtonAdapter(client, logTextField, this, opponentObserver) );
    }
}
