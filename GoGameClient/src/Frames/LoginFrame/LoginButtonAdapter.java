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

    public LoginButtonAdapter(SocketClient newClient, JTextField newTextField) {
        client = newClient;
        logTextField = newTextField;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        client.out.println("SetName;" + logTextField.getText() + ";");

        String name;

        try {
            name = client.in.readLine();
        } catch (IOException e2) {
            e2.printStackTrace();
            return;
        }

        if (name.equals("OK")) {
            LobbyFrame lobbyFrame = new LobbyFrame(client);
            lobbyFrame.init();
        } else {
            System.out.println(name);
        }
    }
}
