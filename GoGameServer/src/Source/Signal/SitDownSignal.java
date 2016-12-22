package Source.Signal;

import Source.Connection.Server;
import Source.Connection.UserConnection;
import Source.Game.Table;
import Source.Manager.TableManager;
import Source.Exception.*;

/**
 * Signal when user sits in table
 */
public class SitDownSignal extends Signal {

    private int tableId;
    private TableManager tableManager;

    public SitDownSignal(Server server, int newId, int newTableId) {
        setUserManager(server.getUserManager());
        tableManager = server.getTableManager();
        id = newId;
        tableId = newTableId;
        setOwner();
    }

    @Override
    public void execute() {

        try {
            tableManager.getTable( tableId ).sitDown(id);
            owner.sitDown(tableId);
        } catch (FullTableException e) {
            System.out.println("[ERROR] USER " + id.toString() + " Can't sit here, full table");
            owner.sendMessageToUser("Table is full");
            return;
        } catch (UnknownTableIdException e) {
            System.out.println("[ERROR] Can't sit here USER "  + id.toString() + " , table don't exists");
            owner.sendMessageToUser("Table don't exists");
            return;
        }

        Table table;
        if(owner.getTableId() != null) {
            try {
                table = tableManager.getTable(owner.getTableId());
            } catch (UnknownTableIdException e) {
                // Unknown table id
                System.out.println("[ERROR] Can't stand up, table don't exists");
                return;
            }

            if (table.getUserCount() >= 2) {
                int opponentId = (table.getIdUserWhite() == id ? table.getIdUserBlack() : table.getIdUserWhite() );

                try { userManager.getUser( opponentId ).sendMessageToUser("OpponentJoinTable;" + owner.getUserName() + ";"); }
                catch (UnknownUserIdException e) {
                    // This user might be deleted
                    System.out.println("[ERROR] User " + id + " don't exists anymore");
                    return;
                }

                try {
                    owner.sendMessageToUser("OpponentName;" + userManager.getUser(opponentId).getUserName() + ";");
                } catch (UnknownUserIdException e) {
                    e.printStackTrace();
                    // user don't exists
                    System.out.println("[ERROR] User " + id + " don't exists anymore");
                }

                return;
            }
        }

        owner.sendMessageToUser("OK");
    }
}
