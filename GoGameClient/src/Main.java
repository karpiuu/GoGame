import Connection.SocketClient;
import Frames.LoginFrame.LoginFrame;

public class Main {

    public static void main(String[] args){
        String x;

        SocketClient client = new SocketClient();
        client.listenSocket();

        LoginFrame loginFrame = new LoginFrame(client);
    }
}
