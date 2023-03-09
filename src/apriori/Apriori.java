package code;

import java.io.FileWriter;
import java.util.ArrayList;

public class Apriori {
    
    public static CTable initialCTable;
    public static int minSupCount;
    private static String OUTPUT_FILE_NAME = "MiningResult.txt";
    private static String OUTPUT_FILE_NAME_TABLES = "MiningResultTables.txt";

    public static void main(String[] args) {
        int minSupPercent;
        //ensure 2 arguments supplied
        if (args.length < 2) {
            System.out.println("Please supply filepath and support in arguments");
            return;
        }
        //get minimum support from arguments
        try {
            minSupPercent = Integer.parseInt(args[1]);
        } catch (Exception e) {
            System.out.println("Second argument wasn't a number");
            return;
        }
        //record start time
        long startTime = System.currentTimeMillis();
        //initilaizes the first CTable and sets our minSup static variable
        //to the appropriate number of support (count not percent)
        Parser.initalize(args[0], minSupPercent);
        if (initialCTable == null) return;//given an error in init

        ArrayList<Table> tables = doApriori();//do apriori
        
        long endTime = System.currentTimeMillis();//record end time

        //print outputs and write to file
        printFrequentPatterns(tables);
        printElapsedTime(endTime - startTime);
        writeOutputToFile(tables);
        
    }
    
    private static ArrayList<Table> doApriori() {
        ArrayList<Table> tables = new ArrayList<Table>();
        tables.add(initialCTable);

        //while the latest table isn't empty
        while (!tables.get(tables.size() - 1).isEmpty()) {
            //if it's a CTable, make the next LTable
            if (tables.get(tables.size() - 1) instanceof CTable) {
                CTable previous = (CTable) tables.get(tables.size() - 1);
                tables.add(LTable.fromCTable(previous));
            } else {//if it's an LTable, make the next CTable
                LTable previous = (LTable) tables.get(tables.size() - 1);
                tables.add(CTable.fromLTable(previous));
            }
        }
        return tables;
    }

    private static void printFrequentPatterns(ArrayList<Table> tables){
        int fps = 0;
        for(Table table:tables) if(table instanceof LTable) fps+=table.rows.size();
        System.out.println("|FPs| = " + fps);
    }

    private static void writeOutputToFile(ArrayList<Table> tables){
        try {
            FileWriter tableWriter = new FileWriter(OUTPUT_FILE_NAME_TABLES);//MiningResultTables.txt
            FileWriter writer = new FileWriter(OUTPUT_FILE_NAME);//MiningResult.txt
            int fps = 0;
            String supports = "";//the string to output for supports
            tableWriter.write("Minimum support is " + minSupCount + '\n');
            for(Table table:tables) {
                tableWriter.write(table.toString());
                if(table instanceof LTable) {
                    fps+=table.rows.size();
                    for(Row row:table.rows) supports+=row;
                }
            }
            writer.write("|FPs| = " + fps + '\n');
            writer.write(supports);
            writer.close();
            tableWriter.close();
            System.out.println("Output written to MiningResult.txt");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Error Occurred Writing Output Files");
        }
    }

    private static void printElapsedTime(long elapsedTime) {
            System.out.printf("%d.%03d sec\n", elapsedTime / 1000, elapsedTime % 1000);
    }
}