public class Segment {

    private String language;
    private int start;
    private int end;

    public Segment(String language, int start, int end) {
        this.language = language;
        this.start = start;
        this.end = end;
    }

    @Override
    public String toString() {
        return "Language: " + language + " ; Start: " + start + " ; End: " + end;
    }
}
