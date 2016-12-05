import Connection.SocketClient;
import Frames.LoginFrame.LoginFrame;

public class Main {

    public static void main(String[] args){
        SocketClient client = new SocketClient();
        client.listenSocket();

        LoginFrame loginFrame = new LoginFrame(client);
    }
}
