import java.io.IOException;
import java.net.ServerSocket;

public class SocketServer {

    private ServerSocket server = null;

    public SocketServer()
    {
        try { server = new ServerSocket(4444); }
        catch (IOException e) {
            System.out.println("Could not listen on port 4444");
            System.exit(-1);
        }
    }

    public ServerSocket getServer() {
        return server;
    }
}