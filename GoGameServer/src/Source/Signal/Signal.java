package Source.Signal;

import Source.Connection.UserConnection;
import Source.Exception.UnknownUserIdException;
import Source.Manager.UserManager;

/**
 * Base for every signal
 */
public abstract class Signal {

    protected Integer id;
    protected UserManager userManager;
    protected UserConnection owner;

    protected void setUserManager(UserManager manager) {
        userManager = manager;
    }

    public abstract void execute();

    protected void setOwner() {

        try { owner = userManager.getUser(id); }
        catch (UnknownUserIdException e) {
            // This user might be deleted
            System.out.println("[ERROR] User " + id + " don't exists anymore");
        }
    }
}
