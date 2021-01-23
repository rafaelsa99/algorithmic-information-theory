import java.io.*;
import java.util.*;

public class LanguageRecognition {
    private HashMap <String, Model> languages; //associate model to a language
    private final String target;
    private final int order;
    private SymbolList targetAlphabet = new SymbolList();

    public LanguageRecognition(String tg, int order)
    {
        this.target=tg;
        this.order = order;
        this.loadAlphabet();
        this.languages=new HashMap<>();
    }

    public HashMap<String,Double> getTargetLanguage() {
        double bits = 0, aux = 0;
        String language = "";
        boolean isFirst = true;

        for (String lang: languages.keySet())
        {
            aux = languages.get(lang).getBitsToCompressTarget(target);
            if (isFirst) {
                bits = aux;
                language=lang;
                isFirst = false;
            }
            else if (aux < bits)
            {
                bits=aux;
                language=lang;
            }
        }
        HashMap<String,Double> targetLanguage = new HashMap<>();
        targetLanguage.put(language, bits);
        return targetLanguage;
    }

    public ArrayList<Double> getCompressionBitsForSymbols(String referenceModel)
    {
        ArrayList <Double> numBits = new ArrayList<>();
        String context;
        char chr;
        double prob;

        for (int i = 0; i<target.length()-order; i++)
        {
            context = target.substring(i,order+i);
            chr = target.charAt(order+i);
            prob = languages.get(referenceModel).getBitsToCompressSymbol(context,chr);
            numBits.add(prob);
        }
        return numBits;
    }

    public ArrayList<Segment> getSegmentationLanguages(int minSegmentSize, int lowPassFilterSmoothing) throws IOException {
        String language = "", sequenceLanguage = "";
        double minValue = 0;
        boolean isFirst = true;
        ArrayList<Double> bps;
        int startSequence = 0, endSequence;
        HashMap<String, ArrayList<Double>> bitsByChar = new HashMap<>();
        ArrayList<Segment> segments = new ArrayList<>();
        for(String lang:languages.keySet()) {
            bps = getCompressionBitsForSymbols(lang);
            //Generate data to graphics
            if(isFirst)
                writeBpsToFile(bps, "dataForGraphics/beforeLowPass.txt"); // Data to graphic before Low Pass Filter
            bps = lowPassFilter(bps, lowPassFilterSmoothing);
            if(isFirst)
                writeBpsToFile(bps, "dataForGraphics/afterLowPass.txt"); // Data to graphic after Low Pass Filter
            isFirst = false;
            bitsByChar.put(lang, bps);
        }
        for (int i = 0; i < target.length() - order; i++) {
            isFirst = true;
            for (Map.Entry<String, ArrayList<Double>> entry: bitsByChar.entrySet()) { //Checks which language offers the lowest compression value for the character
                if(isFirst) {
                    minValue = entry.getValue().get(i);
                    language = entry.getKey();
                    isFirst = false;
                }
                else if(entry.getValue().get(i) < minValue){
                    minValue = entry.getValue().get(i);
                    language = entry.getKey();
                }
            }
            if(i == 0) 
                sequenceLanguage = language;
            else{
                if(!language.equals(sequenceLanguage) && (getSizeOfNextSequence(i, language, bitsByChar) >= minSegmentSize)){ //Checks if a possible new sequence has been detected and if the new sequence will have the minimum size required
                    if(((i - 1) - startSequence) >= minSegmentSize) { //Checks if the sequence that ended has the minimum size
                        endSequence = i - 1;
                        segments.add(new Segment(sequenceLanguage, startSequence, endSequence));
                        startSequence = i;
                    }
                    sequenceLanguage = language; //Language of the new sequence
                }
            }
        }
        endSequence = target.length(); //Last Sequence
        segments.add(new Segment(sequenceLanguage, startSequence, endSequence));
        return segments;
    }

    public void writeBpsToFile(ArrayList<Double> bps, String filename) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
        for (Double bp : bps) {
            writer.write(bp.toString());
            writer.newLine();
        }
        writer.flush();
        writer.close();
    }

    public int getSizeOfNextSequence(int startingIndex, String language, HashMap<String, ArrayList<Double>> bitsByChar){
        String sequenceLanguage = "";
        double minValue = 0;
        boolean isFirst = true;
        int endSequence = target.length() - order - 1;
        for (int i = startingIndex; i < target.length() - order; i++) {
            isFirst = true;
            for (Map.Entry<String, ArrayList<Double>> entry: bitsByChar.entrySet()) {
                if(isFirst) {
                    minValue = entry.getValue().get(i);
                    sequenceLanguage = entry.getKey();
                    isFirst = false;
                }
                else if(entry.getValue().get(i) < minValue){
                    minValue = entry.getValue().get(i);
                    sequenceLanguage = entry.getKey();
                }
            }
            if(!sequenceLanguage.equals(language)){
                endSequence = i - 1;
                break;
            }
        }
        return endSequence - startingIndex;
    }

    public void addModels(String language, String filename, int orderK, float alpha) throws IOException {
        DataFile dataFile = new DataFile();
        String fileContent = dataFile.loadFileData(filename);
        if (!languages.containsKey(language)){
            Model modelFiles = new Model(orderK, alpha);
            modelFiles.loadAlphabet(filename);
            modelFiles.addAlphabet(targetAlphabet);
            dataFile.file(fileContent,orderK,modelFiles);
            languages.put(language,modelFiles);
        }else{
            languages.get(language).loadAlphabet(filename);
            dataFile.file(fileContent,orderK,languages.get(language));
        }
    }

    public void readFile(String file, int orderK, float alpha) throws IOException {
        File text = new File(file);
        Scanner scnr = new Scanner(text);
        //Reading each line of file using Scanner class
        while(scnr.hasNextLine()){
            String line = scnr.nextLine();
            String[] value = line.split(",");
            addModels(value[0], value[1], orderK,alpha);
        }
    }

    public void loadDataSet(String fileName, String langFile, int orderK, float alpha) throws IOException {
        DataFile dataFile = new DataFile();
        dataFile.loadFileFromDataset(fileName, langFile, orderK, alpha, languages, targetAlphabet);
    }

    public double log2(double x)
    {
        return (Math.log(x) / Math.log(2));
    }

    public ArrayList<Double> lowPassFilter(ArrayList<Double> values, int smoothing){
        double value = values.get(0);
        for (int i=1; i<values.size(); i++){
            double currentValue = values.get(i);
            value += (currentValue - value) / smoothing;
            values.set(i, value);
        }
        return values;
    }

    //load alphabet from reference text and target text to a symbol list
    public void loadAlphabet(){
        for (int chr = 0; chr < target.length(); chr++)
            targetAlphabet.addSymbol(target.charAt(chr));
    }

}
