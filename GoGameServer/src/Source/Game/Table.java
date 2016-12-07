package Source.Game;

import Source.Exception.*;

/**
 * Class which desciribe lobby tables, and state of game.
 */
public class Table {
    private Integer idUserBlack;    // Id of user playing Black
    private Integer idUserWhite;    // Id of user playing While
    private Integer id;             // Id of table

    public Table(Integer index) {
        idUserBlack = null;
        idUserWhite = null;
        id = index;
    }

    /**
     * Adds user to table
     * @param userId new user
     * @throws FullTableException is Table is full (2+ users)
     */
    public void sitDown(Integer userId) throws FullTableException {
        if(idUserBlack == null) {
            idUserBlack = userId;
            return;
        }

        else if(idUserWhite == null) {
            idUserWhite = userId;
            return;
        }

        throw new FullTableException();
    }

    /**
     * Removes user from table
     * @param userId user which want to stand up
     * @throws UnknownUserIdException User isn't sitting in this table
     */
    public void standUp(Integer userId) throws UnknownUserIdException {
        if(idUserBlack != null) {
            if(idUserBlack.equals(userId)) {
                idUserBlack = null;
                return;
            }
        }

        if(idUserWhite != null) {
            if(idUserWhite.equals(userId)) {
                idUserWhite = null;
                return;
            }
        }

        throw new UnknownUserIdException(userId);
    }

    /**
     * @return Number of users in table
     */
    public int getUserCount() {
        int count = 0;

        if( idUserWhite != null ) count++;
        if( idUserBlack != null ) count++;

        return count;
    }

    /**
     * @return Id of User playing Black
     */
    public int getIdUserBlack() {
        if(idUserBlack == null) return -1;
        return idUserBlack;
    }

    /**
     * @return Id of User playing White
     */
    public int getIdUserWhite() {
        if(idUserWhite == null) return -1;
        return idUserWhite;
    }

    /**
     * @return This table id
     */
    public Integer getId() {
        return id;
    }
}
