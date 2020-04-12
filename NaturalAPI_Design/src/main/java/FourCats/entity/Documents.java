package FourCats.entity;

import java.util.LinkedList;

public class Documents {
    private LinkedList<Document> list;

    public Documents(){
        list = new LinkedList<Document>();
    }

    public void add(Document d){
        list.add(d);
    }

    public String print(){
        String res="";
        for(Document d: list){
            res = res + d.getContent();
            res = res + "\n";
        }
        return res;
    }

    public LinkedList<Document> getList(){
        return list;
    }

}
