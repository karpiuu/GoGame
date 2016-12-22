package Source.Game;

/**
 * Type of one field
 */
public class Stone {

    private StoneType stoneType;    // Type of stone
    private int testType;           // Type need is test physics

    public Stone() {
        stoneType = StoneType.EMPTY;
        testType = 0;
    }

    /**
     * @return stone type of this field
     */
    public StoneType getStoneType() {
        return stoneType;
    }

    /**
     * @param stoneType given stone type
     */
    public void setStoneType(StoneType stoneType) {
        this.stoneType = stoneType;
    }

    /**
     * @return test type, need for physics
     */
    public int getTestType() {
        return testType;
    }

    /**
     * @param testType given test type
     */
    public void setTestType(int testType) {
        this.testType = testType;
    }
}
