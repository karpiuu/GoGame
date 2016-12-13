package Tests;

import Connection.SocketClient;
import org.junit.Test;

import static org.junit.Assert.*;

public class SocketClientTest {
    @Test
    public void readFromInput() throws Exception {

        SocketClient client = new SocketClient();
        String line = client.readFromInput();
        assertEquals("",line);
    }

}