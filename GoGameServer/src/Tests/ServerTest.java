package Tests;

import Source.Connection.Server;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ServerTest {
    Server server;

    @Test
    public void addBot() throws Exception {
        server = new Server();
        int index = server.addBot();
        assertEquals(0, index);
    }
}