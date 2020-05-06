package fourcats.port;

import java.io.IOException;

public interface LoadBdlInputPort {
    void loadBdlFiles(String[] nameBdl) throws IOException;
}
