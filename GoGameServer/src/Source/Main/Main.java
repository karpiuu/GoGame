package Source.Main;

import Source.Connection.Server;

/**
 * Main program function
 */
public class Main {

    public static void main(String[] args) {
        Server server = new Server();
        // Start running server
        server.startServer();
    }
}
