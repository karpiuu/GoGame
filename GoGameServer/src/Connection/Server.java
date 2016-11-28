package Connection;
import Exception.UnknownUserIdException;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

    private ArrayList<UserConnection> connections;
    private ArrayList<Table> tables;
    private SocketServer socketServer;

    private Integer userCount;             // Count user
    private Integer tablesCount;

    private ArrayList<Integer> newUsersId; // Array contain Id for new users added to server
    private ArrayList<Integer> newTableId; // Array contain Id for new tables added to server

    public Server() {
        socketServer = new SocketServer();
        userCount = 0;
        tablesCount = 0;
    }

    public void startServer() {
        connections = new ArrayList<>();
        tables = new ArrayList<>();
        newUsersId = new ArrayList<>();

        while(true) {
            Socket clientSocket;
            try { clientSocket = socketServer.getServer().accept(); }
            catch (IOException e) { System.out.print("Fail to read user.");
                                    continue; }

            if(newUsersId.size() > 0) {
                int index = newUsersId.get(0);

                connections.set(index, new UserConnection(this, clientSocket, index));
                connections.get(index).start();
                newUsersId.remove(0);
            }
            else {
                connections.add( new UserConnection(this, clientSocket, userCount) );
                userCount++;
                connections.get( connections.size() - 1 ).start();
            }
        }
    }

    public boolean checkValidName(String name) {
        return true;
    }

    public void deleteUser(int userId) throws UnknownUserIdException {

        if(userId >= connections.size() ) throw new UnknownUserIdException();

        connections.set(userId, null);
        newUsersId.add(userId);
    }

    public ArrayList<UserConnection> getConnections() {
        return connections;
    }
}
