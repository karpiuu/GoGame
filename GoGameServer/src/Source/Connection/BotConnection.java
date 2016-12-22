package Source.Connection;

import java.net.Socket;

public class BotConnection extends UserConnection {

    public BotConnection(Server newServer, Integer id) {
        super(newServer, null, id);
    }
}
