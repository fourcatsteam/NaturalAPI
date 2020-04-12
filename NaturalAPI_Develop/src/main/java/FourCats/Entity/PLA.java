package Entity;

public class PLA {

    String extension;
    String text;

    public PLA(String t){
        String split[] = t.split("\n",2);
        extension = split[0];
        text = split[1];
    }

    public String getText() {
        return text;
    }

    public String getExtension() {
        return extension;
    }

    public void setText(String text) {
        this.text = text;
    }

}
