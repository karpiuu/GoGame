package Source.Connection;

import Source.Exception.WrongSignalException;
import Source.Game.StoneType;

import java.util.ArrayList;
import java.util.Random;

/**
 * This main bot class
 */
public class BotConnection extends UserConnection {

    private volatile boolean haveAnswer;        // If bot have answer
    private Random rnd;                         // Random generator for fields
    private volatile String command;            // Command given by bot

    public BotConnection(Server newServer, Integer id) {
        super(newServer, null, id);
        this.userId = id;
        rnd = new Random();
    }

    /**
     * Main thread run for bot
     */
    @Override
    public void run() {
        System.out.println("BOT " + userId + " is created");

        while(true) {

            if(haveAnswer) {
                line = readBot();

                if (line != null) {
                    try {
                        server.getSignalManager().executeCommand(line, userId);
                        command = "";
                    } catch (WrongSignalException e) {
                        // Signal send by user is incorrect
                        System.out.println("[ERROR] Incorrect Signal");

                        // User might wait for response???
                        sendMessageToUser("Incorrect Signal");
                    }
                }
            }
        }
    }

    /**
     * @return command which bot provides
     */
    public String readBot() {
        haveAnswer = false;
        return command;
    }

    /**
     * Bot calculate command, and make proper answer
     * @param line message
     */
    @Override
    public void sendMessageToUser(String line) {

        if(!line.equals("OK")) {
            String serverCommand = line.substring(0, line.indexOf(";"));

            if (serverCommand.equals("Stone")) {

                boolean set = false;
                ArrayList<Integer> empty = gameEngine.getAllEmptyFields();
                ArrayList<Integer> emptyRandom = new ArrayList<>();

                int n = empty.size();
                int random;

                for(int i = 0; i < empty.size(); i++) {

                    random = rnd.nextInt(n);
                    emptyRandom.add( empty.get(random) );
                    empty.set( random, empty.get(n-1) );
                    n--;
                }

                for(int position = 0; position < emptyRandom.size(); position++) {
                    command = gameEngine.checkMove(emptyRandom.get(position), StoneType.WHITE);
                    gameEngine.placeBot(emptyRandom.get(position), StoneType.EMPTY);

                    if (command.contains("YourMove")) {
                        set = true;
                        break;
                    }
                }

                if(set) {
                    command = "Stone" + command.substring(command.indexOf(";"));
                }
                else {
                    command = "Pass;";
                }

                // RANDOM PLACES
//                int position;
//
//                while (true) {
//                    position = rnd.nextInt(361);
//                    command = gameEngine.checkMove(position, StoneType.WHITE);
//
//                    if (command.contains("YourMove")) break;
//                }

                haveAnswer = true;
            } else if (serverCommand.equals("Pass")) {
                command = "Pass;";

                haveAnswer = true;
            } else if (serverCommand.equals("DeadStone")) {

                String rest;
                if(line.contains(";")) {
                    rest = line.substring( line.indexOf(";")+1);
                }
                else {
                    rest = " ";
                }

                try {
                    server.getSignalManager().executeCommand("YesDeadStone;" + rest, userId);
                } catch (WrongSignalException e) {
                    e.printStackTrace();
                }

                command = "DeadStone;";
                haveAnswer = true;
            }
            else if (serverCommand.equals("NoDeadStone")) {
                command = "DeadStone;";
                haveAnswer = true;
            }
            else if (serverCommand.equals("TerritoryCheck")) {
                command = "YesTerritory;";
                haveAnswer = true;
            }
        }
    }
}
