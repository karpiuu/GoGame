package Source.Game;

/**
 * State of the field, hold by enum value
 */
public enum StoneType {
    EMPTY, BLACK, WHITE;

    /**
     * @param type given in string
     * @return enum value
     */
    public static StoneType getTypeFromString(String type) {
        switch (type) {
            case "E" : return EMPTY;
            case "W" : return WHITE;
            case "B" : return BLACK;
            default: return EMPTY;
        }
    }

    /**
     * @return String value of type
     */
    @Override
    public String toString() {
        switch (this) {
            case EMPTY: return "E";
            case BLACK: return "B";
            case WHITE: return "W";
            default: return "E";
        }
    }
}
