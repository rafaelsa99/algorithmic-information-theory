
import org.apache.commons.compress.compressors.CompressorException;

import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        //Verify if the program has the correct parameters
        Set<String> compressionMethods = new HashSet<>(Arrays.asList("gzip", "bzip2", "lzma", "deflate", "lz4", "snappy", "xz", "zstd"));
        if ((args.length != 6 && args.length != 7 || (args.length == 7 && !args[6].equals("-g"))) || !compressionMethods.contains(args[0])) {
            System.out.println("Parameters: compressionMethod databaseFolder musicSample noiseLevel noiseType topSize [To generate freqs: -g]");
            System.out.println("Compression Methods Available: gzip, bzip2, lzma, deflate, lz4, snappy, xz, zstd");
            return;
        }
        try {
            boolean generateFreqs = false;
            if (args.length == 7)
                generateFreqs = true;
            long startTimeR = System.nanoTime();
            if (Double.parseDouble(args[3]) > 1 || Double.parseDouble(args[3]) < 0) {
                System.out.println("Noise level must be between 0 and 1 (E.g.: 0.5)");
                return;
            }
            if (!args[4].equals("whitenoise") && !args[4].equals("pinknoise") && !args[4].equals("brownnoise")) {
                System.out.println("The noise type must be one of the following: whitenoise, pinknoise or brownnoise");
                return;
            }
            Discover discover = new Discover(args[0], args[1], args[2], Double.parseDouble(args[3]), args[4], generateFreqs);
            List<Music> result = discover.getMinNCDs(Integer.parseInt(args[5]));
            long stopTimeR = System.nanoTime();
            for (int i = (result.size() - 1); i >= 0; i--) {
                System.out.println("Top " + (i + 1) + ": " + result.get(i).toString());
            }
            System.out.println("Execution time: " + ((stopTimeR - startTimeR) / 1000000000.0) + " seconds");

        } catch (IOException | CompressorException e) {
            System.out.println(e.getMessage());
        }
    }

}
