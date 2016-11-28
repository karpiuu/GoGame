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
        setResizable(false);

        setSize(400,300);
        setLocation(50, 50);
        setLayout(new BorderLayout());

        logField = new JTextField("");

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add( new JLabel("Set you name"), BorderLayout.NORTH );
        mainPanel.add( logField, BorderLayout.CENTER );

        add(mainPanel, BorderLayout.CENTER);

        JButton logButton = new JButton("Log");
        logButton.addActionListener(new ButtonlogButtonAdapter(client,logField));
        add(logButton, BorderLayout.SOUTH);

//      JButton cancelButton = new JButton("Cancel");
//      add(cancelButton);

        setResizable(false);
        setVisible(true);
    }
}
