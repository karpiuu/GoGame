package Source.Exception;

/**
 * Exception thrown when table id is unknown
 */
public class UnknownTableIdException extends Exception {
    public UnknownTableIdException(int id) {
        System.out.println("TABLE " + Integer.toString(id) + " dont exists");
    }
}
