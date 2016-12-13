package Source.Signal;

import Source.Connection.Server;
import Source.Manager.TableManager;

public class StoneSignal extends Signal {

    private TableManager tableManager;

    public StoneSignal(Server server, int newId) {
        setUserManager(server.getUserManager());
        tableManager = server.getTableManager();
        id = newId;

        setOwner();
    }
    @Override
    public void execute() {

    }
}
