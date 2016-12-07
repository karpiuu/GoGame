package Source.Signal;

import Source.Connection.UserConnection;
import Source.Exception.*;
import Source.Connection.Server;

/**
 * Signal for setting user name
 */
public class SetNameSignal extends Signal {

    private String name;

    public SetNameSignal(Server server, int newId, String newName) {
        setUserManager(server.getUserManager());
        id = newId;
        name = newName;

        setOwner();
    }

    @Override
    public void execute() {

        if(userManager.checkValidUserName(name)) {

            owner.setUserName(name);
            System.out.println("USER " + Integer.toString(id) + " set name to " + name);
            owner.sendMessageToUser("OK");
        }
        else {

            System.out.println("USER " + Integer.toString(id) + " fail to change name");
            owner.sendMessageToUser("Name is incorrect, change name.");
        }
    }
}
