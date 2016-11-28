package Connection;

import java.io.IOException;
import java.net.ServerSocket;

public class SocketServer {

    private ServerSocket server = null;

    public SocketServer(Integer port)
    {
        try { server = new ServerSocket(port); }
        catch (IOException e) {
            System.out.println("Could not listen on port " + port.toString());
            System.exit(-1);
        }
    }

    public ServerSocket getServer() {
        return server;
    }
}