import java.util.HashSet;

public class SymbolList {
    private final HashSet<Character> symbolList; //list of symbols of the alphabet
    private Integer numOfSymbols;  //number of symbols of the alphabet

    public SymbolList() { //constructor
        this.symbolList = new HashSet<>();
        numOfSymbols = 0;
    }

    //Add a new symbol to the alphabet
    public void addSymbol(Character symbol){
        if(symbolList.add(symbol)) //Verifies if the symbol was added
            numOfSymbols++;
    }
    //Return the symbols of the alphabet
    public HashSet<Character> getSymbolList() { return symbolList; }

    //Return the number of symbols of the alphabet
    public Integer getNumOfSymbols() { return numOfSymbols; }
}
