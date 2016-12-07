package Frames.LoginFrame;

import Connection.SocketClient;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class LoginFrame extends JFrame {
    private JPanel panel1;
    private JTextField logTextField;
    private JButton logInButton;

    SocketClient client;

    public LoginFrame(SocketClient newClient) {
        super("Login window");

        client = newClient;

        setContentPane(panel1);

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setResizable(false);

        logInButton.addActionListener( new LoginButtonAdapter(client, logTextField) );

        setVisible(true);
    }
}
