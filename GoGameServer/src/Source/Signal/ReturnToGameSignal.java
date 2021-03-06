package Source.Signal;

import Source.Connection.Server;
import Source.Exception.UnknownTableIdException;
import Source.Exception.UnknownUserIdException;
import Source.Game.Table;
import Source.Manager.TableManager;

/**
 * Signal executed when user want to return to old game state before end game phase
 */
public class ReturnToGameSignal extends Signal {
    private TableManager tableManager;

    public ReturnToGameSignal(Server server, int newId) {
        setUserManager(server.getUserManager());
        tableManager = server.getTableManager();
        id = newId;

        setOwner();
    }

    @Override
    public void execute() {
        Table table;

        if (owner.getTableId() != null) {
            try {
                table = tableManager.getTable(owner.getTableId());
            } catch (UnknownTableIdException e) {
                // Unknown table id
                System.out.println("[ERROR] Can't start game, table don't exists");
                return;
            }

            System.out.println("Table " + Integer.toString(table.getId()) + " is trying to return to game.");
            owner.sendMessageToUser("OK");

            int opponentId;

            if( table.getIdUserBlack() == id ) {
                opponentId = table.getIdUserWhite();
            }
            else {
                opponentId = table.getIdUserBlack();
            }

            try {
                userManager.getUser(opponentId).sendMessageToUser("ReturnToGame;");
            } catch (UnknownUserIdException e) {
                // This user might be deleted
                System.out.println("[ERROR] User " + id + " don't exists anymore");
            }

            table.getGameEngine().returnToGame();
            table.setReturnToGame();
            table.setActivePlayer( opponentId );
        }
    }
}
