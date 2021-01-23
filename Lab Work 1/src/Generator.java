public class Generator {
    private Model model;

    public Generator(Model model) {
        this.model = model;
    }

    public String generateText(String sample, Integer maxSize){
        if (sample.length() < model.getOrderK()){
            return "The Sample string is too short";
        }else {
            String result = sample;
            for (int i = sample.length(); i < maxSize; i++){
                try {
                    result += model.getNextCharBasedOnContext(result.substring(result.length()-model.getOrderK(),i));
                }catch (Exception e){
                    System.out.println(e.getMessage());
                }
            }
            return result;
        }
    }
}