package Signal;

import Connection.Server;
import Connection.UserConnection;
import Exception.*;
import Game.Table;
import Manager.TableManager;
import Manager.UserManager;

public class RefreshSignal extends Signal {

    private int id;
    private TableManager tableManager;

    public RefreshSignal(Server server, int newId) {
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

        owner.sendMessageToUser(names);

        try { owner = userManager.getUser(id); }
        catch (UnknownUserIdException e) {
            System.out.println("USER " + Integer.toString(id) + " dont exists");
        }

        String tables = "Tables;";
        String idTable;

        for(Table table : tableManager.getAllTables() ) {

            if(table != null) {
                idTable = table.getId();
                if(idTable != null) {
                    tables += table.getUsers();
                }
            }
        }

        owner.sendMessageToUser(tables);
    }
}
