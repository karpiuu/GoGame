package Source.Manager;

import Source.Connection.Server;
import Source.Exception.UnknownUserIdException;
import Source.Connection.UserConnection;

import java.net.Socket;
import java.util.ArrayList;

public class UserManager {

    private ArrayList<UserConnection> user;     // Hold every user Thread
    private ArrayList<Integer> freeUserId;      // Contain free ID for new users
    private Integer userCount;                  // If every slot in array is taken, describe ID for new user

    public UserManager() {

        user = new ArrayList<>();
        freeUserId = new ArrayList<>();
        userCount = 0;
    }

    /**
     * Adding new user
     * @param server which create new user
     * @param socket for client
     */
    public void addUser(Server server, Socket socket) {
        int index;

        if(freeUserId.size() > 0) {
            index = freeUserId.get(0);
            freeUserId.remove(0);

            user.set(index, new UserConnection(server, socket, index));
            user.get(index).start();
        }
        else {
            user.add(new UserConnection(server, socket, userCount));
            user.get(userCount).start();
            userCount++;
        }
    }

    /**
     * Returns user on given Id
     * @param id user id
     * @return user
     * @throws UnknownUserIdException throw Source.Exception when given ID is out of bounds
     */
    public UserConnection getUser(int id) throws UnknownUserIdException {
        if(id < user.size()) {
            return user.get(id);
        }
        else {
            throw new UnknownUserIdException(id);
        }
    }

    public ArrayList<UserConnection> getAllUsers() {
        return user;
    }

    /**
     * Delete user of given id
     * @param id id of user
     * @throws UnknownUserIdException throw Source.Exception when given ID is out of bounds
     */
    public void deleteUser(int id) throws UnknownUserIdException {
        if(id < user.size() ) {
            user.set(id, null);
            freeUserId.add(id);
        }
        else {
            throw new UnknownUserIdException(id);
        }
    }

    /**
     * Check if name is free in server
     * @param name new user name
     * @return True - name is free, False - name already taken
     */
    public boolean checkValidUserName(String name) {
        return true;
    }
}
