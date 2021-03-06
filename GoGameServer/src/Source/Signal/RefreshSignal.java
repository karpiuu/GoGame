package Source.Signal;

import Source.Connection.Server;
import Source.Connection.UserConnection;
import Source.Exception.*;
import Source.Game.Table;
import Source.Manager.TableManager;

/**
 * Signal for refreshing user view
 */
public class RefreshSignal extends Signal {

    private TableManager tableManager;

    public RefreshSignal(Server server, int newId) {
        setUserManager(server.getUserManager());
        tableManager = server.getTableManager();
        id = newId;

        setOwner();
    }

    @Override
    public void execute() {

        String names = "Users;";
        String name;

        for(UserConnection user : userManager.getAllUsers() ) {

            if(user != null) {
                name = user.getUserName();
                if(name != null) {
                    names += name + ";";
                }
            }
        }

        try { owner = userManager.getUser(id); }
        catch (UnknownUserIdException e) {
            // User which run command might be deleted??
            System.out.println("[ERROR] USER " + Integer.toString(id) + " dont exists");
            return;
        }

        String tables = "Tables;";
        String idTable;

        for(Table table : tableManager.getAllTables() ) {

            if(table != null) {
                idTable = table.getId().toString();

                tables += ("#" + idTable + ";");

                if(table.getIdUserBlack() != -1 ) {
                    try { tables += (userManager.getUser(table.getIdUserBlack()).getUserName() + ";"); }
                    catch (UnknownUserIdException e) {
                        System.out.println("[ERROR] USER " + Integer.toString(id) + " dont exists");
                        tables += ";";
                    }
                }
                else {
                    tables += ";";
                }

                if(table.getIdUserWhite() != -1 ) {
                    try { tables += (userManager.getUser(table.getIdUserWhite()).getUserName() + ";"); }
                    catch (UnknownUserIdException e) {
                        System.out.println("[ERROR] USER " + Integer.toString(id) + " dont exists");
                        tables += ";";
                    }
                }
                else {
                    tables += ";";
                }
            }
        }

        owner.sendMessageToUser(names + tables);
    }
}
