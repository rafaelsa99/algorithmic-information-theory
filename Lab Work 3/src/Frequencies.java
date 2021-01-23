import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

//This class will represent the set of frequencies in our application
public class Frequencies {

    //The data structure below represents the <Music, Frequency>
    private HashMap<String, String> database;
    //Music sample
    private String sample;

    public Frequencies(String databaseFolder, String musicSample, boolean generateFreqs, double noiseLevel, String noiseType) {
        database = new HashMap<>();
        if(generateFreqs)
            generateFreqs(databaseFolder);
        loadDatabase(databaseFolder);
        loadSample(musicSample, noiseLevel, noiseType);
    }

    public HashMap<String, String> getDatabase() {
        return database;
    }

    public String getSampleFreq() {
        return sample;
    }

    public void generateFreqs(String databaseFolder) {
        File folder = new File(databaseFolder);
        File[] listOfMusics = folder.listFiles();
        for (File listOfMusic : listOfMusics) {
            if (listOfMusic.isFile()) {
                generateMaxFreq(listOfMusic.getName(), databaseFolder);
            }
        }
    }

    public void loadDatabase(String databaseFolder) {
        String freqsFolder = databaseFolder + "freqs/";
        File folder = new File(freqsFolder);
        File[] listOfFreqs = folder.listFiles();
        for (File freqFile : listOfFreqs) {
            if (freqFile.isFile()) {
                String freq = loadMaxFreq(freqFile.getName(), freqsFolder);
                if (freq != null) {
                    String[] name = freqFile.getName().split("\\.");
                    database.put(name[0], freq);
                }
            }
        }
    }

    public void genFileNoise(String filePath, double noiseLevel, String noiseType){
        try {
            String OSType = OSValidator.OSType();
            switch (OSType){
                case "Windows":
                    //String command = "../SOX/sox.exe \"" + filePath + "\" ../Samples/noise.wav synth " + noiseType + " vol " + noiseLevel; // To generate bin
                    String command = "SOX/sox.exe \"../" + filePath + "\" ../Samples/noise.wav synth " + noiseType + " vol " + noiseLevel;
                    //Process proc = Runtime.getRuntime().exec(command, null, new File("../SOX/")); // To generate bin
                    Process proc = Runtime.getRuntime().exec(command, null, new File("SOX/"));
                    proc.waitFor();
                    //String command2 = "../SOX/sox.exe -m \"" + filePath + "\" ../Samples/noise.wav ../Samples/noisySample.wav"; // To generate bin
                    String command2 = "SOX/sox.exe -m \"../" + filePath + "\" ../Samples/noise.wav ../Samples/noisySample.wav";
                    //Process proc2 = Runtime.getRuntime().exec(command2, null, new File("../SOX/")); // To generate bin
                    Process proc2 = Runtime.getRuntime().exec(command2, null, new File("SOX/"));
                    proc2.waitFor();
                    break;
                case "Mac":
                    //String commandM = "../SOX-MACOS/sox \"" + filePath + "\" ../Samples/noise.wav synth " + noiseType + " vol " + noiseLevel; // To generate bin
                    String commandM = "SOX-MACOS/sox \"../" + filePath + "\" ../Samples/noise.wav synth " + noiseType + " vol " + noiseLevel;
                    //Process procM = Runtime.getRuntime().exec(commandM); // To generate bin
                    Process procM = Runtime.getRuntime().exec(commandM);
                    procM.waitFor();
                    //String commandM2 = "../SOX-MACOS/sox -m \"" + filePath + "\" ../Samples/noise.wav ../Samples/noisySample.wav"; // To generate bin
                    //Process procM2 = Runtime.getRuntime().exec(commandM2); // To generate bin
                    String commandM2 = "SOX-MACOS/sox -m \"../" + filePath + "\" ../Samples/noise.wav ../Samples/noisySample.wav";
                    Process procM2 = Runtime.getRuntime().exec(commandM2);
                    procM2.waitFor();
                    break;
                case "Unix":
                    ArrayList<String> commandU = new ArrayList<>();
                    commandU.add("/bin/bash");
                    commandU.add("-c");
                    //commandU.add("../SOX-Linux/sox \"" + filePath + "\"" + " ../Samples/noise.wav synth " + noiseType + " vol " + noiseLevel); // To generate bin
                    commandU.add("SOX-Linux/sox \"" + filePath + "\"" + " Samples/noise.wav synth " + noiseType + " vol " + noiseLevel);
                    ArrayList<String> commandU2 = new ArrayList<>();
                    commandU2.add("/bin/bash");
                    commandU2.add("-c");
                    //commandU2.add("../SOX-Linux/sox -m \"" + filePath + "\" ../Samples/noise.wav ../Samples/noisySample.wav"); // To generate bin
                    commandU2.add("SOX-Linux/sox -m \"" + filePath + "\" Samples/noise.wav Samples/noisySample.wav");
                    try {
                        // Generate Noise
                        ProcessBuilder p = new ProcessBuilder(commandU);
                        Process process = p.start();
                        process.waitFor();

                        //ADD noise to sample
                        ProcessBuilder p2 = new ProcessBuilder(commandU2);
                        Process process2 = p2.start();
                        process2.waitFor();

                    }catch (IOException e) { System.out.println(e); }
                    break;
                default:
                    System.out.println("System " + OSType + " not supported.");
            }
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

    public void loadSample(String musicSample, double noiseLevel, String noiseType) {
        //Add noise to sample according to program parameter
        genFileNoise(musicSample, noiseLevel, noiseType);
        //String freq = generateMaxFreq("noisySample.wav", "../Samples/"); // To generate bin
        String freq = generateMaxFreq("noisySample.wav", "Samples/");
        if(freq != null)
            sample = freq;
    }

    public String generateMaxFreq(String filename, String databaseFolder) {
        try {
            String[] name = filename.split("\\.");
            String OSType = OSValidator.OSType();
            switch (OSType){
                case "Windows":
                    //String command = "../GetMaxFreqs/bin/GetMaxFreqs.exe -w \"../" + databaseFolder + "freqs/" + name[0] + ".freqs\" \"../" + databaseFolder + filename + "\""; // To generate bin
                    String command = "GetMaxFreqs/bin/GetMaxFreqs.exe -w \"../../" + databaseFolder + "freqs/" + name[0] + ".freqs\" \"../../" + databaseFolder + filename + "\"";
                    //Create .freqs file
                    //Process proc = Runtime.getRuntime().exec(command, null, new File("../GetMaxFreqs/bin/")); // To generate bin
                    Process proc = Runtime.getRuntime().exec(command, null, new File("GetMaxFreqs/bin/"));
                    proc.waitFor();
                    //Read file
                    break;
                case "Mac":
                case "Unix":
                    //String commandM = "../GetMaxFreqs/bin/GetMaxFreqs -w \"" + databaseFolder + "freqs/" + name[0] + ".freqs\" \"" +  databaseFolder + filename + "\""; // To generate bin
                    String commandM = "GetMaxFreqs/bin/GetMaxFreqs -w \"" + databaseFolder + "freqs/" + name[0] + ".freqs\" \"" +  databaseFolder + filename + "\"";
                    ArrayList<String> generateMax = new ArrayList<>();
                    generateMax.add("/bin/bash");
                    generateMax.add("-c");
                    generateMax.add(commandM);

                    try {
                        // Generate Freq
                        ProcessBuilder pMax = new ProcessBuilder(generateMax);
                        Process processMax = pMax.start();
                        processMax.waitFor();
                    }catch (IOException e)
                    {
                        System.out.println(e);
                    }
                    break;
                //Create .freqs file
                //Read file
                default:
                    System.out.println("System " + OSType + " not supported.");
            }
            return loadMaxFreq(name[0] + ".freqs", databaseFolder + "freqs/");
        } catch (InterruptedException | IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public String loadMaxFreq(String filename, String freqsFolder){
        try{
            StringBuilder freq = new StringBuilder();
            BufferedReader reader = new BufferedReader(new FileReader(freqsFolder + filename));
            String currentLine;
            while ((currentLine = reader.readLine()) != null)
                freq.append(currentLine);
            reader.close();
            return freq.toString();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
