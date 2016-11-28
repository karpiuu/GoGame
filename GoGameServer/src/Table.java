import Exception.FullTableException;
import Exception.WrongUserNumberException;
import Exception.UnknownUserIdException;

public class Table {
    private Integer user[];

    public Table() {
        user = new Integer[2];
    }

    public void sitDown(Integer userId) throws FullTableException {
        if(user[0] == null) {
            user[0] = userId;
        }
        else if(user[1] == null) {
            user[1] = userId;
        }
        else {
            throw new FullTableException();
        }
    }

    public Integer getUserId(Integer place) throws WrongUserNumberException  {
        if(place == 0 || place == 1) {
            return user[place];
        }
        else {
            throw new WrongUserNumberException();
        }
    }

    public void standUp(Integer userId) throws UnknownUserIdException {
        if(user[0].equals(userId)) {
            user[0] = null;
        }
        else if(user[1].equals(userId)) {
            user[1] = null;
        }
        else {
            throw new UnknownUserIdException();
        }

    }
}
