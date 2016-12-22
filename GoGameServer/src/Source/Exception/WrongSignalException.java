package Source.Exception;

/**
 * Exception thrown when the signal is wrong
 */
public class WrongSignalException extends Exception {
    public WrongSignalException(String line) {
        System.out.println("Signal [" + line + "] is incorrect");
    }
}
