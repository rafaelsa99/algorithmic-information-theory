public class gen { //main class
    public static void main(String[] args){
        //verify if the program have 5 parameters
        if (args.length != 5) {
            System.out.println("Error! Parameters: orderK alpha filePath StartingText TextLength");
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
            startTime = System.nanoTime();
            Generator generator = new Generator(model);
            System.out.println(generator.generateText(args[3], Integer.parseInt(args[4])));
            stopTime = System.nanoTime();
            System.out.println("Text generation time: " + ((stopTime - startTime) / 1000000000.0) + " seconds");
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }
}
