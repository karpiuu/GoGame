package GameEngine;

import java.util.ArrayList;

/**
 * Class GameEngine has function which operate on single game
 */
public class GameEngine {

    private Stone gameTab[][];
    private boolean deadStoneTab[][];

    private int size;
    private Stone playerStone;

    private boolean gameStart;
    private boolean gameEnd;
    private boolean youSelect;

    private boolean yourTurn;
    private int lastMove[];
    private int pointsBlack;
    private int pointsWhite;

    public GameEngine(int size) {
        pointsBlack = 0;
        pointsWhite = 0;

        gameStart = false;
        gameEnd = false;
        yourTurn = false;
        lastMove = new int[2];
        lastMove[0] = -1;

        playerStone = Stone.WHITE;

        this.size = size;
        gameTab = new Stone[size][size];

        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                gameTab[i][j] = Stone.EMPTY;
            }
        }
    }

    /**
     * Function place is setting stone on the gameboard.
     * @param x
     * @param y
     * @param type
     * @return
     */
    public boolean place(int x, int y, Stone type) {
        if( x >= 0 && x < size && y >= 0 && y < size) {
            gameTab[x][y] = type;
            return true;
        }
        return false;
    }

    /**
     * Function changes all fields which are forwarded from server
     * @param line
     */
    public void place(String line) {
        ArrayList<String> list = SignalOperation.parseString(line);
        int move[] = convertMove( Integer.parseInt(list.get(1)) );

        lastMove[0] = move[0];
        lastMove[1] = move[1];

        for(int i = 0; i < list.size(); i += 2) {

            move = convertMove( Integer.parseInt(list.get(i+1)) );
            place( move[0], move[1], Stone.getTypeFromString( list.get(i) ) );
        }
    }

    /**
     * Function getStone gets type of stone form field.
     * @param x
     * @param y
     * @return
     */
    public Stone getStone(int x, int y) {
        if( x >= 0 && x < size && y >= 0 && y < size) {
            return gameTab[x][y];
        }
        return Stone.EMPTY;
    }

    /**
     * Function getPlayerStone returns actual stone of player.
     * @return
     */
    public Stone getPlayerStone() {
        return playerStone;
    }

    public Stone getOpponentStone() {
        return ( playerStone.equals(Stone.BLACK) ? Stone.WHITE : Stone.BLACK );
    }


    /**
     * Function setPlayerStone sets stone of player to the variable.
     * @param stone
     */
    public void setPlayerStone(Stone stone) { playerStone = stone; }


    /**
     * Function startGame sets variable gameStart on true.
     */
    public void startGame() {
        gameStart = true;
        gameEnd = false;
    }


    public void setGameEnd(boolean state) {
        deadStoneTab = new boolean[size][size];
        gameEnd = state;
    }

    /**
     *
     * @return
     */
    public boolean getGameEnd() {
        return gameEnd;
    }

    /**
     * Function changeTurn sets variable yourTurn on opposite.
     */
    public void changeTurn() {
        yourTurn = !yourTurn;
    }

    public void setYouSelect(boolean state) {
        youSelect = state;
    }

    public boolean isYouSelect() {
        return youSelect;
    }

    /**
     * Function getGameStart returns actual value of gameStart variable: 0 or 1.
     * @return
     */
    public boolean getGameStart() {
        return gameStart;
    }

    /**
     * @return actual value of yourTurn variable: 0 or 1.
     */
    public boolean getYourTurn() {
        return yourTurn;
    }

    /**
     * @return Last placed move
     */
    public int[] getLastMove() { return lastMove; }

    public void changePoints(String line) {
        ArrayList<String> args = SignalOperation.parseString(line);

        pointsBlack = Integer.parseInt( args.get(0) );
        pointsWhite = Integer.parseInt( args.get(1) );
    }

    public Integer getPointsBlack() {
        return pointsBlack;
    }

    public Integer getPointsWhite() {
        return pointsWhite;
    }

    public boolean selectDeadStone(int x, int y, Stone type) {
        if( gameTab[x][y].equals(type) ) {
            deadStoneTab[x][y] = !deadStoneTab[x][y];
            return true;
        }
        else {
            return false;
        }
    }

    public boolean isStoneDead(int x, int y) {
        return deadStoneTab[x][y];
    }

    /**
     * Function gets number of row and column from single value.
     * @param value
     * @return
     */
    private int[] convertMove(int value) {
        int move[] = new int[2];

        move[0] = value % size;
        move[1] = value / size;

        return move;
    }
}
