package Connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;

public class SocketClient extends Thread  {

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private OpponentSignalObserver opponentObserver;

    private volatile LinkedList<String> inputList;

    public SocketClient(OpponentSignalObserver opponentObserver) {
        inputList = new LinkedList<>();
        this.opponentObserver = opponentObserver;
    }

    public void listenSocket()
    {
        try
        {
            //socket = new Socket("localhost", 4444);
            socket = new Socket("192.168.0.11", 4444);
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

                if( !opponentObserver.checkLine( line ) ) {
                    inputList.offer(line);
                }
            }
        }
    }

    public String readFromInput() {
        String line;

        do {
            line = inputList.poll();
        } while (line == null);

        return line;
    }

    public void sendMessage(String message) {
        out.println(message);
    }
}