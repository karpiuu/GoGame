import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

    private ArrayList<UserConnection> connections;
    private SocketServer socketServer;

    public Server() {
        socketServer = new SocketServer();
    }

    public void startServer() {
        connections = new ArrayList<>();

        while(true) {
            Socket clientSocket = null;
            try { clientSocket = socketServer.getServer().accept(); }
            catch (IOException e) { System.out.print("Fail to read user.");
                                    continue; }
            connections.add( new UserConnection(clientSocket) );
            connections.get( connections.size() - 1 ).start();
        }
    }
}
