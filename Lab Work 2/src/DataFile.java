import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class DataFile {

    public void file(String DataString, int orderK, Model model){
        String predecessor; //previous char
        Character character; // actual file

        // iterate data in orderK to add on your model
        for (int i = 0; i < DataString.length() - orderK; i++)
        {
            predecessor= DataString.substring(i, orderK + i); //the previous will be the next char
            character = DataString.charAt(orderK + i); //actual char will be the char at orderk + i
            model.addData(predecessor,character);//function to add data to model
        }
    }

    //Load data on file
    public String loadFileData(String fileName) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        File text = new File(fileName);
        BufferedReader reader = new BufferedReader(new FileReader(text));
        int chr;
        while((chr = reader.read()) != -1) {
            stringBuilder.append((char)chr); //add char at a string
        }
        reader.close();
        return String.valueOf(stringBuilder);
    }

    public void loadFileFromDataset(String fileName, String languageFile, int orderK, float alpha, HashMap<String, Model> languages, SymbolList targetAlphabet) throws IOException {
        BufferedReader textFile;
        BufferedReader langFile;
        textFile = new BufferedReader(new FileReader(fileName));
        langFile = new BufferedReader(new FileReader(languageFile));
        String textLine = textFile.readLine();
        String langLine = langFile.readLine();
        while (textLine != null) {
            if (!languages.containsKey(langLine)){
                Model modelFiles = new Model(orderK, alpha);
                modelFiles.loadAlphabetFromLine(textLine);
                modelFiles.addAlphabet(targetAlphabet);
                file(textLine,orderK,modelFiles);
                languages.put(langLine,modelFiles);
            }else{
                languages.get(langLine).loadAlphabetFromLine(textLine);
                file(textLine,orderK,languages.get(langLine));
            }
            textLine = textFile.readLine();
            langLine = langFile.readLine();
        }
        textFile.close();
        langFile.close();
    }
}
