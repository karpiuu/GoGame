package Signal;

import Connection.Server;
import Connection.UserConnection;
import Exception.*;

public class RefreshSignal extends Signal {

    int id;

    public RefreshSignal(Server server, int newId) {
        setUserManager(server.getUserManager());
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

        try { owner = userManager.getUser(id); }
        catch (UnknownUserIdException e) {
            System.out.println("USER " + Integer.toString(id) + " dont exists");
        }

        owner.sendMessageToUser(names);
    }
}
