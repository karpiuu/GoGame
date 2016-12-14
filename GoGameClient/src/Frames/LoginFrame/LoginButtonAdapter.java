package Frames.LoginFrame;

import Connection.OpponentSignalObserver;
import Connection.SocketClient;
import Frames.LobbyFrame.LobbyFrame;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Class LoginButtonAdapter is using one function which is talking with server
 * and create lobby frame or not.
 */
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

    /**
     * This function sends to the server nickname of user, which was written to the field.
     * If server accept this string, it will be send agreement to the client and
     * lobby frame will be created. Otherwise server will send message about incorrectness name.
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        client.sendMessage("SetName;" + logTextField.getText() + ";");

        String name;

        name = client.readFromInput();

        if (name.equals("OK")) {
            loginFrame.setVisible(false);
            LobbyFrame lobbyFrame = new LobbyFrame(client, opponentObserver);
            lobbyFrame.init();

            client.sendMessage("Refresh;");

            String line;
            line = client.readFromInput();

            lobbyFrame.refreshUserList(line.substring( 0, line.indexOf("Tables") ));
            lobbyFrame.refreshTableList(line.substring( line.indexOf("Tables") ));

        } else {
            System.out.println(name);
        }
    }
}
