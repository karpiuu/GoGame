package Connection;

import Frames.GameFrame.GameFrame;

public class OpponentSignalObserver {

    GameFrame gameFrame;

    public OpponentSignalObserver() {

    }

    /**
     * Function check line with signal
     * @param line
     * @return true if commands is OK
     */
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

        if(line.contains("OpponentJoinTable;")) {
            gameFrame.notifyGame(line);
            return true;
        }

        if(line.equals("ReturnToGame;")) {
            gameFrame.notifyGame(line);
            return true;
        }

        if(line.equals("GameEnd;")) {
            gameFrame.notifyGame(line);
            return true;
        }

        if(line.contains("YesDeadStone;")) {
            gameFrame.notifyGame(line);
            return true;
        }

        if(line.contains("NoDeadStone;")) {
            gameFrame.notifyGame(line);
            return true;
        }

        if(line.contains("DeadStone")) {
            gameFrame.notifyGame(line);
            return true;
        }

        if(line.contains("TerritoryCheck")) {
            gameFrame.notifyGame(line);
            return true;
        }

        if(line.contains("GameResult")) {
            gameFrame.notifyGame(line);
            return true;
        }
        return false;
    }

    /**
     * Function setObserver sets actual fram with the game
     * @param frame
     */
    public void setObserver(GameFrame frame) {
        gameFrame = frame;
    }
}
