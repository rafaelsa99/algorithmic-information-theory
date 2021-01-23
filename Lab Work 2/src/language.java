
public class language { //main class
    public static void main(String[] args){
        //verify if the program have 4 parameters
        if (args.length != 4) {
            System.out.println("Error! Parameters: orderK alpha referenceText targetText");
            return;
        }
        try {
            DataFile dataFile = new DataFile();
            String referenceFileContent, targetFileContent;
            int orderK = Integer.parseInt(args[0]);
            //Load Text Representative
            long startTimeR = System.nanoTime();
            Model referenceModel = new Model(orderK, Float.parseFloat(args[1]));
            referenceModel.loadAlphabet(args[2]); //load alphabet from both texts
            referenceModel.loadAlphabet(args[3]); //load alphabet from both texts
            referenceFileContent = dataFile.loadFileData(args[2]); //reference file
            dataFile.file(referenceFileContent,orderK,referenceModel);
            long stopTimeR = System.nanoTime();
            targetFileContent = dataFile.loadFileData(args[3]); //target file
            double bits = referenceModel.getBitsToCompressTarget(targetFileContent);
            System.out.println("Reference Model build time: " + ((stopTimeR - startTimeR) / 1000000000.0) + " seconds");
            System.out.println("Entropy of Reference Text: " + referenceModel.getEntropy());
            System.out.println("Number of bits needed to compress target: " + bits + " bits");
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }
}
