package Source.Connection;

import Source.Exception.UnknownUserIdException;
import Source.Exception.WrongSignalException;
import Source.Game.GameEngine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class UserConnection extends Thread {

    protected Integer userId;
    protected Server server;
    protected String userName;
    protected Integer tableId;

    protected Socket client = null;
    protected BufferedReader in = null;
    protected PrintWriter out = null;
    protected String line = "";
    protected GameEngine gameEngine;

    public UserConnection(Server newServer, Socket socket, Integer id) {
        server = newServer;
        client = socket;
        userId = id;
        tableId = null;
    }

    /**
     * Reads from user input
     */
    public void run() {
        System.out.println("USER " + userId.toString() + " is created");

        try
        {
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            out = new PrintWriter(client.getOutputStream(), true);
        }
        catch (IOException e)
        {
            System.out.println("[ERROR] Accept failed");
            shutDown();
            return;
        }

        while(line != null)
        {
            try { line = in.readLine(); }
            catch (IOException e) {
                // End the loop and goes to shutdown() function
                break;
            }

            if( line != null ) {
                try {
                    server.getSignalManager().executeCommand(line, userId);
                }
                catch (WrongSignalException e) {
                    // Signal send by user is incorrect
                    System.out.println("[ERROR] Incorrect Signal");

                    // User might wait for response???
                    sendMessageToUser("Incorrect Signal");
                }
            }
        }

        shutDown();
    }

    /**
     * Sets user table to given by param id
     * @param id for table
     */
    public void sitDown(Integer id) {
        tableId = id;
    }

    /**
     * Stand up from table
     */
    public void standUp() {
        tableId = null;
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
     * Sets game engine to user
     * @param gameEngine given game engine
     */
    public void setGameEngine(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
    }

    /**
     * Delete user and clear his tables
     */
    private void shutDown() {

        // USER STAND UP FROM HIS TABLE
        try { server.getSignalManager().executeCommand("StandUp;",userId); }
        catch (WrongSignalException e) {
            //This shouldn't be incorrect, "StandUp;" commend is correct
            System.out.println("[ERROR] StandUp signal error");
        }

        // USER DELETE
        try { server.getUserManager().deleteUser(userId); }
        catch (UnknownUserIdException e1) {
            System.out.println("[ERROR] USER " + userId.toString() + " can't delete this user");
        }

        System.out.println("USER " + userId.toString() + " dropped");

        stop();
    }
}
