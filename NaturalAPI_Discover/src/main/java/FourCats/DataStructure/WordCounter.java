package FourCats.DataStructure;

public class WordCounter implements Comparable<WordCounter>{
    private String word;
    private Integer count;
    private boolean isKeyValue;

    public WordCounter(String w, Integer c) {
        word=w;
        count=c;
        isKeyValue = false;
    }

    public WordCounter(String w) {
        this(w,1);
    }

    public void incrementCounter() {
        count++;
    }

    public void decrementCounter(){
        if(count > 0){
            count--;
        }
    }

    public String getWord() {return word;}
    public Integer getCount() {return count;}

    public int compareTo(WordCounter w) {
        if(count<w.getCount()) return -1;
        if(count.equals(w.getCount())) return 0;
        return 1;
    }

    public boolean isKeyValue() {
        return isKeyValue;
    }

    public void setKeyValue(boolean keyValue) {
        isKeyValue = keyValue;
    }


    @Override
    public String toString() {
        return "["+word+","+count+"]";
    }
}
