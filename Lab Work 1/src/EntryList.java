import java.util.HashMap;

//This represents a row of data in a table, each value in the HashMap represents a column
public class EntryList {
    private final HashMap<Character, Integer> entries; //entries of a character
    private Integer sum; //number of entries

    //create new entry with a character
    public EntryList(Character symbol) {
        this.entries = new HashMap<>();//create entries for a character on a hash map
        this.sum = 0;
        this.addEntry(symbol);
    }

    //get entries of a character
    public Integer getEntry(Character chr){
        return entries.get(chr);
    }

    //Verify if the character is in the Entry List
    public boolean containsChar(Character chr){
        return entries.containsKey(chr);
    }

    //add entries replacing 0 with 1
    public void addEntry(Character symbol){
        entries.merge(symbol,1, Integer::sum);
        sum++;
    }

    // return number of entries
    public Integer getSum() {
        return sum;
    }
}