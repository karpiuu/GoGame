package Connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import Exception.UnknownUserIdException;

public class UserConnection extends Thread {

    private Integer userId;
    private Server server;
    private String userName;

    private Socket client = null;
    private BufferedReader in = null;
    private PrintWriter out = null;
    private String line = "";

    private Socket socket = null;

    public UserConnection(Server newServer, Socket socket, Integer id) {
        server = newServer;
        client = socket;
        userId = id;
    }

    /**
     * Main function of thread, have basic I/O operations.
     */
    public void run(){
        System.out.println("Reading [" + userId.toString() + "]");

        try
        {
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            out = new PrintWriter(client.getOutputStream(), true);
        }
        catch (IOException e)
        {
            System.out.println("Accept failed: 4444");
            System.exit(-1);
        }

        while(line != null)
        {
            try { line = in.readLine(); }
            catch (IOException e) {
                break;
            }

            if( line != null ) executeLine(line);
        }

        try { server.deleteUser(userId); }
        catch (UnknownUserIdException e1) {
            System.out.println("Can't delete user [" + userId.toString() + "] - UnknownUserIdException");
        }
        System.out.println("Stop [" + userId.toString() + "]");
        stop();
    }

    public Integer getUserId() {
        return userId;
    }

    /**
     * Function which operate on user input,
     * create user name, sends information to opponent.
     * @param line which user sends
     */
    public void executeLine(String line) {

        System.out.println("User " + userId.toString() + " - [" + line + "]");

        if(line.charAt(0) == 'C') {
            String name;

            if( line.length() > 2 ) {
                name = line.substring(2, line.length());
            }
            else {
                return;
            }

            if(server.checkValidName(name)) {
                userName = name;
                out.println("OK");
                System.out.println("User " + userId.toString() + " - Set name to [" + userName + "]");
            }
            else {
                out.println("Name is already in use");
            }
        }

        if(line.charAt(0) == 'R') {
            String users = "U ";

            for(UserConnection user : server.getConnections()) {
                synchronized (this) {
                    if (user != null) {
                        users += (user.userName + ";");
                    }
                }
            }

            out.println(users);
            System.out.println("(" + users + ")");
        }
    }
}
