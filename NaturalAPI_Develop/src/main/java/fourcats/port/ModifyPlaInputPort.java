package fourcats.port;

import java.io.IOException;

public interface ModifyPlaInputPort {

    void loadPlaToModify(String filename);
    void modify(String filename,String text) throws IOException;
}
