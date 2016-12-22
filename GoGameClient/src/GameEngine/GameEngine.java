package GameEngine;

import java.util.ArrayList;

/**
 * Class GameEngine has function which operate on single game
 */
public class GameEngine {

    private Stone gameTab[][];                  // table of the game
    private boolean deadStoneTabYours[][];      // table : is our stone dead
    private boolean deadStoneTabOpponent[][];   // table : is opponent stoned dead
    private Stone gameTerritory[][];            // table : every field belongs to W,B or E

    private int size;
    private Stone playerStone;

    private boolean gameStart;
    private boolean gameEnd;
    private boolean youSelect;
    private boolean youCheck;
    private boolean territoryCheck;

    private boolean yourTurn;
    private int lastMove[];
    private int pointsBlack;  // points for beaten stones - slaves
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

        clearStones();
    }

    /**
     * Function place is setting stone on the gameboard.
     * @param x position
     * @param y position
     * @param type of stone
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
     * @param x position
     * @param y position
     * @return
     */
    public Stone getStone(int x, int y) {
        if( x >= 0 && x < size && y >= 0 && y < size) {
            return gameTab[x][y];
        }
        return Stone.EMPTY;
    }


    /**
     *
     * @return size of board
     */
    public int getSize() {
        return size;
    }


    /**
     * Function sets your turn as state
     * @param state
     */
    public void setYourTurn(boolean state) {
        yourTurn = state;
    }



    /**
     * Function getPlayerStone returns actual stone of player.
     * @return
     */
    public Stone getPlayerStone() {
        return playerStone;
    }


    /**
     *
     * @return color if the opponent stone. If our stone is BLACK then function retruns WHITE, otherwise BLACK.
     */
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
    public void setStartGame(boolean state) {

        gameStart = state;
        lastMove[0] = -1;
        lastMove[1] = -1;

        clearStones();
        setReturnToGame();
    }


    /**
     * Function sets parametrs of the game to go to the previous state of the game.
     */
    public void setReturnToGame() {
        gameEnd = false;
        clearYoursDeadStone();
        clearOpponentDeadStone();
        clearTerritory();
    }


    /**
     * Function goes after each single field and sets every stone as EMPTY
     */
    private void clearStones() {
        gameTab = new Stone[size][size];

        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                gameTab[i][j] = Stone.EMPTY;
            }
        }
    }


    /**
     * Function sets parametrs of the game to end phase the game.
     * @param state
     */
    public void setGameEnd(boolean state) {
        deadStoneTabYours = new boolean[size][size];
        deadStoneTabOpponent = new boolean[size][size];
        gameEnd = state;
    }

    /**
     *
     * @return value of the gameEnd
     */
    public boolean getGameEnd() {
        return gameEnd;
    }


    /**
     *
     * @param state of our turn
     */
    public void setTurn(boolean state) {
        yourTurn = state;
    }


    /**
     * Function changeTurn sets variable yourTurn on opposite.
     */
    public void changeTurn() {
        yourTurn = !yourTurn;
    }


    /**
     * If you select dead stones.
     * @param state
     */
    public void setYouSelect(boolean state) {
        youSelect = state;
    }


    /**
     *
     * @return value of youSelect
     */
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


    /**
     * This function gets points for slaves.
     * @param line
     */
    public void changePoints(String line) {
        ArrayList<String> args = SignalOperation.parseString(line);

        pointsBlack = Integer.parseInt( args.get(0) );
        pointsWhite = Integer.parseInt( args.get(1) );
    }


    /**
     *
     * @return points of the black player for slaves
     */
    public Integer getPointsBlack() {
        return pointsBlack;
    }


    /**
     *
     * @return points of the white player for slaves.
     */
    public Integer getPointsWhite() {
        return pointsWhite;
    }


    /**
     * Change state of the dead stones
     * @param x position
     * @param y position
     * @param type of stone
     * @return if stone is property color, function returns true
     */
    public boolean selectDeadStone(int x, int y, Stone type) {
        if( gameTab[x][y].equals(type) ) {
            deadStoneTabYours[x][y] = !deadStoneTabYours[x][y];
            return true;
        }
        else {
            return false;
        }
    }

    /**
     *
     * @param x position
     * @param y position
     * @return true if is dead stone
     */
    public boolean isStoneDead(int x, int y) {
        return ( deadStoneTabYours[x][y] || deadStoneTabOpponent[x][y] );
    }


    /**
     *
     * @return places of all of dead stones of our player
     */
    public String getAllYoursDeadStones() {
        String text = "";

        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                if( deadStoneTabYours[i][j] ) {
                    text += Integer.toString(i + size * j) + ";";
                }
            }
        }
        return text;
    }


    /**
     *
     * @return places of all of dead stones of opponent player
     */
    public String getAllOpponentDeadStones() {
        String text = "";

        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                if( deadStoneTabOpponent[i][j] ) {
                    text += Integer.toString(i + size * j) + ";";
                }
            }
        }

        return text;
    }


    /** Function sets dead stones of the opponent
     * @param line
     */
    public void setOpponentDeadStone(String line) {
        ArrayList<String> args = SignalOperation.parseString(line);

        int move[];

        for(String position : args) {
            move = convertMove( Integer.parseInt(position) );

            deadStoneTabOpponent[move[0]][move[1]] = true;
        }

        youCheck = true;
    }


    /**
     * Table of the dead stones of opponent is cleared
     */
    public void clearOpponentDeadStone() {
        deadStoneTabOpponent = new boolean[size][size];
    }


    /**
     * Table of the our dead stones is cleared
     */
    private void clearYoursDeadStone() {
        deadStoneTabYours = new boolean[size][size];
    }


    /**
     * Function sets variable youCheck - you are checking yours dead stones
     * @param state
     */
    public void setYouCheck(boolean state) {
        youCheck = state;
    }


    /**
     *
     * @return youCheck
     */
    public boolean isYouCheck() {
        return youCheck;
    }


    /**
     * Sets territoryCheck on the state
     * @param state
     */
    public void setTerritoryCheck(boolean state) {
        territoryCheck = state;
    }


    /**
     *
     * @return territoryCheck
     */
    public boolean isTerritoryCheck() {
        return territoryCheck;
    }


    /**
     * Function sets stones to the gameTerritory
     * @param line
     */
    public void setTerritory(String line) {

        clearTerritory();

        ArrayList<String> args = SignalOperation.parseString(line);

        int move[];

        for(int i = 0; i < args.size(); i += 2) {
            move = convertMove( Integer.parseInt( args.get(i+1) ) );

            if(args.get(i).equals("B")) {
                gameTerritory[move[0]][move[1]] = Stone.BLACK;
            }
            else if(args.get(i).equals("W")) {
                gameTerritory[move[0]][move[1]] = Stone.WHITE;
            }
        }
    }


    /**
     *
     * @param x position
     * @param y position
     * @return territory from given field
     */
    public Stone getTerritory(int x, int y) {
        return gameTerritory[x][y];
    }


    /**
     * Table gameTerritory is filling by empty stones.
     */
    public void clearTerritory() {
        gameTerritory = new Stone[size][size];

        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                gameTerritory[i][j] = Stone.EMPTY;
            }
        }
    }


    /**
     * Function clears last move.
     */
    public void clearLastMove() {
        lastMove[0] = -1;
        lastMove[1] = -1;
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
