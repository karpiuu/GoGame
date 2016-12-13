package Main;

import Connection.OpponentSignalObserver;
import Connection.SocketClient;
import Frames.LoginFrame.*;

import javax.swing.*;

public class Main {

    public static void main(String[] args){

        OpponentSignalObserver opponentSignalObserver = new OpponentSignalObserver();

        SocketClient client = new SocketClient(opponentSignalObserver);
        client.listenSocket();
        client.start();

        LoginFrame loginFrame = new LoginFrame(client, opponentSignalObserver);
        loginFrame.init();
    }
}
