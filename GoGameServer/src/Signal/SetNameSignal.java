package Signal;

import Connection.UserConnection;
import Exception.*;
import Connection.Server;

public class SetNameSignal extends Signal {

    private int id;
    private String name;

    public SetNameSignal(Server server, int newId, String newName) {
        setUserManager(server.getUserManager());
        id = newId;
        name = newName;
    }

    @Override
    public void execute() {
        UserConnection user;
        try { user = userManager.getUser(id); }
        catch (UnknownUserIdException e) {
            return;
        }

        if(userManager.checkValidUserName(name)) {

            user.setUserName(name);
            System.out.println("USER " + Integer.toString(id) + " set name to " + name);
            user.sendMessageToUser("OK");
        }
        else {
            System.out.println("USER " + Integer.toString(id) + " fail to change name");

            user.sendMessageToUser("Name is taken");
        }
    }
}
