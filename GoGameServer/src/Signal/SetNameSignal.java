package Signal;

import Connection.UserConnection;
import Exception.*;
import Connection.Server;
import Manager.UserManager;

public class SetNameSignal extends Signal {

    private int id;
    private String name;
    private UserManager userManager;

    public SetNameSignal(Server server, int newId, String newName) {
        setUserManager(server.getUserManager());
        id = newId;
        name = newName;
    }

    @Override
    public void execute() {
        UserConnection owner;
        try { owner = userManager.getUser(id); }
        catch (UnknownUserIdException e) {
            return;
        }

        if(userManager.checkValidUserName(name)) {

            owner.setUserName(name);
            System.out.println("USER " + Integer.toString(id) + " set name to " + name);
            owner.sendMessageToUser("OK");
        }
        else {
            System.out.println("USER " + Integer.toString(id) + " fail to change name");

            owner.sendMessageToUser("Name is taken");
        }
    }
}
