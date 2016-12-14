package Source.Game;

public class Stone {

    private StoneType stoneType;
    private int testType;

    public Stone() {
        stoneType = StoneType.EMPTY;
        testType = 0;
    }


    public StoneType getStoneType() {
        return stoneType;
    }

    public void setStoneType(StoneType stoneType) {
        this.stoneType = stoneType;
    }

    public int getTestType() {
        return testType;
    }

    public void setTestType(int testType) {
        this.testType = testType;
    }
}
