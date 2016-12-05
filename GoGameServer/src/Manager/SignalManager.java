package Manager;

import Connection.Server;
import Connection.UserConnection;
import Signal.*;
import java.util.ArrayList;

public class SignalManager {

    private Server server;

    public SignalManager(Server newServer) {
        server = newServer;
    }

    public void executeCommand(String line, int id) {
        ArrayList<String> argv = parseString(line);

        Signal signal;
        if(argv != null) {
            if (argv.get(0).equals("SetName")) {

                System.out.println("USER " + Integer.toString(id) + " try to change name to " + argv.get(1));
                signal = new SetNameSignal(server, id, argv.get(1));
                synchronized (UserConnection.class) {
                    signal.execute();
                }
            }
            else if (argv.get(0).equals("Refresh")) {

                System.out.println("USER " + Integer.toString(id) + " refresh view ");
                signal = new RefreshSignal(server, id);
                synchronized (UserConnection.class) {
                    signal.execute();
                }
            }
            else if (argv.get(0).equals("CreateTable")) {

                System.out.println("USER " + Integer.toString(id) + " is creating table.");
                signal = new CreateTableSignal(server, id);
                synchronized (UserConnection.class) {
                    signal.execute();
                }
            }
            else if (argv.get(0).equals("SitDown")) {

                System.out.println("USER " + Integer.toString(id) + " is sit down in table " + argv.get(1));
                signal = new SitDownSignal(server, id, Integer.parseInt( argv.get(1) ));
                synchronized (UserConnection.class) {
                    signal.execute();
                }
            }
        }
    }

    private ArrayList<String> parseString(String line) {

        if( line.length() > 0 ) {
            ArrayList<String> argv = new ArrayList<>();

            int start = 0;
            int end = 1;

            while (start < line.length() || end == -1) {
                end = line.indexOf(';', start);
                if( end != -1 ) {
                    argv.add(line.substring(start, end));
                    start = end + 1;
                }
            }
            return argv;
        }
        return null;
    }
}
