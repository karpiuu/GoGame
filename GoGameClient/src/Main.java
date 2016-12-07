import Connection.SocketClient;
import Frames.LoginFrame.*;

import javax.swing.*;

public class Main {

    public static void main(String[] args){

        SocketClient client = new SocketClient();
        client.listenSocket();

        JFrame loginFrame = new LoginFrame(client);

        //loginFrame.setCo

    }
}
