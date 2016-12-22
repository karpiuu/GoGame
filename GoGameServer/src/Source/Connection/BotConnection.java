package Source.Connection;

import Source.Exception.WrongSignalException;
import Source.Game.StoneType;
import java.util.Random;

/**
 * This main bot class
 */
public class BotConnection extends UserConnection {

    private volatile boolean haveAnswer;
    private Random rnd;
    private volatile String command;

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
    private String readBot() {
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

                int position;

                while (true) {
                    position = rnd.nextInt(361);
                    command = gameEngine.checkMove(position, StoneType.WHITE);

                    if (command.contains("YourMove")) break;
                }

                gameEngine.placeBot(position, StoneType.EMPTY);

                command = "Stone" + command.substring(command.indexOf(";"));

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
