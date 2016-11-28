package Frames.LoginFrame;

import Connection.SocketClient;
import Frames.TableFrame.TableFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Created by SZYMON on 28.11.2016.
 */
public class ButtonlogButtonAdapter implements ActionListener {

    SocketClient client;
    JTextField logField;

    public ButtonlogButtonAdapter(SocketClient newClient, JTextField newlogField) {
        client = newClient;
        logField = newlogField;
    }

    public void actionPerformed(ActionEvent e) {

        client.out.println("SetName;" + logField.getText() + ";");

        String name;

        try {
            name = client.in.readLine();
        } catch (IOException e2) {
            e2.printStackTrace();
            return;
        }

        if (name.equals("OK")) {
            TableFrame tableFrame = new TableFrame(client);
            tableFrame.init();
        } else {
            System.out.println(name);
        }
    }
}
