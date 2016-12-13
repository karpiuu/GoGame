package Source.Signal;

import Source.Connection.Server;
import Source.Connection.UserConnection;
import Source.Exception.FullTableException;
import Source.Exception.UnknownTableIdException;
import Source.Exception.UnknownUserIdException;
import Source.Manager.TableManager;

/**
 * UnknownSignal from user
 */
public class UnknownSignal extends Signal {

    public UnknownSignal(Server server, int newId) {
        setUserManager(server.getUserManager());
        id = newId;
        setOwner();
    }

    @Override
    public void execute() {
        owner.sendMessageToUser("UnknownSignal");
    }
}
