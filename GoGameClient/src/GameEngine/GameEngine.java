package GameEngine;

import java.util.ArrayList;

public class GameEngine {
    private Stone gameTab[][];
    private int size;
    private Stone playerStone;
    private boolean gameStart;
    private boolean yourTurn;

    public GameEngine(int size) {
        gameStart = false;
        yourTurn = false;

        playerStone = Stone.WHITE;

        this.size = size;
        gameTab = new Stone[size][size];

        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                gameTab[i][j] = Stone.EMPTY;
            }
        }
    }

    public boolean place(int x, int y, Stone type) {
        if( x >= 0 && x < size && y >= 0 && y < size) {
            gameTab[x][y] = type;
            return true;
        }
        return false;
    }

    public void place(String line) {
        ArrayList<String> list = SignalOperation.parseString(line);
        int move[];

        for(int i = 0; i < list.size(); i += 2) {

            move = convertMove( Integer.parseInt(list.get(i+1)) );
            place( move[0], move[1], Stone.getTypeFromString( list.get(i) ) );
        }
    }

    public Stone getStone(int x, int y) {
        if( x >= 0 && x < size && y >= 0 && y < size) {
            return gameTab[x][y];
        }
        return Stone.EMPTY;
    }

    public Stone getPlayerStone() {
        return playerStone;
    }

    public void setPlayerStone(Stone stone) { playerStone = stone; }

    public void startGame() {
        gameStart = true;
    }

    public void endGame() {
        gameStart = false;
    }

    public void changeTurn() {
        yourTurn = !yourTurn;
    }

    public boolean getGameStart() {
        return gameStart;
    }

    public boolean getYourTurn() {
        return yourTurn;
    }

    private int[] convertMove(int value) {
        int move[] = new int[2];

        move[0] = value % size;
        move[1] = value / size;

        return move;
    }
}
