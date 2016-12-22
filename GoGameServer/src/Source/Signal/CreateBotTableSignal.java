package Source.Signal;

import Source.Connection.Server;
import Source.Exception.FullTableException;
import Source.Exception.UnknownTableIdException;
import Source.Exception.UnknownUserIdException;
import Source.Game.Table;
import Source.Manager.TableManager;

/**
 * Signal executed when table is created
 */
public class CreateBotTableSignal extends Signal {
    private TableManager tableManager;
    private Server server;

    public CreateBotTableSignal(Server server, int newId) {
        this.server = server;
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

        Table table;

        if(owner.getTableId() != null) {
            try {
                table = tableManager.getTable(owner.getTableId());
            } catch (UnknownTableIdException e) {
                // Unknown table id
                System.out.println("[ERROR] Can't start game, table don't exists");
                return;
            }

            int index = server.addBot();

            try {
                userManager.getUser(index).setGameEngine(table.getGameEngine());
            } catch (UnknownUserIdException e) {
                e.printStackTrace();
            }

            try {
                userManager.getUser(index).setUserName("Bot");
                userManager.getUser(index).sitDown(table.getId());
            } catch (UnknownUserIdException e) {
                e.printStackTrace();
            }

            try {
                table.sitDown(index);
            } catch (FullTableException e) {
                e.printStackTrace();
            }

            table.setBotGame(true);
            table.startGame();
        }
    }
}
