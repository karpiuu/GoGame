package Source.Signal;

import Source.Connection.Server;
import Source.Exception.UnknownTableIdException;
import Source.Exception.UnknownUserIdException;
import Source.Game.Table;
import Source.Manager.TableManager;

/**
 * Signal run when user leave table, or quit game
 */
public class StandUpSignal extends Signal {

    private TableManager tableManager;

    public StandUpSignal(Server server, int newId) {
        setUserManager(server.getUserManager());
        tableManager = server.getTableManager();
        id = newId;

        setOwner();
    }

    @Override
    public void execute() {
        Table table;
        if(owner.getTableId() != null) {
            try { table = tableManager.getTable( owner.getTableId() ); }
            catch (UnknownTableIdException e) {
                // Unknown table id
                System.out.println("[ERROR] Can't stand up, table don't exists");
                return;
            }

            try { table.standUp(id); }
            catch (UnknownUserIdException e) {
                // User don't sit in this table
                System.out.println("[ERROR] User don't sit in this table");
                return;
            }

            owner.standUp();

            if(table.getUserCount() == 0) {
                tableManager.deleteTable( table.getId() );
                System.out.println("DELETE: Table " + table.getId());
            }
        }
    }
}
