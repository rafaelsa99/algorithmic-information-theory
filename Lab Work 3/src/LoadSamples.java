import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.*;
import java.util.ArrayList;

public class LoadSamples {
    public static void main(String[] args) {
        if(args.length != 3)
        {
            System.out.println("Parameters: seconds databaseFolder sampleFolder");
            return;
        }
        generateSamples(args[0], args[1], args[2]);
    }

    private static void generateSamples(String seconds, String databaseFolder, String sampleFolder)
    {
        File folder = new File(databaseFolder);
        File[] listOfMusics = folder.listFiles();
        String OSType = OSValidator.OSType();
        for (int i = 0; i < listOfMusics.length; i++) {
            if (listOfMusics[i].isFile()) {
                try {
                    File file = listOfMusics[i];
                    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
                    AudioFormat format = audioInputStream.getFormat();
                    long frames = audioInputStream.getFrameLength();
                    double durationInSeconds = (frames+0.0) / format.getFrameRate();
                    int hour = (int)durationInSeconds / 3600;
                    int minutes = (int)(durationInSeconds % 3600) / 60;
                    int sec = (int)durationInSeconds % 60;
                    String timeStart = hour + ":" + minutes/2 + ":" + sec;
                    switch (OSType){
                        case "Windows":
                            //String commandw = "../SOX/sox.exe \"" + databaseFolder + listOfMusics[i].getName() + "\" \"" + sampleFolder + listOfMusics[i].getName() + "\" trim \"" + timeStart + "\" \"" + seconds + "\""; // To generate bin
                            //Process procw = Runtime.getRuntime().exec(commandw, null, new File("../SOX/")); // To generate bin
                            String commandw = "SOX/sox.exe \"../" + databaseFolder + listOfMusics[i].getName() + "\" \"../" + sampleFolder + listOfMusics[i].getName() + "\" trim \"" + timeStart + "\" \"" + seconds + "\"";
                            Process procw = Runtime.getRuntime().exec(commandw, null, new File("SOX/"));
                            procw.waitFor();
                            break;
                        case "Mac":
                            //String commandm = "../SOX-MACOS/sox \"" + databaseFolder + listOfMusics[i].getName() + "\" \"" + sampleFolder + listOfMusics[i].getName() + "\" trim \"" + timeStart + "\" \"" + seconds + "\""; // To generate bin
                            //Process procm = Runtime.getRuntime().exec(commandm); // To generate bin
                            String commandm = "SOX-MACOS/sox \"../" + databaseFolder + listOfMusics[i].getName() + "\" \"../" + sampleFolder + listOfMusics[i].getName() + "\" trim \"" + timeStart + "\" \"" + seconds + "\"";
                            Process procm = Runtime.getRuntime().exec(commandm);
                            procm.waitFor();
                            break;
                        case "Unix":
                            //String commandU = "../SOX-Linux/sox \"" + databaseFolder + listOfMusics[i].getName() + "\" \"" + sampleFolder + listOfMusics[i].getName() + "\" trim \"" + timeStart + "\" \"" + seconds + "\""; // To generate bin
                            String commandU = "SOX-Linux/sox \"../" + databaseFolder + listOfMusics[i].getName() + "\" \"../" + sampleFolder + listOfMusics[i].getName() + "\" trim \"" + timeStart + "\" \"" + seconds + "\"";
                            ArrayList<String> commandLoad = new ArrayList<>();
                            commandLoad.add("/bin/bash");
                            commandLoad.add("-c");
                            commandLoad.add(commandU);

                            try {
                                ProcessBuilder p = new ProcessBuilder(commandLoad);
                                Process process = p.start();
                                process.waitFor();
                            }catch (IOException e) { System.out.println(e); }
                            break;
                        default:
                            System.out.println("System " + OSType + " not supported.");
                    }

                } catch (InterruptedException | IOException | UnsupportedAudioFileException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}