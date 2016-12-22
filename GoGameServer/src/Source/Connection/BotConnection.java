package Source.Connection;

import Source.Exception.WrongSignalException;
import Source.Game.GameEngine;
import Source.Game.StoneType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;

public class BotConnection extends UserConnection {

    private volatile boolean haveAnswer;
    private Random rnd;
    private volatile String command;

    public BotConnection(Server newServer, Integer id) {
        super(newServer, null, id);
        this.userId = id;
        rnd = new Random();
    }

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

    private String readBot() {
        haveAnswer = false;
        return command;
    }

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
            else {
                System.out.println("BOT NOT ANSWER [" + line + "]");
            }
        }
    }
}
