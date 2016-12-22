package Source.Manager;

import java.util.ArrayList;
import Source.Game.Table;
import Source.Exception.*;

/**
 * Manage all tables, add tables, delete and return them
 */
public class TableManager {

    private ArrayList<Table> table;        // Hold every table
    private ArrayList<Integer> freeTableId;  // Contain free table Id
    private Integer tablesCount;            // When full, Id for new table

    public TableManager() {
        table = new ArrayList<>();
        freeTableId = new ArrayList<>();
        tablesCount = 0;
    }

    /**
     * Adding new table to ArrayList
     */
    public int addTable() {
        int index;

        if(freeTableId.size() > 0) {
            index = freeTableId.get(0);
            freeTableId.remove(0);

            table.set(index, new Table(index));
        }
        else {
            table.add(new Table(tablesCount));
            index = tablesCount;
            tablesCount++;
        }

        System.out.println("Table " + Integer.toString(index) + " is created.");

        return index;
    }

    /**
     *
     * Returns tabe on given Id
     * @param id table id
     * @return table
     * @throws UnknownTableIdException If table id is incorrect
     */
    public Table getTable(int id) throws UnknownTableIdException {
        if(id < table.size()) {
            if(table.get(id) == null) throw new UnknownTableIdException(id);
            return table.get(id);
        }
        else {
            throw new UnknownTableIdException(id);
        }
    }

    /**
     * Returns ArrayList of tables
     * @return ArrayList
     */
    public ArrayList<Table> getAllTables() {
        return table;
    }

    /**
     * Delete table of given id
     * @param id id of table
     * @throws UnknownTableIdException throw Source.Exception when given ID is out of bounds
     */
    public void deleteTable(int id) {
        if(id < table.size() ) {
            if(table.get(id) != null) {
                table.set(id, null);
                freeTableId.add(id);
            }
        }
    }
}
