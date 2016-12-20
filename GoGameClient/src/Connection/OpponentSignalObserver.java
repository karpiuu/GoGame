package Connection;

import Frames.GameFrame.GameFrame;

public class OpponentSignalObserver {

    GameFrame gameFrame;

    public OpponentSignalObserver() {

    }

    public boolean checkLine(String line) {
        if(line.contains("Stone")) {
            gameFrame.notifyGame(line);
            return true;
        }

        if(line.equals("Pass;")) {
            gameFrame.notifyGame(line);
            return true;
        }

        if(line.contains("StartGame")) {
            gameFrame.notifyGame(line);
            return true;
        }

        if(line.equals("GameEnd;")) {
            gameFrame.notifyGame(line);
            return true;
        }
        return false;
    }

    public void setObserver(GameFrame frame) {
        gameFrame = frame;
    }
}
