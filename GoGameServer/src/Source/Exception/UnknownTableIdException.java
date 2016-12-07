package Source.Exception;

public class UnknownTableIdException extends Exception {
    public UnknownTableIdException(int id) {
        System.out.println("TABLE " + Integer.toString(id) + " dont exists");
    }
}
