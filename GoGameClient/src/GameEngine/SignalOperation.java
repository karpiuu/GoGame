package GameEngine;

import java.util.ArrayList;

/**
 * Parsing line from server
 */
public class SignalOperation {

    /**
     * Function parseString changes line to arraylist with arguments
     * @param line
     * @return
     */
    public static ArrayList<String> parseString(String line) {

        if( line.length() > 0 ) {
            ArrayList<String> argv = new ArrayList<>();

            int start = line.indexOf(';',0)+1;
            int end = 1;

            while (start < line.length() && end != -1) {
                end = line.indexOf(';', start);
                if( end != -1 ) {
                    argv.add(line.substring(start, end));
                    start = end + 1;
                }
            }
            return argv;
        }
        return null;
    }
}
