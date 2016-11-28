import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

    private ArrayList<UserConnection> connections;
    private ArrayList<Table> tables;
    private SocketServer socketServer;

    private Integer userCount;
    private Integer tablesCount;

    public Server() {
        socketServer = new SocketServer();
    }

    public void startServer() {
        connections = new ArrayList<>();
        tables = new ArrayList<>();

        while(true) {
            Socket clientSocket = null;
            try { clientSocket = socketServer.getServer().accept(); }
            catch (IOException e) { System.out.print("Fail to read user.");
                                    continue; }
            connections.add( new UserConnection(this, clientSocket, userCount) );
            userCount++;
            connections.get( connections.size() - 1 ).start();
        }
    }

    public boolean checkValidName(String name) {
        return true;
    }
}
