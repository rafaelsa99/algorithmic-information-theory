import java.io.*;
import java.util.HashMap;

public class Model {
    private final HashMap<String,EntryList> data; //Finite Context Model
    private final SymbolList alphabet;
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

    //load alphabet from reference text and target text to a symbol list
    public void loadAlphabet(String fileName) throws IOException {
        File referenceText = new File(fileName);
        BufferedReader referenceReader = new BufferedReader(new FileReader(referenceText));
        int chr;
        while((chr = referenceReader.read()) != -1)
            alphabet.addSymbol((char)chr);
        referenceReader.close();
    }

    //load alphabet from reference text and target text to a symbol list
    public void loadAlphabetFromLine(String line) {
        for (int i = 0; i < line.length(); i++)
            alphabet.addSymbol(line.charAt(i));
    }

    public void addAlphabet(SymbolList _alphabet){
        for ( Character character : _alphabet.getSymbolList()){
            alphabet.addSymbol(character);
        }
    }


    public double getProbability(String context, Character nextChar){
        double probability;
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
        double probability;
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
                entropy += getProbability(context, c) * log2(getProbability(context, c));
        return -entropy;
    }

    //Calculates the probability of a context that never occurred
    public double getInexistentContextEntropy(){
        double entropy = 0;
        if(alpha > 0) {
            double probability = (alpha / (alpha * alphabet.getNumOfSymbols()));
            entropy += alphabet.getNumOfSymbols() * probability * log2(probability);
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

    public double getNumberOfAllPossibleContexts(){
        return Math.pow(alphabet.getNumOfSymbols(), orderK);
    }

    public double getBitsToCompressTarget(String targetText){
        String context;
        char chr;
        double prob, total = 0;
        // iterate data in orderK to calculate the total number of bits to compress the target
        for (int i = 0; i < targetText.length() - orderK; i++)
        {
            context= targetText.substring(i, orderK + i);
            chr = targetText.charAt(orderK + i);
            prob = getProbability(context, chr);
            if(prob > 0)
                total += (-log2(prob));
        }
        return total;
    }

    public double getBitsToCompressSymbol(String context, Character chr){
        double prob = getProbability(context, chr);
        if(prob > 0)
            return (-log2(prob));
        return prob;
    }

    public double log2(double x)
    {
        return (Math.log(x) / Math.log(2));
    }
}