package Source.Signal;

import Source.Connection.Server;
import Source.Connection.UserConnection;
import Source.Manager.TableManager;
import Source.Exception.*;

public class CreateTableSignal extends Signal {

    private TableManager tableManager;

    public CreateTableSignal(Server server, int newId) {
        setUserManager(server.getUserManager());
        tableManager = server.getTableManager();
        id = newId;

        setOwner();
    }

    @Override
    public void execute() {

        try {
            tableManager.getTable( tableManager.addTable() ).sitDown(id);
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
