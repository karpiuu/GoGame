import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class UserConnection extends Thread {

    private Socket client = null;
    private BufferedReader in = null;
    private PrintWriter out = null;
    private String line = "";

    private Socket socket = null;

    public UserConnection(Socket socket) {
        client = socket;
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
                System.out.println(line);
            }
            catch (IOException e)
            {
                System.out.println("Read failed");
                System.exit(-1);
            }

        }
    }
}
