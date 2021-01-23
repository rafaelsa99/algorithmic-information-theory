
public class fcm { //main class
    public static void main(String[] args){
        //verify if the program have 3 parameters
        if (args.length != 3) {
            System.out.println("Error! Parameters: orderK alpha filePath");
            return;
        }
        try {
            int orderK = Integer.parseInt(args[0]);
            long startTime = System.nanoTime();
            Model model = new Model(orderK, Float.parseFloat(args[1]));
            model.loadAlphabet(args[2]); //load alphabet
            DataFile dataFile = new DataFile();
            String DataString = dataFile.loadFileData(args[2]); //file
            dataFile.file(DataString,orderK,model);
            long stopTime = System.nanoTime();
            System.out.println("Model build time: " + ((stopTime - startTime) / 1000000000.0) + " seconds");
            System.out.println("Entropy: " + model.getEntropy());
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }
}
