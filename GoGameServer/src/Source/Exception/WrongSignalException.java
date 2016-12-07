package Source.Exception;

public class WrongSignalException extends Exception {
    public WrongSignalException(String line) {
        System.out.println("Signal [" + line + "] is incorrect");
    }
}
