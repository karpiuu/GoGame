package Source.Game;

import java.util.ArrayList;

public class GameEngine {

    private Stone gameField[][];    // Actual state of the game
    private Stone gameEndField[][];    // GameEnd state of the game
    private int size;
    private String stoneMove;
    private int lastMove;
    private ArrayList<Integer> lastKilled;
    private ArrayList<Integer> newKilled;

    private int nearStone[][];

    private int pointsBlack;
    private int pointsWhite;

    private boolean pointsTo;       // Describe who gets points after killing stones : False - Black, True - White
    private int numKill;

    private int pass;
    private int territoryAccept;

    private ArrayList<String> territoryValue;  // U - UNDEFINED, E - EMPTY, B - BLACK, W - WHITE
    private int territoryField[][];

    public GameEngine(int size) {
        this.size = size;
        nearStone = new int[][]{ { -1, -1 }, { 0, -1 }, { 1, -1 }, { 1, 0 }, { 1, 1 }, { 0, 1 }, { -1, 1 }, { -1, 0 } };

        startGame();
    }

    public String checkMove(int value, StoneType type) {

        newKilled = new ArrayList<>();

        boolean killed = false;

        int move[] = convertMove(value);

        if( place(move[0], move[1], type) ) {

            stoneMove = "YourMove;" + type.toString() + ";" + Integer.toString(value) + ";";

            StoneType opponentType = ( type.equals(StoneType.BLACK) ? StoneType.WHITE : StoneType.BLACK );

            // Check if this move kill any other stone
            for(int i = 0; i < 8; i++) {
                if (checkFullArea(move[0] + nearStone[i][0], move[1] + nearStone[i][1], opponentType)) {
                    // Checks if move is against rule KO
                    if (!checkKO(convertMove(move[0] + nearStone[i][0], move[1] + nearStone[i][1]), value)) {
                        gameField[move[0]][move[1]].setStoneType(StoneType.EMPTY);
                        return "That move creates infinite loop";
                    }
                    pointsTo = (!type.equals(StoneType.BLACK));
                    numKill = 0;
                    deleteArea(move[0] + nearStone[i][0], move[1] + nearStone[i][1]);

                    if( numKill == 1 ) {
                        newKilled.add( convertMove( move[0] + nearStone[i][0], move[1] + nearStone[i][1] ) );
                    }

                    killed = true;
                }
            }

            // Check himself
            if ( !killed && checkFullArea(move[0], move[1], type) ) {
                gameField[move[0]][move[1]].setStoneType(StoneType.EMPTY);
                return "This move is suicide";
            }
        }
        else {
            return "This field is already taken";
        }

        stoneMove += "Points;" + Integer.toString(pointsBlack) + ";" + Integer.toString(pointsWhite) + ";";

        lastKilled = newKilled;
        lastMove = value;
        pass = 0;
        return stoneMove;
    }

    public void userPass() {
        pass += 1;
    }

    public int getUserPass() {
        return pass;
    }

    private boolean checkKO(int move, int playerMove) {
        for (Integer value : lastKilled) {
            if(value == playerMove) {
                if(lastMove == move) return false;
            }
        }
        return true;
    }

    public int[] convertMove(int value) {
        int move[] = new int[2];

        move[0] = value % size;
        move[1] = value / size;

        return move;
    }

    private int convertMove(int x, int y) {
        return x + y*size;
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

            if (!gameField[x][y].getStoneType().equals(type)) return false;

            clearTest();
            boolean isKilled = ifAreaIsFull(x, y, 1, type);

            if (isKilled) {
                System.out.print("ELEMENT ZABITY: X: " + Integer.toString(x) + " Y: " + Integer.toString(y) + "\n");
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

                stoneMove += "E;" + Integer.toString(x + (y * size)) + ";";
                numKill++;

                if(!pointsTo) pointsBlack++;
                else pointsWhite++;

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

    public void setGameEnd(boolean state) {
        if(state) {
            gameEndField = new Stone[size][size];

            for(int i = 0; i < size; i++) {
                for(int j = 0; j < size; j++) {
                    gameEndField[i][j] = gameField[i][j];
                }
            }
        }
    }

    public void clearDeadStone(int x, int y) {
        gameEndField[x][y].setStoneType(StoneType.EMPTY);
    }

    public String getTerritory() {

        territoryValue = new ArrayList<>();
        territoryField = new int[size][size];

        int counter = 1;

        territoryValue.add(0,"E");

        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                if( territoryField[i][j] == 0 ) {

                    territoryValue.add(counter,"U");
                    fillTerritory(i, j, counter);

                    if(territoryValue.get(counter).equals("U")) {
                        territoryValue.set(counter, "E");
                    }

                    counter++;
                }
            }
        }

        return getTerritoryString();
    }

    private String getTerritoryString() {
        String str = "";

        for (int j = 0; j < size; j++) {
            for(int i = 0; i < size; i++) {
                if( !territoryValue.get(territoryField[i][j]).equals("E") ) {
                    str += territoryValue.get(territoryField[i][j]) + ";";
                    str += Integer.toString(convertMove(i, j)) + ";";
                }
            }
        }
        return str;
    }

    public void fillTerritory(int x, int y, int value) {
        if( x < 0 || x >= size || y < 0 || y >= size ) return;

        if(territoryField[x][y] == 0) {
            if(gameEndField[x][y].getStoneType().equals(StoneType.EMPTY)) {
                //EMPTY
                territoryField[x][y] = value;

                fillTerritory(x-1,   y, value);
                fillTerritory(x+1,   y, value);
                fillTerritory(  x, y-1, value);
                fillTerritory(  x, y+1, value);
            }
            else if(gameEndField[x][y].getStoneType().equals(StoneType.WHITE)) {
                // WHITE
                if(territoryValue.get(value).equals("U")) {
                    territoryValue.set(value, "W");
                }
                else if(territoryValue.get(value).equals("B")){
                    territoryValue.set(value, "E");
                }
            }
            else {
                // BLACK
                if(territoryValue.get(value).equals("U")) {
                    territoryValue.set(value, "B");
                }
                else if(territoryValue.get(value).equals("W")){
                    territoryValue.set(value, "E");
                }
            }
        }
    }

    public boolean isTerritoryAccepted() {
        return (territoryAccept >= 2);
    }

    public void setTerritoryAccept(int territoryAccept) {
        this.territoryAccept = territoryAccept;
    }

    public void userAcceptTerritory() {
        territoryAccept++;
    }

    public String getPoints() {

        double blackTerritoryPoints = 0;
        double whiteTerritoryPoints = 0;

        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                if( territoryValue.get( territoryField[i][j] ).equals("B") ) {
                    blackTerritoryPoints++;
                }
                else if( territoryValue.get( territoryField[i][j] ).equals("W") ) {
                    whiteTerritoryPoints++;
                }
            }
        }

        String result = "";

        blackTerritoryPoints += pointsBlack;
        whiteTerritoryPoints += pointsWhite + 6.5;

        result += ( blackTerritoryPoints > whiteTerritoryPoints ? "Black wins! " : "White wins! " );
        result += "Black gets " + Double.toString(blackTerritoryPoints) + " points";
        result += " White gets " + Double.toString(whiteTerritoryPoints) + " points.";

        return result;
    }

    public void startGame() {
        pointsBlack = 0;
        pointsWhite = 0;
        pointsTo = false;
        gameField = new Stone[size][size];
        lastKilled = new ArrayList<>();
        pass = 0;

        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                gameField[i][j] = new Stone();
            }
        }
    }

    public void returnToGame() {
        pass = 0;
        territoryAccept = 0;
    }
}
