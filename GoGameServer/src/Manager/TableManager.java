package Manager;

import java.util.ArrayList;

public class TableManager {

    //private ArrayList<Table> tables;        // Hold every table
    private ArrayList<Integer> freeTableId;  // Contain free table Id
    private Integer tablesCount;            // When full, Id for new table

    public TableManager() {
        //tables = new ArrayList<>();
        freeTableId = new ArrayList<>();
        tablesCount = 0;
    }
}
