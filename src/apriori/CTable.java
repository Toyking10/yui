package code;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

//the candidate table
public class CTable extends Table {

    private CTable(ArrayList<Row> rows, int iterationNumber) {
        super(rows, iterationNumber);
    }

    //this is the self-join
    public static CTable fromLTable(LTable previousTable) {
        ArrayList<ArrayList<Integer>> newRowIdentifiers = new ArrayList<ArrayList<Integer>>();
        for (int i = 0; i < previousTable.rows.size(); i++) {
            for (int j = i+1; j < previousTable.rows.size(); j++) {
                //if alpha the same, join
                if(previousTable.rows.get(i).alpha.equals(previousTable.rows.get(j).alpha) &&
                     j!=previousTable.rows.size()){
                    ArrayList<Integer> al = new ArrayList<Integer>(previousTable.rows.get(i).alpha);
                    al.add(previousTable.rows.get(i).idenifier);
                    al.add(previousTable.rows.get(j).idenifier);
                    newRowIdentifiers.add(al);
                }
            }
        }
        //in this line, we call Parser.identifiersToRows which scans the DB and gives us the support
        return new CTable(Parser.identifiersToRows(newRowIdentifiers), previousTable.iterationNumber + 1);
    }

    //makes the initial CTable
    public static void makeInitialTable(HashMap<Integer, Integer> rowData) {
        if (Apriori.initialCTable != null) return;
        Set<Integer> keys = rowData.keySet();
        ArrayList<Row> rows = new ArrayList<Row>();
        for (int itemID : keys) {
            ArrayList<Integer> item = new ArrayList<Integer>();
            item.add(itemID);
            rows.add(new Row(item, rowData.get(itemID)));
        }
        Apriori.initialCTable = new CTable(rows, 1);
    }

    @Override
    public String toString() {
        String output = "";
        output += ("-------------------------\n");
        output += ("|    C Table #" + iterationNumber + "\n");
        output += super.toString();
        return output;
    }

}
