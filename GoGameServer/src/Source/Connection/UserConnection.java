package Source.Connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import Source.Exception.UnknownUserIdException;
import Source.Exception.WrongSignalException;
import Source.Game.Table;

public class UserConnection extends Thread {

    private Integer userId;
    private Server server;
    private String userName;
    private Integer tableId;

    private Socket client = null;
    private BufferedReader in = null;
    private PrintWriter out = null;
    private String line = "";

    public UserConnection(Server newServer, Socket socket, Integer id) {
        server = newServer;
        client = socket;
        userId = id;
        tableId = null;
    }

    public void run() {
        System.out.println("USER " + userId.toString() + " is created");

        try
        {
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            out = new PrintWriter(client.getOutputStream(), true);
        }
        catch (IOException e)
        {
            System.out.println("Accept failed");
            shutDown();
            return;
        }

        while(line != null)
        {
            try { line = in.readLine(); }
            catch (IOException e) {
                break;
            }

            if( line != null ) {
                try {
                    server.getSignalManager().executeCommand(line, userId);
                }
                catch (WrongSignalException e) {
                    e.printStackTrace();
                }
            }
        }

        shutDown();
    }

    public void sitDown(Integer id) {
        tableId = id;
    }

    /**
     * Sends message to user
     * @param line message
     */
    public void sendMessageToUser(String line) {
        System.out.println("SERVER TO USER " + userId.toString() + " [" + line + "]");
        out.println(line);
    }

    /**
     * Get name of user
     * @return user name
     */
    public String getUserName() { return userName; }

    /**
     * Return table id in with user sits
     * @return table id
     */
    public Integer getTableId() {
        return tableId;
    }

    /**
     * Sets user name to new
     * @param newName new name
     */
    public void setUserName(String newName) { userName = newName; }

    /**
     * Delete user and clear his tables
     */
    private void shutDown() {

        // USER STAND UP FROM HIS TABLE
        try { server.getSignalManager().executeCommand("StandUp;",userId); }
        catch (WrongSignalException e) {
            e.printStackTrace();
        }

        // USER DELETE
        try { server.getUserManager().deleteUser(userId); }
        catch (UnknownUserIdException e1) { System.out.println("USER " + userId.toString() + " can't delete this user"); }

        System.out.println("USER " + userId.toString() + " dropped");

        stop();
    }
}
