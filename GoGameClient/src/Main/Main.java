package Main;

import Connection.OpponentSignalObserver;
import Connection.SocketClient;
import Frames.LoginFrame.*;

/**
 * Class Main creates Login Frame to log to the game.
 */
public class Main {

    /**
     * Function creates new SocketClient and opponentSignalObserver, which services all signals
     * second player. On the beginning We create login frame to log to the game and we add ActionListener
     * @param args
     */
    public static void main(String[] args){

        OpponentSignalObserver opponentSignalObserver = new OpponentSignalObserver();

        SocketClient client = new SocketClient(opponentSignalObserver);
        client.listenSocket();
        client.start();

        LoginFrame loginFrame = new LoginFrame(client, opponentSignalObserver);
        loginFrame.init();
    }
}
