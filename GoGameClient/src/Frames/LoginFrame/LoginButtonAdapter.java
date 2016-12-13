package Frames.LoginFrame;

import Connection.SocketClient;
import Frames.LobbyFrame.LobbyFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class LoginButtonAdapter implements ActionListener {

    SocketClient client;
    JTextField logTextField;
    JFrame loginFrame;

    public LoginButtonAdapter(SocketClient newClient, JTextField newTextField, JFrame newloginFrame) {
        client = newClient;
        logTextField = newTextField;
        loginFrame = newloginFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        client.sendMessage("SetName;" + logTextField.getText() + ";");

        String name;

        name = client.readFromInput();

        if (name.equals("OK")) {
            loginFrame.setVisible(false);
            LobbyFrame lobbyFrame = new LobbyFrame(client);
            lobbyFrame.init();

        } else {
            System.out.println(name);
        }
    }
}
