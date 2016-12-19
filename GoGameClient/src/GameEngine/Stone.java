package GameEngine;

/**
 * Enum class has function which retruns type of Stone and parses to String
 */
public enum Stone {
    EMPTY, BLACK, WHITE;

    /**
     * Function getTypeFromString gets type of stone with switch-case.
     * @param type
     * @return
     */
    public static Stone getTypeFromString(String type) {
        switch (type) {
            case "E" : return EMPTY;
            case "W" : return WHITE;
            case "B" : return BLACK;
            default: return EMPTY;
        }
    }

    /**
     * Function toString returns type stone as String
     * @return
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
