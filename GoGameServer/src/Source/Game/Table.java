package Source.Game;

import Source.Exception.*;

/**
 * Class which desciribe lobby tables, and state of game.
 */
public class Table {
    private Integer idUserBlack;    // Id of user playing Black
    private Integer idUserWhite;    // Id of user playing While
    private Integer id;        // Id of table

    public Table(Integer index) {
        idUserBlack = null;
        idUserWhite = null;
        id = index;
    }

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

    public void standUp(Integer userId) throws UnknownUserIdException {
        if(idUserBlack == userId) {
            idUserBlack = null;
        }

        else if( idUserWhite == userId) {
            idUserWhite = null;
        }

        throw new UnknownUserIdException(userId);
    }

    public int getIdUserBlack() {
        if(idUserBlack == null) return -1;
        return idUserBlack;
    }
    public int getIdUserWhite() {
        if(idUserWhite == null) return -1;
        return idUserWhite;
    }

    public String getId() {
        return id.toString();
    }
}
