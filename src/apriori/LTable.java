package code;
import java.util.ArrayList;
//the L table
public class LTable extends Table{

    private LTable(ArrayList<Row> rows, int iterationNumber){
        super(rows, iterationNumber);
    }

    //makes a new LTable from a CTable
    public static LTable fromCTable(CTable previousTable){
        ArrayList<Row> oldRows = previousTable.rows;
        ArrayList<Row> newRows = new ArrayList<Row>();
        //simply checks the rows to see if their support meets threshold
        for(Row row:oldRows) if(row.support>=Apriori.minSupCount) newRows.add(row);
        return new LTable(newRows, previousTable.iterationNumber);
    }

    @Override
    public String toString(){
        String output="";
        output+=("-------------------------\n");
        output+=("|    L Table #" + iterationNumber +"\n");
        output+=super.toString();
        return output;
    }
}
