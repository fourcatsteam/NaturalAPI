package FourCats.DataStructure;

public class TableRow {
    String word;
    String frequency;
    boolean isKeyValue;

    public TableRow(String w, String f) {
        this.word = w;
        this.frequency = f;
        this.isKeyValue = false;
    }

    public TableRow(String w, String f,boolean keyValue) {
        this.word = w;
        this.frequency = f;
        this.isKeyValue = keyValue;
    }

    public String getWord() {
        return word;
    }

    public String getFrequency() {
        return frequency;
    }

    public boolean isKeyValue(){ return isKeyValue; }
}
