package code;

import java.util.ArrayList;

public abstract class Table {
    protected final ArrayList<Row> rows;
    public final int iterationNumber;

    protected Table(ArrayList<Row> rows, int iterationNumber) {
        this.rows = rows;
        this.iterationNumber = iterationNumber;
    }

    public boolean isEmpty() {
        if (this.rows.size() == 0)
            return true;
        return false;
    }

    @Override
    public String toString() {
        String output = "";
        output += ("|  Item : Support   \n");
        for (Row row : rows) output += ("|   " + row);
        output += ("-------------------------\n");
        return output;
    }
}
