package FourCats.entity;

public class Document {
    private String title;
    private String author;
    private Integer year;
    private String content;
    private String language;

    public Document(String t, String a, Integer y, String c, String l) {
        title=t;
        author=a;
        year=y;
        content=c;
        language=l;
    }

    public String getTitle() {return title;}

    public String getContent() {return content;}

}

