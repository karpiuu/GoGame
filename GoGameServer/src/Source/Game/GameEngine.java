package Source.Game;

public class GameEngine {

    private Stone gameField[][];    // Actual state of the game
    private int size;

    public GameEngine(int size) {
        gameField = new Stone[size][size];

        this.size = size;

        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                gameField[i][j] = new Stone();
            }
        }
    }

    public int[] convertMove(int value) {
        int move[] = new int[2];

        move[0] = value % size;
        move[1] = value / size;

        return move;
    }

    public boolean place(int x, int y, StoneType type) {
        if( gameField[x][y].getStoneType().equals( StoneType.EMPTY ) ) {    // Check if field is empty
            gameField[x][y].setStoneType(type);
            return true;
        }
        else {
            return false;
        }
    }

    public boolean checkMove(int x, int y, StoneType type) {
        if( place(x,y,type) ) {
            if ( checkFullArea(x, y, type) ) return false;        // Sprawdza sam siebie
        }
        else {
            return false;
        }

        return true;

//        checkFullArea( x-1, y-1, type );
//        checkFullArea(   x, y-1, type );
//        checkFullArea( x+1, y-1, type );
//        checkFullArea( x+1,   y, type );
//        checkFullArea( x+1, y+1, type );
//        checkFullArea(   x, y+1, type );
//        checkFullArea( x-1, y+1, type );
//        checkFullArea( x-1,   y, type );
    }

    private boolean checkFullArea(int x, int y, StoneType type) {

        boolean isKilled = ifAreaIsFull(x, y, 1, type);
        clearTest();

        if (isKilled) {
            System.out.print("ELEMENT ZABITY: X: " + Integer.toString(x) + " Y: " + Integer.toString(y) + "\n");
        }
        return isKilled;
    }

    private boolean ifAreaIsFull(int x, int y, int number, StoneType type) {

        if( x < 0 || x >= 19 || y < 0 || y >= 19 ) return true;

        if( gameField[x][y].getTestType() <= number && gameField[x][y].getTestType() != 0 ) return true;

        if( gameField[x][y].getStoneType().equals(type) ) gameField[x][y].setTestType(number);
        else {
            if( gameField[x][y].getStoneType().equals( StoneType.EMPTY ) ) return false;
            else return true;
        }

        if( !ifAreaIsFull(  x, y-1, number+1, type) )  return false;
        if( !ifAreaIsFull(x+1,   y, number+1, type) )  return false;
        if( !ifAreaIsFull(  x, y+1, number+1, type) )  return false;
        if( !ifAreaIsFull(x-1,   y, number+1, type) )  return false;

        return true;
    }

    public void clearTest() {
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                gameField[i][j].setTestType(0);
            }
        }
    }
}
