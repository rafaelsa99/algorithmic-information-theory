import java.io.*;
import java.rmi.NotBoundException;
import java.util.HashMap;

public class Model {
    private final HashMap<String,EntryList> data; //Finite Context Model
    private SymbolList alphabet;
    private int totalSum; //number of contexts that occurred
    private final int orderK;
    private final float alpha;

    public Model(int orderK, float alpha) { //constructor
        this.data = new HashMap<>();
        this.alphabet = new SymbolList();
        if(orderK > 0)
            this.orderK = orderK;
        else
            this.orderK = 3;
        if(alpha >= 0 && alpha <= 1)
            this.alpha = alpha;
        else
            this.alpha = 0;
        this.totalSum = 0;
    }

    public void addData(String predecessor, Character nextChar) {
        EntryList entryList = new EntryList(nextChar); // new entry list
        if (data.containsKey(predecessor)){ //if data contains predecessor we add next char
            data.get(predecessor).addEntry(nextChar);
        }else{
            data.put(predecessor,entryList); //add new char and entry list
        }
        totalSum++;
    }

    //load alphabet to a symbol list
    public void loadAlphabet(String filename) throws IOException {
        File text = new File(filename);
        BufferedReader reader = new BufferedReader(new FileReader(text));
        int chr;
        while((chr = reader.read()) != -1)
            alphabet.addSymbol((char)chr);
        reader.close();
    }

    public double getProbability(String context, Character nextChar){
        double probability = 0;
        if(!data.containsKey(context))
            probability = alpha / (alpha * alphabet.getNumOfSymbols());
        else if(!data.get(context).containsChar(nextChar))
            probability = alpha / (data.get(context).getSum() + alpha * alphabet.getNumOfSymbols());
        else {
            probability = (data.get(context).getEntry(nextChar) + alpha) / (data.get(context).getSum() + alpha * alphabet.getNumOfSymbols());
        }
        return probability;
    }

    public double getContextProbability(String context){
        double probability = 0;
        if(!data.containsKey(context))
            probability = alpha / (totalSum + alpha * getNumberOfAllPossibleContexts());
        else
            probability = (data.get(context).getSum() + alpha) / (totalSum + alpha * getNumberOfAllPossibleContexts());
        return probability;
    }

    public double getContextEntropy(String context){
        double entropy = 0;
        for (Character c:alphabet.getSymbolList())
            if(data.get(context).containsChar(c) || alpha > 0)
                entropy += getProbability(context, c) * Math.log(getProbability(context, c));
        return -entropy;
    }

    //Returns a character for a given context based on the model
    public Character getNextCharBasedOnContext(String context) throws NotBoundException{
        double r = Math.random();
        double cdf = 0.0;
        for (Character character : alphabet.getSymbolList()) {
            cdf += getProbability(context,character);
            if (r <=  cdf) {
                return character;
            }
        }
        throw new NotBoundException();
    }

    //Calculates the probability of a context that never occurred
    public double getInexistentContextEntropy(){
        double entropy = 0;
        if(alpha > 0) {
            double probability = (alpha / (alpha * alphabet.getNumOfSymbols()));
            entropy += alphabet.getNumOfSymbols() * probability * Math.log(probability);
        }
        return -entropy;
    }

    public double getEntropy(){
        double entropy = 0;
        for (String context:data.keySet())
            entropy += getContextProbability(context) * getContextEntropy(context);
        if(alpha > 0){ // Only consider contexts that never occurred if the alpha is greater than zero
            double numInexistentCont = getNumberOfAllPossibleContexts() - data.size(); //Number of contexts that never occurred
            double inexistentContProb = alpha / (totalSum + alpha * getNumberOfAllPossibleContexts()); //Probability of a context that never occurred
            entropy += numInexistentCont * inexistentContProb * getInexistentContextEntropy(); //Multiply P(c)*H(c) by the number of contexts that never occurred
        }
        return entropy;
    }

    public int getOrderK() {
        return orderK;
    }

    public double getNumberOfAllPossibleContexts(){
        return Math.pow(alphabet.getNumOfSymbols(), orderK);
    }
}