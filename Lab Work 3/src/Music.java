public class Music {

    String music;
    double ncd;

    public Music(String music, double ncd) {
        this.music = music;
        this.ncd = ncd;
    }

    public String getMusic() {
        return music;
    }

    @Override
    public String toString() {
        return "Music \"" + music + "\" with NCD = " + ncd;
    }
}
