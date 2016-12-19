package GameEngine.Tests;

import GameEngine.GameEngine;
import org.junit.Test;

import java.util.ArrayList;

import static GameEngine.SignalOperation.parseString;
import static org.junit.Assert.*;

/**
 * Created by SZYMON on 19.12.2016.
 */
public class SignalOperationTest {
    @Test
    public void parseStringtest() throws Exception {

        ArrayList<String> argv = new ArrayList<>();
        argv.add("B");
        argv.add("1");

        String line = "Stone;B;1;";
        
        assertEquals(argv, parseString(line) );

    }

}