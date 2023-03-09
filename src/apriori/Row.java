package code;

import java.util.ArrayList;

public class Row {
    public final ArrayList<Integer> alpha;
    public final Integer idenifier;
    public final int support;

    public Row(ArrayList<Integer> newIdentifier, int support) {
        this.idenifier = newIdentifier.remove(newIdentifier.size()-1);// unsafe
        this.alpha = newIdentifier;
        this.support = support;
    }

    @Override
    public String toString(){
        return ((alpha.size()==0 ? "" : alpha.toString().substring(1).replace(']', ',') + ' ') + 
        idenifier + " : " + support + "\n");
    }

}
