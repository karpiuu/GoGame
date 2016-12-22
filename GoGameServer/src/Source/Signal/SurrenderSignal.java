package Source.Signal;

import Source.Connection.Server;
import Source.Exception.UnknownTableIdException;
import Source.Exception.UnknownUserIdException;
import Source.Game.Table;
import Source.Manager.TableManager;

public class SurrenderSignal extends Signal {
    private TableManager tableManager;
    private String line;

    public SurrenderSignal(Server server, int newId) {
        setUserManager(server.getUserManager());
        tableManager = server.getTableManager();
        id = newId;

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

            int opponentId = (table.getIdUserWhite() == id ? table.getIdUserBlack() : table.getIdUserWhite() );

            //owner.sendMessageToUser("GameResult;You lost game." );

            try { userManager.getUser( opponentId ).sendMessageToUser("GameResult;Your opponent surrender. You won game"); }
            catch (UnknownUserIdException e) {
                // This user might be deleted
                System.out.println("[ERROR] User " + id + " don't exists anymore");
            }
        }
        else {
            owner.sendMessageToUser("Your don't sit in any table.");
        }
    }
}