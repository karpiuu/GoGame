package Signal;

import Connection.Server;
import Connection.UserConnection;
import Manager.TableManager;
import Exception.*;

public class CreateTableSignal extends Signal {

    private int id;
    private TableManager tableManager;

    public CreateTableSignal(Server server, int newId) {
        setUserManager(server.getUserManager());
        tableManager = server.getTableManager();
        id = newId;
    }

    @Override
    public void execute() {
        UserConnection owner;
        try { owner = userManager.getUser(id); }
        catch (UnknownUserIdException e) {
            return;
        }

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
