package Frames.LoginFrame;

import Connection.OpponentSignalObserver;
import Connection.SocketClient;
import Frames.LobbyFrame.LobbyFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class LoginButtonAdapter implements ActionListener {

    private SocketClient client;
    private JTextField logTextField;
    private JFrame loginFrame;
    private OpponentSignalObserver opponentObserver;

    public LoginButtonAdapter(SocketClient newClient, JTextField newTextField, JFrame newloginFrame, OpponentSignalObserver opponentObserver) {
        client = newClient;
        logTextField = newTextField;
        loginFrame = newloginFrame;
        this.opponentObserver = opponentObserver;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        client.sendMessage("SetName;" + logTextField.getText() + ";");

        String name;

        name = client.readFromInput();

        if (name.equals("OK")) {
            loginFrame.setVisible(false);
            LobbyFrame lobbyFrame = new LobbyFrame(client, opponentObserver);
            lobbyFrame.init();

        } else {
            System.out.println(name);
        }
    }
}
