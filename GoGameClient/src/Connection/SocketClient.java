package Connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class SocketClient extends Thread  {

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    private LinkedList<String> inputList;
    private boolean readable;

    public SocketClient() {
        inputList = new LinkedList<>();
        readable = false;
    }

    public void listenSocket()
    {
        try
        {
            socket = new Socket("localhost", 4444);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }
        catch(UnknownHostException e)
        {
            System.out.println("Unknown host: localhost");
            System.exit(1);
        }
        catch(IOException e)
        {
            System.out.println("No I/O");
            System.exit(1);
        }
    }

    public void run() {
        String line;

        while(true) {

            try {
                line = in.readLine();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            if( line != null ) {
                inputList.offer( line );
                readable = true;
            }
        }
    }

    public String readFromInput() {
        String line;

        do {
            line = inputList.poll();
        }while (line == null);

        return line;
    }

    public void sendMessage(String message) {
        out.println(message);
    }
}