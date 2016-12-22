package Tests;

import Source.Connection.UserConnection;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UserConnectionTest {
    UserConnection user;

    @Before
    public void setUp() throws Exception {
        user = new UserConnection(null, null, null);
    }

    @Test
    public void getUserName() throws Exception {
        assertEquals(user.getUserName(), null);
    }

    @Test
    public void setUserName() throws Exception {
        user.setUserName("Tomasz");

        assertEquals(user.getUserName(), "Tomasz");
    }

}