package Source.Manager;

import Source.Connection.Server;
import Source.Connection.UserConnection;
import Source.Exception.WrongSignalException;
import Source.Signal.*;
import java.util.ArrayList;

/**
 * Main class which connects signals with line send by user
 * Works like factory (fits signal with command)
 */
public class SignalManager {

    private Server server;

    public SignalManager(Server newServer) {
        server = newServer;
    }

    /**
     * Function execute command from user, choose between list of signals
     * @param line command to execute
     * @param id id of user with send signal
     * @throws WrongSignalException throw exception when command is incorrect
     */
    public void executeCommand(String line, int id) throws WrongSignalException {
        ArrayList<String> argv = parseString(line);

        Signal signal;
        if(argv != null) {
            if (argv.get(0).equals("SetName")) {

                if(argv.size() >= 2) {

                    System.out.println("USER " + Integer.toString(id) + " try to change name to " + argv.get(1));
                    signal = new SetNameSignal(server, id, argv.get(1));
                    synchronized (UserConnection.class) {
                        signal.execute();
                    }
                }
                else {
                    throw new WrongSignalException(line);
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

                if(argv.size() >= 2) {

                    System.out.println("USER " + Integer.toString(id) + " is siting down in table " + argv.get(1));
                    signal = new SitDownSignal(server, id, Integer.parseInt(argv.get(1)));
                    synchronized (UserConnection.class) {
                        signal.execute();
                    }
                }
                else {
                    throw new WrongSignalException(line);
                }
            }
            else if (argv.get(0).equals("StandUp")) {
                System.out.println("USER " + Integer.toString(id) + " is standing up from table.");
                signal = new StandUpSignal(server, id);
                signal.execute();
            }
            else if (argv.get(0).equals("StartGame")) {
                System.out.println("USER " + Integer.toString(id) + " try to start game.");
                signal = new StartGameSignal(server, id);
                synchronized (UserConnection.class) {
                    signal.execute();
                }
            }
            else if (argv.get(0).equals("Stone")) {
                System.out.println("USER " + Integer.toString(id) + " try to put stone " + argv.get(1) + " at " + argv.get(2) );
                signal = new StoneSignal(server, id, argv.get(1), argv.get(2));
                signal.execute();
            }
            else {
                System.out.println("USER " + Integer.toString(id) + " send signal [" + line + "]" );
                signal = new UnknownSignal(server, id);
                signal.execute();
            }
        }
    }

    private ArrayList<String> parseString(String line) {

        if( line.length() > 0 ) {
            ArrayList<String> argv = new ArrayList<>();

            int start = 0;
            int end = 1;

            while (start < line.length() && end != -1) {
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
