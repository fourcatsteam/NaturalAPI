package fourcats.port;

import java.io.IOException;

public interface ApiInputPort {

    void create(String filenameBal, String filenamePla) throws IOException;
}
