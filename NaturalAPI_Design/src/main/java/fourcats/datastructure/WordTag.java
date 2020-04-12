package fourcats.datastructure;

public class WordTag {
    private String value;
    private String tag;
    private String lemma;

    public WordTag(String w, String t, String l) {
        value=w;
        tag=t;
        lemma=l;
    }

    public String getValue() {return value;}
    public String getTag() {return tag;}
    public String getLemma() {return lemma;}

    public String toString(){
        return "["+value+","+tag+","+lemma+"]";
    }
}
