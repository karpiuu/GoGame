package Source.Game;

public class GameEngine {

    private Stone gameField[][];    // Actual state of the game
    private int size;
    private String stoneMove;

    public GameEngine(int size) {
        gameField = new Stone[size][size];

        this.size = size;

        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                gameField[i][j] = new Stone();
            }
        }
    }

    public String checkMove(int value, StoneType type) {

        boolean killed = false;

        int move[] = convertMove(value);

        if( place(move[0], move[1], type) ) {

            stoneMove = "YourMove;" + type.toString() + ";" + Integer.toString(value) + ";";

            StoneType opponentType = ( type.equals(StoneType.BLACK) ? StoneType.WHITE : StoneType.BLACK );

            if( checkFullArea( move[0]-1, move[1]-1, opponentType ) ) killed = true;
            if( checkFullArea( move[0],   move[1]-1, opponentType ) ) killed = true;
            if( checkFullArea( move[0]+1, move[1]-1, opponentType ) ) killed = true;
            if( checkFullArea( move[0]+1,   move[1], opponentType ) ) killed = true;
            if( checkFullArea( move[0]+1, move[1]+1, opponentType ) ) killed = true;
            if( checkFullArea(   move[0], move[1]+1, opponentType ) ) killed = true;
            if( checkFullArea( move[0]-1, move[1]+1, opponentType ) ) killed = true;
            if( checkFullArea( move[0]-1,   move[1], opponentType ) ) killed = true;

            if ( !killed && checkFullArea(move[0], move[1], type) ) return "This move is suicide";        // Sprawdza sam siebie
        }
        else {
            return "This field is already taken";
        }

        return stoneMove;
    }

    private int[] convertMove(int value) {
        int move[] = new int[2];

        move[0] = value % size;
        move[1] = value / size;

        return move;
    }

    private boolean place(int x, int y, StoneType type) {
        if( gameField[x][y].getStoneType().equals( StoneType.EMPTY ) ) {    // Check if field is empty
            gameField[x][y].setStoneType(type);
            return true;
        }
        else {
            return false;
        }
    }

    private boolean checkFullArea(int x, int y, StoneType type) {

        if( x >= 0 && x < size && y >= 0 && y < size ) {

            if (gameField[x][y].getStoneType().equals(StoneType.EMPTY)) return false;

            clearTest();
            boolean isKilled = ifAreaIsFull(x, y, 1, type);

            if (isKilled) {
                System.out.print("ELEMENT ZABITY: X: " + Integer.toString(x) + " Y: " + Integer.toString(y) + "\n");
                deleteArea(x, y);
            }
            return isKilled;
        }
        else {
            return false;
        }
    }

    private boolean ifAreaIsFull(int x, int y, int number, StoneType type) {

        if( x < 0 || x >= size || y < 0 || y >= size ) return true;

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

    private void deleteArea(int x, int y) {
        if( x >= 0 && x < size && y >= 0 && y < size ) {
            if( gameField[x][y].getTestType() > 0 ) {
                gameField[x][y].setTestType(0);
                gameField[x][y].setStoneType(StoneType.EMPTY);

                stoneMove += "E;" + Integer.toString(x + (y * 19)) + ";";

                deleteArea(x - 1, y);
                deleteArea(x + 1, y);
                deleteArea(x, y - 1);
                deleteArea(x, y + 1);
            }
        }
    }

    public void clearTest() {
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                gameField[i][j].setTestType(0);
            }
        }
    }
}
