package Connection;
import Exception.UnknownUserIdException;
import Manager.SignalManager;
import Manager.TableManager;
import Manager.UserManager;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

    private UserManager userManager;
    private TableManager tableManager;
    private SignalManager signalManager;
    private SocketServer socketServer;

    public Server() {
        userManager = new UserManager();
        tableManager = new TableManager();
        socketServer = new SocketServer(4444);
    }

    /**
     * Start server, and waits for new users.
     */
    public void startServer() {
        Socket clientSocket;

        signalManager = new SignalManager(this);

        while(true) {
            try { clientSocket = socketServer.getServer().accept(); }
            catch (IOException e) {
                System.out.print("Fail to read user.");
                continue;
            }
            userManager.addUser(this, clientSocket);
        }
    }

    /**
     *
     * @return userManager object
     */
    public UserManager getUserManager() {
        return userManager;
    }
    public TableManager getTableManager() { return tableManager; }

    public SignalManager getSignalManager() {
        return signalManager;
    }
}
