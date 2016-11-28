import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

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

    public void run(){
        System.out.println("Reading");

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
            try
            {
                line = in.readLine();
                executeLine(line);
            }
            catch (IOException e)
            {
                System.out.println("Read failed");
                System.exit(-1);
            }

        }
    }

    public Integer getUserId() {
        return userId;
    }

    public void executeLine(String line) {
        if(line.charAt(0) == 'C') {
            String name = line.substring( 2, line.length()-1 );

            if(server.checkValidName(name)) {
                userName = name;
                out.println("OK");
            }
            else {
                out.print("NameU");
            }
        }
    }
}
