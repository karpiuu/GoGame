package Source.Signal;

import Source.Connection.Server;
import Source.Exception.UnknownTableIdException;
import Source.Exception.UnknownUserIdException;
import Source.Game.Table;
import Source.Manager.SignalManager;
import Source.Manager.TableManager;

import java.util.ArrayList;

public class YesDeadStoneSignal extends Signal {

    private TableManager tableManager;
    private String line;

    public YesDeadStoneSignal(Server server, int newId, String line) {
        setUserManager(server.getUserManager());
        tableManager = server.getTableManager();
        id = newId;
        this.line = line;

        setOwner();
    }

    @Override
    public void execute() {
        Table table;

        if(owner.getTableId() != null) {
            try {
                table = tableManager.getTable(owner.getTableId());
            } catch (UnknownTableIdException e) {
                // Unknown table id
                System.out.println("[ERROR] Can't start game, table don't exists");
                return;
            }

            int move[];

            ArrayList<String> args = SignalManager.parseString(line.substring( line.indexOf(";")+1 ));

            if( args != null ) {
                for (String position : args) {
                    move = table.getGameEngine().convertMove(Integer.parseInt(position));
                    table.getGameEngine().clearDeadStone(move[0], move[1]);
                }
            }

            owner.sendMessageToUser("OK");

            int opponentId;

            if( table.getIdUserWhite() == id ) {
                opponentId = table.getIdUserBlack();

                try { userManager.getUser( opponentId ).sendMessageToUser("YesDeadStone;"); }
                catch (UnknownUserIdException e) {
                    // This user might be deleted
                    System.out.println("[ERROR] User " + id + " don't exists anymore");
                }
            }
            else {
                opponentId = table.getIdUserWhite();

                try { userManager.getUser( opponentId ).sendMessageToUser("TerritoryCheck;" + table.getGameEngine().getTerritory()); }
                catch (UnknownUserIdException e) {
                    // This user might be deleted
                    System.out.println("[ERROR] User " + id + " don't exists anymore");
                }

                owner.sendMessageToUser("TerritoryCheck;" + table.getGameEngine().getTerritory() );
            }



        }
        else {
            owner.sendMessageToUser("Your don't sit in any table.");
        }
    }
}
