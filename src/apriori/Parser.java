package code;

import java.util.ArrayList;
import java.util.HashMap;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

//parses input files and cleans them up
public class Parser {
    private static String filePath;
    //initilizes first c table and minsup
    public static void initalize(String filePath, float minSupPercent){
        try{
            //this map is used to make the initial CTable effeciently
            Parser.filePath=filePath;
            HashMap<Integer,Integer> itemCountMap = new HashMap<Integer,Integer>();
            File file = new File(filePath);
            Scanner scanner = new Scanner(file);
            final int supposedTransactionCount = scanner.nextInt();
            int realTransactionCount = 0;
            while(scanner.hasNextInt()) {
                // int transactionID = scanner.nextInt();
                scanner.nextInt();//for transaction id
                int itemCount = scanner.nextInt();
                ArrayList<Integer> items = new ArrayList<Integer>();
                for(int i=0;i<itemCount;i++) {
                    int itemID = scanner.nextInt();
                    itemCountMap.merge(itemID, 1, Integer::sum);//increments value by 1 or sets it to 1 if not present
                    items.add(itemID);
                }
                realTransactionCount++;
            }
            scanner.close();
            if(realTransactionCount!=supposedTransactionCount) throw new Exception("Transaction counts not consistent");
            CTable.makeInitialTable(itemCountMap);//makes the initial cTable without an additional database scan
            Apriori.minSupCount= (int) (0.5+((double) realTransactionCount * 0.01 * (double) minSupPercent));
        }
        catch (FileNotFoundException e){
            System.out.println("File doesn't exist there");
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static ArrayList<Row> identifiersToRows(ArrayList<ArrayList<Integer>> newRowIdentifiers){
        HashMap<ArrayList<Integer>,Integer> supportMap = new HashMap<ArrayList<Integer>,Integer>();
        ArrayList<Row> rows = new ArrayList<Row>();
        try {
            File file = new File(filePath);
            Scanner scanner = new Scanner(file);
            scanner.nextInt();
            while(scanner.hasNextInt()) {
                scanner.nextInt();//for skipping transaction id
                int itemCount = scanner.nextInt();
                ArrayList<Integer> items = new ArrayList<Integer>();
                //get all the items in the transaction
                for(int i=0;i<itemCount;i++) items.add(scanner.nextInt());
                //for each of our identifier lists
                for(ArrayList<Integer> itemList:newRowIdentifiers)
                //if this entry had every identifier, add to its support by 1
                    if(items.containsAll(itemList)) supportMap.merge(itemList, 1, Integer::sum);
            }
            scanner.close();
            //making the actual rows
            for(ArrayList<Integer> itemList:newRowIdentifiers)
                rows.add(new Row(itemList, supportMap.getOrDefault(itemList, 0)));

        } catch (Exception e) {
            System.out.println("Data file has been moved/edited during runtime.");
        }
        return rows;
    }

}
