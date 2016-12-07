package Source.Signal;

import Source.Connection.Server;
import Source.Connection.UserConnection;
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

        owner.sendMessageToUser("OK");
    }
}
