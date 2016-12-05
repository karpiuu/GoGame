package Exception;

public class UnknownUserIdException extends Exception {
    public UnknownUserIdException(int id) {
        System.out.println("USER " + Integer.toString(id) + " dont exists");
    }
}
