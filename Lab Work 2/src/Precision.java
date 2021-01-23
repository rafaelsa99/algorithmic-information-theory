import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

//The principal objective of this program/class is to determine the precision of global asserts in file with various languages
public class Precision {
    public static void main(String[] args) {
        //verify if the program have 5 parameters
        if (args.length != 5){
            System.out.println("Error!\nParameters: orderK alpha targetTextsFile textFile languageFile");
            return;
        }
        try {
            languagePrecision(args[2],Integer.parseInt(args[0]),Float.parseFloat(args[1]),args[3],args[4]);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    static String languageEvaluation(int orderK, float alpha, String targetFile, String fileName, String langFile){
        try {
            String targetFileLanguage;
            long startTimeR = System.nanoTime();
            DataFile dataFile = new DataFile();
            targetFileLanguage = dataFile.loadFileData(targetFile);
            LanguageRecognition languageRecognition = new LanguageRecognition(targetFileLanguage, orderK);
            languageRecognition.loadDataSet(fileName, langFile, orderK, alpha);
            long stopTimeR = System.nanoTime();
            System.out.println("Reference Model build time: " + ((stopTimeR - startTimeR) / 1000000000.0) + " seconds");
            HashMap<String,Double> targetLanguage = languageRecognition.getTargetLanguage();
            for (String lang:targetLanguage.keySet())
                return lang;
                //System.out.println("Target file: " + targetFile + " is in " + lang + " language, with compression of " + targetLanguage.get(lang) + " bits");
        }catch(Exception e){
            System.out.println("Exception: " + e.getMessage());
        }
        return null;
    }

    static void languagePrecision(String testFile, int orderK, float alpha, String fileName, String langFile) throws IOException {
        File text = new File(testFile);
        Scanner scnr = new Scanner(text);
        //Reading each line of file using Scanner class
        String eval= "";
        int asserts = 0;
        int totalLangs = 0;
        HashMap<String,String> fails = new HashMap<>();
        while(scnr.hasNextLine()){
            String line = scnr.nextLine();
            String[] value = line.split(",");
            eval = languageEvaluation(orderK,alpha,value[1],fileName,langFile);
            if (value[0].equals(eval))
                asserts += 1;
            else{
                fails.put(value[0],eval);
            }
            totalLangs +=1;
        }
        System.out.println("Classification Accuracy: " + ((double)asserts/(double)totalLangs)*100 + "%");
        System.out.println("Languages with wrong classifications: ");
        for (HashMap.Entry<String, String> entry : fails.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            System.out.println("\tExpected language: " + key + " -> Obtained language: " + value);
        }
    }
}
