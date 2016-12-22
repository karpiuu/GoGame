package Source.Game;

import Source.Exception.*;

/**
 * Class which desciribe lobby tables, and state of game.
 */
public class Table {
    private Integer idUserBlack;    // Id of user playing Black
    private Integer idUserWhite;    // Id of user playing While
    private Integer id;             // Id of table
    private Integer activePlayer;   // Id of current player making move
    private boolean gameStart;      // If game stats
    private boolean gameEnd;        // If game end phase started
    private boolean botGame;        // If this is bot game

    private GameEngine gameEngine;  // Checks if move is correct, or give stones to delete, holds game table

    public Table(Integer index) {
        idUserBlack = null;
        idUserWhite = null;
        id = index;
        gameStart = false;
        gameEnd = false;
        gameEngine = new GameEngine(19);
    }

    /**
     * Adds user to table
     * @param userId new user
     * @throws FullTableException is Table is full (2+ users)
     */
    public void sitDown(Integer userId) throws FullTableException {
        if(idUserBlack == null) {
            idUserBlack = userId;
            return;
        }

        else if(idUserWhite == null) {
            idUserWhite = userId;
            return;
        }

        throw new FullTableException();
    }

    /**
     * Removes user from table
     * @param userId user which want to stand up
     * @throws UnknownUserIdException User isn't sitting in this table
     */
    public void standUp(Integer userId) throws UnknownUserIdException {
        if(idUserBlack != null) {
            if(idUserBlack.equals(userId)) {
                idUserBlack = null;
                return;
            }
        }

        if(idUserWhite != null) {
            if(idUserWhite.equals(userId)) {
                idUserWhite = null;
                return;
            }
        }

        throw new UnknownUserIdException(userId);
    }

    /**
     * @return if you are able to start game in this table
     */
    public boolean startGame() {
        if( getUserCount() >= 2 ) {
            activePlayer = idUserBlack;
            gameStart = true;
            gameEnd = false;
            gameEngine.startGame();
            return true;
        }
        return false;
    }

    /**
     * Sets game to previous state
     */
    public void setReturnToGame() {
        gameStart = true;
        gameEnd = false;
        activePlayer = idUserBlack;
    }

    /**
     * @return if game started
     */
    public boolean getGameStart() {
        return gameStart;
    }

    /**
     * @return active player
     */
    public Integer getActivePlayer() {
        return activePlayer;
    }

    /**
     * @param id sets new active player
     */
    public void setActivePlayer(int id) {
        activePlayer = id;
    }

    /**
     * @return unactive player
     */
    public Integer getUnactivePlayer() {
        if( !idUserWhite.equals(activePlayer) ) return idUserWhite;
        else return idUserBlack;
    }

    /**
     * @return Number of users in table
     */
    public int getUserCount() {
        int count = 0;

        if( idUserWhite != null ) count++;
        if( idUserBlack != null ) count++;

        return count;
    }

    /**
     * @return Id of User playing Black
     */
    public int getIdUserBlack() {
        if(idUserBlack == null) return -1;
        return idUserBlack;
    }

    /**
     * @return Id of User playing White
     */
    public int getIdUserWhite() {
        if(idUserWhite == null) return -1;
        return idUserWhite;
    }

    /**
     * @return This table id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @return gameEngine of this table
     */
    public GameEngine getGameEngine() {
        return gameEngine;
    }

    /**
     * @return if game is in ending phase
     */
    public boolean isGameEnd() {
        if( gameEngine.getUserPass() >= 2 ) {
            gameEnd = true;
            gameEngine.setGameEnd(true);
        }
        return gameEnd;
    }

    /**
     * @param state sets game to played with bot
     */
    public void setBotGame(boolean state) {
        botGame = state;
    }

    /**
     * @return if game is a bot game
     */
    public boolean isBotGame() {
        return botGame;
    }
}
