package Source.Signal;

import Source.Connection.Server;
import Source.Exception.UnknownTableIdException;
import Source.Exception.UnknownUserIdException;
import Source.Game.Table;
import Source.Manager.SignalManager;
import Source.Manager.TableManager;

import java.util.ArrayList;

public class YesTerritorySignal extends Signal {

    private TableManager tableManager;
    private String line;

    public YesTerritorySignal(Server server, int newId, String line) {
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

            owner.sendMessageToUser("OK");

            table.getGameEngine().userAcceptTerritory();

            if(table.getGameEngine().isTerritoryAccepted()) {
                owner.sendMessageToUser("GameResult;B;100;W;24.5");

                int opponentId = (table.getIdUserWhite() == id ? table.getIdUserBlack() : table.getIdUserWhite() );

                try { userManager.getUser( opponentId ).sendMessageToUser("GameResult;B;100;W;24.5"); }
                catch (UnknownUserIdException e) {
                    // This user might be deleted
                    System.out.println("[ERROR] User " + id + " don't exists anymore");
                }
            }
        }
        else {
            owner.sendMessageToUser("Your don't sit in any table.");
        }
    }
}
