package Signal;

import Manager.TableManager;
import Manager.UserManager;

public abstract class Signal {

    protected UserManager userManager;

    protected void setUserManager(UserManager manager) {
        userManager = manager;
    }

    public abstract void execute();
}
