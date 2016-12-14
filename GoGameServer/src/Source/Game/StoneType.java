package Source.Game;

public enum StoneType {
    EMPTY, BLACK, WHITE;

    public static StoneType getTypeFromString(String type) {
        switch (type) {
            case "E" : return EMPTY;
            case "W" : return WHITE;
            case "B" : return BLACK;
            default: return EMPTY;
        }
    }

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
