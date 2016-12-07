package Source.Signal;

import Source.Connection.Server;
import Source.Connection.UserConnection;
import Source.Manager.TableManager;
import Source.Exception.*;

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
        } catch (FullTableException e) {
            owner.sendMessageToUser("Table is full");
            return;
        } catch (UnknownTableIdException e) {
            owner.sendMessageToUser("Table don't exists");
            return;
        }

        owner.sendMessageToUser("OK");
    }
}
