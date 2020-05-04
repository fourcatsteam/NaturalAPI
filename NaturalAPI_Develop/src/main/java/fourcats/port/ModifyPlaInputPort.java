package fourcats.port;

public interface ModifyPlaInputPort {

    void loadPlaToModify(String filename);
    void modify(String filename,String text);
}
