package Source.Signal;

import Source.Connection.UserConnection;
import Source.Exception.UnknownUserIdException;
import Source.Manager.UserManager;

public abstract class Signal {

    protected UserManager userManager;
    protected Integer id;
    protected UserConnection owner;

    protected void setUserManager(UserManager manager) {
        userManager = manager;
    }

    public abstract void execute();

    protected void setOwner() {

        try { owner = userManager.getUser(id); }
        catch (UnknownUserIdException e) {
            return;
        }
    }
}
