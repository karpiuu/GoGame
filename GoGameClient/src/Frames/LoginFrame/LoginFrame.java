package Frames.LoginFrame;

import javax.swing.*;
import java.awt.*;

/**
 * Created by SZYMON on 28.11.2016.
 */
public class LoginFrame extends JFrame {

    public LoginFrame(){

        super("Frames.LoginFrame.LoginFrame");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300,300);
        setLocation(50,50);
        setLayout(new GridLayout(4,1));

        JLabel logLabel= new JLabel("Write your nick",JLabel.CENTER);
        add(logLabel);

        JTextField logField = new JTextField("");
        add(logField);

        JButton logButton = new JButton("Log");
        add(logButton);

        JButton cancelButton = new JButton("Cancel");
        add(cancelButton);

        setResizable(false);
        setVisible(true);


    }
}
