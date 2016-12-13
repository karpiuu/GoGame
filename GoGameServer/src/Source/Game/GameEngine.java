package Source.Game;

public class GameEngine {

    private Stone gameField[][];    // Actual state of the game
    private int size;

    public GameEngine(int size) {
        gameField = new Stone[size][size];

        this.size = size;

        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                gameField[i][j] = Stone.EMPTY;
            }
        }
    }

    public boolean checkMove(int x, int y, Stone type) {
        return true;
    }

    public int[] convertMove(int value) {
        int move[] = new int[2];

        move[0] = value % size;
        move[1] = value / size;

        return move;
    }
}
