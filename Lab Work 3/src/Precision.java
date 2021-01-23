import org.apache.commons.compress.compressors.CompressorException;

import java.io.File;
import java.io.IOException;
import java.util.*;

//The principal objective of this program/class is to determine the precision of global asserts in file with various languages
public class Precision {
    public static void main(String[] args) {
        //verify if the program have 5 parameters
        Set<String> compressionMethods = new HashSet<>(Arrays.asList("gzip", "bzip2", "lzma", "deflate", "lz4", "snappy", "xz", "zstd"));
        if ((args.length != 5) || !compressionMethods.contains(args[0])) {
            System.out.println("Parameters: compressionMethod databaseFolder samplesFolder noiseLevel noiseType");
            System.out.println("Compression Methods Available: gzip, bzip2, lzma, deflate, lz4, snappy, xz, zstd");
            return;
        }
        if (Double.parseDouble(args[3]) > 1 || Double.parseDouble(args[3]) < 0) {
            System.out.println("Noise level must be between 0 and 1 (E.g.: 0.5)");
            return;
        }
        if (!args[4].equals("whitenoise") && !args[4].equals("pinknoise") && !args[4].equals("brownnoise")) {
            System.out.println("The noise type must be one of the following: whitenoise, pinknoise or brownnoise");
            return;
        }
        try {
            long startTimeR = System.nanoTime();
            samplePrecision(args[2], args[0], args[1], Double.parseDouble(args[3]), args[4]);
            long stopTimeR = System.nanoTime();
            System.out.println("Execution time: " + ((stopTimeR - startTimeR) / 1000000000.0) + " seconds");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    static String sampleEvaluation(String compressionMethod, String databaseFolder, String musicSample, Double noiseLevel, String noiseType){
        try {
            Discover discover = new Discover(compressionMethod, databaseFolder, musicSample, noiseLevel, noiseType, false);
            List<Music> result = discover.getMinNCDs(1);
            return result.get(0).getMusic();
        } catch (IOException | CompressorException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    static void samplePrecision(String testFolder, String compressionMethod, String databaseFolder, Double noiseLevel, String noiseType) throws IOException {
        File folder = new File(testFolder);
        File[] listOfMusics = folder.listFiles();
        //Reading each line of file using Scanner class
        String eval= "";
        int asserts = 0;
        int totalSamples = 0;
        HashMap<String,String> fails = new HashMap<>();
        for (int i = 0; i < listOfMusics.length; i++) {
            if (listOfMusics[i].isFile()) {
                String test = listOfMusics[i].getName().replace(".wav", "");
                eval = sampleEvaluation(compressionMethod,databaseFolder,listOfMusics[i].getPath(),noiseLevel, noiseType);
                if (test.equals(eval))
                    asserts += 1;
                else {
                    fails.put(test, eval);
                }
                totalSamples += 1;
            }
        }

        System.out.println("Samples with wrong classifications: ");
        for (HashMap.Entry<String, String> entry : fails.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            System.out.println("\tExpected Result: " + key + " -> Obtained Result: " + value);
        }
        System.out.println("\nClassification Accuracy: " + ((double)asserts/(double)totalSamples)*100 + "%");
    }
}
