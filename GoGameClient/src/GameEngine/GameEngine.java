package GameEngine;

import java.util.ArrayList;

/**
 * Class GameEngine has function which operate on single game
 */
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
        int move[];

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
    }


    /**
     * Function endGame sets variable gameStart on false.
     */
    public void endGame() {
        gameStart = false;
    }


    /**
     * Function changeTurn sets variable yourTurn on opposite.
     */
    public void changeTurn() {
        yourTurn = !yourTurn;
    }


    /**
     * Function getGameStart returns actual value of gameStart variable: 0 or 1.
     * @return
     */
    public boolean getGameStart() {
        return gameStart;
    }


    /**
     * Function getYourTurn returns actual value of yourTurn variable: 0 or 1.
     * @return
     */
    public boolean getYourTurn() {
        return yourTurn;
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
