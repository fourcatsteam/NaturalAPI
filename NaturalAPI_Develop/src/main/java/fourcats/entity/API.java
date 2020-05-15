package fourcats.entity;

public class API {

    String filename;
    String text;

    public API(){
        filename = "";
        text = "";
    }

    public String getFilename() {
        return filename;
    }

    public String getText() {
        return text;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public void setText(String text) {
        this.text = text;
    }
}
