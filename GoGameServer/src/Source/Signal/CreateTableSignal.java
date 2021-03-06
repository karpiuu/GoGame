package Source.Signal;

import Source.Connection.Server;
import Source.Connection.UserConnection;
import Source.Manager.TableManager;
import Source.Exception.*;

/**
 * Signal for Table creation
 */
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
            int index = tableManager.addTable();
            tableManager.getTable( index ).sitDown(id);
            userManager.getUser(id).sitDown( index );

        }
        catch (FullTableException e) {
            System.out.println("[ERROR] USER " + id.toString() + " can't join to table, table is full");
            owner.sendMessageToUser("Table is full");
            return;
        }
        catch (UnknownTableIdException e) {
            System.out.println("[ERROR] USER " + id.toString() + " can't join table, table doesn't exist");
            owner.sendMessageToUser("Table don't exists");
            return;
        }
        catch (UnknownUserIdException e) {
            //User don't exists???
            System.out.println("[ERROR] USER " + id.toString() + " doesn't exist");
            owner.sendMessageToUser("You don't exist in server");
        }

        owner.sendMessageToUser("OK");
    }
}
