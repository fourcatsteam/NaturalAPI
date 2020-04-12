package FourCats.Entities;

public class Document {
    private String title;
    private String content;

    public Document(String t,String c) {
        title=t;
        content=c;
    }

    public String getTitle() {return title;}

    public String getContent() {return content;}

    @Override
    public String toString() {
        return "Document{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}

