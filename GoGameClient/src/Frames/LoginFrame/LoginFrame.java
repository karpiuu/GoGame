package Frames.LoginFrame;

import Connection.SocketClient;

import javax.swing.*;
import java.awt.*;

/**
 * Created by SZYMON on 28.11.2016.
 */
public class LoginFrame extends JFrame {

    SocketClient client;
    JTextField logField;

    public LoginFrame(SocketClient newClient){

        super("LoginFrame");

        client = newClient;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300,300);
        setLocation(50,50);
        setLayout(new GridLayout(4,1));

        JLabel logLabel= new JLabel("Write your nick",JLabel.CENTER);
        add(logLabel);

        logField = new JTextField("");
        add(logField);

        JButton logButton = new JButton("Log");
        logButton.addActionListener(new ButtonlogButtonAdapter(client,logField));
        add(logButton);

        JButton cancelButton = new JButton("Cancel");
        add(cancelButton);

        setResizable(false);
        setVisible(true);


    }
}
