import org.apache.commons.compress.compressors.CompressorException;

import java.io.IOException;
import java.util.*;

public class Discover {

    private Frequencies frequencies;
    private Compressor compressor;

    public Discover(String compressionMethod, String databaseFolder, String musicSample, double noiseLevel, String noiseType, boolean generateFreqs) throws IOException {
        this.frequencies = new Frequencies(databaseFolder, musicSample, generateFreqs, noiseLevel, noiseType);
        this.compressor = new Compressor(compressionMethod);
    }

    //This will take our database and compute NCD for each one of the freq
    public List<Music> getMinNCDs(int topSize) throws IOException, CompressorException {
        HashMap<String, Double> results = new HashMap<>();
        List<Music> top = new ArrayList<>();
        // Compute NCD for each entry in Database
        for (Map.Entry<String, String> musicFreq: frequencies.getDatabase().entrySet())
            results.put(musicFreq.getKey(), computeNCD(frequencies.getSampleFreq(), musicFreq.getValue()));
        // Get best NCDs
        results.entrySet().stream().sorted(Map.Entry.comparingByValue()).limit(topSize)
                .forEach(v -> top.add(new Music(v.getKey(), v.getValue())));
        return top;
    }

    //Compute Normalized Compression Distance (NCD) for two given Freqs
    private Double computeNCD(String x, String y) throws IOException {
        long cx = compressor.compression(x);
        long cy = compressor.compression(y);
        long cxy = compressor.compression(x + y);
        return (((double)cxy - (double)Math.min(cx, cy)) / (double) Math.max(cx, cy));
    }

}
