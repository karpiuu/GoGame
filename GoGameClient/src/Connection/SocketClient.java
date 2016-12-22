package Connection;

import javax.swing.*;
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
        String ip;

        ip = (String)JOptionPane.showInputDialog( null, "Write server ip: ", "192.168.0.11" );

        try
        {
            socket = new Socket(ip, 4444);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }
        catch(UnknownHostException e)
        {
            JOptionPane.showMessageDialog(null, "Unknown host ip");
            System.exit(1);
        }
        catch(IOException e)
        {
            JOptionPane.showMessageDialog(null, "Server is offline");
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