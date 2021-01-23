import java.util.ArrayList;
import java.util.HashMap;

public class LanguageRecognitionMain {
    public static void main(String[] args) {
        //verify if the program have the correct parameters
        if (args.length < 5 || (args[2].toLowerCase().equals("--dataset") && args.length < 7)){
            System.out.println("Error!\nParameters: orderK alpha LanguagesFiles targetText [fulltext OR segments] (Optional for segments: lowPassFilterSmoothing minSegmentSize)");
            System.out.println("OR");
            System.out.println("Parameters: orderK alpha --dataset targetText textFile languageFile [fullText OR segments] (Optional for segments: lowPassFilterSmoothing minSegmentSize)");
            return;
        }
        try {
            String recognitionType;
            int smoothing = 40, minSegmentSize = 5;
            if(args[2].toLowerCase().equals("--dataset")) {
                recognitionType = args[6].toLowerCase();
                if(args.length >= 8 && Integer.parseInt(args[7]) >= 0)
                    smoothing = Integer.parseInt(args[7]);
                if(args.length >= 9 && Integer.parseInt(args[8]) >= 0)
                    minSegmentSize = Integer.parseInt(args[8]);
            }else {
                recognitionType = args[4].toLowerCase();
                if(args.length >= 6 && Integer.parseInt(args[5]) >= 0)
                    smoothing = Integer.parseInt(args[5]);
                if(args.length >= 7 && Integer.parseInt(args[6]) >= 0)
                    minSegmentSize = Integer.parseInt(args[6]);
            }
            if(!recognitionType.equals("fulltext") && !recognitionType.equals("segments")){
                System.out.println("Type of recognition invalid!\nParameters: orderK alpha LanguagesFiles targetText [fulltext | segments] (Optional for segments: lowPassFilterSmoothing minSegmentSize)");
                System.out.println("OR");
                System.out.println("Parameters: orderK alpha --dataset targetText textFile languageFile [fullText | segments] (Optional for segments: lowPassFilterSmoothing minSegmentSize)");
                return;
            }
            int orderK = Integer.parseInt(args[0]);
            float alpha = Float.parseFloat(args[1]);
            String targetFile = args[3];
            String targetFileLanguage;
            long startTimeR = System.nanoTime();
            DataFile dataFile = new DataFile();
            targetFileLanguage = dataFile.loadFileData(targetFile);
            LanguageRecognition languageRecognition = new LanguageRecognition(targetFileLanguage, orderK);
            if (!args[2].toLowerCase().equals("--dataset")) {
                String loadFileLanguages = args[2];
                languageRecognition.readFile(loadFileLanguages, orderK, alpha);
            }else{
                languageRecognition.loadDataSet(args[4], args[5], orderK, alpha);
            }
            long stopTimeR = System.nanoTime();
            System.out.println("Reference Model build time: " + ((stopTimeR - startTimeR) / 1000000000.0) + " seconds");
            if(recognitionType.equals("fulltext")){
                HashMap<String,Double> targetLanguage = languageRecognition.getTargetLanguage();
                for (String lang:targetLanguage.keySet())
                    System.out.println("Target file: " + targetFile + " is in " + lang + " language, with compression of " + targetLanguage.get(lang) + " bits");
            } else {
                ArrayList<Segment> segments = languageRecognition.getSegmentationLanguages(minSegmentSize, smoothing);
                for (Segment segment : segments)
                    System.out.println(segment.toString());
            }
        }catch(Exception e){
                System.out.println("Exception: " + e.getMessage());
            }
        }
}

