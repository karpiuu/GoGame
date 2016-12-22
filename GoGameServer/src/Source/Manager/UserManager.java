package Source.Manager;

import Source.Connection.BotConnection;
import Source.Connection.Server;
import Source.Exception.UnknownUserIdException;
import Source.Connection.UserConnection;

import java.net.Socket;
import java.util.ArrayList;

/**
 * Read signals from User I/O,
 * Add users and delete
 * Checks if name is valid in server
 */
public class UserManager {

    private volatile ArrayList<UserConnection> user;     // Hold every user Thread
    private ArrayList<Integer> freeUserId;               // Contain free ID for new users
    private Integer userCount;                           // If every slot in array is taken, describe ID for new user
    private ArrayList<String> invalidStringInName;

    public UserManager() {

        user = new ArrayList<>();
        freeUserId = new ArrayList<>();
        userCount = 0;

        initInvalidNames();
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

    public void addBot(Server server) {
        user.add( new BotConnection(server, 0));
        user.get(0).setUserName("Bot");
        userCount++;
    }

    public void initInvalidNames() {
        invalidStringInName = new ArrayList<>();

        invalidStringInName.add(";");
        invalidStringInName.add(" ");
        invalidStringInName.add("   ");
        invalidStringInName.add("CreateTable");
        invalidStringInName.add("DeadStone");
        invalidStringInName.add("NoDeadStone");
        invalidStringInName.add("NoTerritory");
        invalidStringInName.add("Pass");
        invalidStringInName.add("Refresh");
        invalidStringInName.add("ReturnToGame");
        invalidStringInName.add("SetName");
        invalidStringInName.add("SitDown");
        invalidStringInName.add("StandUp");
        invalidStringInName.add("StartGame");
        invalidStringInName.add("Stone");
        invalidStringInName.add("Surrender");
        invalidStringInName.add("YesDeadStone");
        invalidStringInName.add("YesTerritory");
        invalidStringInName.add("TerritoryCheck");
        invalidStringInName.add("GameResult");
        invalidStringInName.add("GameEnd");
        invalidStringInName.add("\\");

    }

    /**
     * Returns user on given Id
     * @param id user id
     * @return user
     * @throws UnknownUserIdException throw Source.Exception when given ID is out of bounds
     */
    public UserConnection getUser(int id) throws UnknownUserIdException {
        if(id < user.size() && id >= 0) {
            return user.get(id);
        }
        else {
            throw new UnknownUserIdException(id);
        }
    }

    /**
     * Return ArrayList of Users
     * @return all users
     */
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
        if(name.equals("")){
            return false;
        }

        for( String text : invalidStringInName ) {
            if(name.contains(text)) return false;
        }

        return true;
    }


}
