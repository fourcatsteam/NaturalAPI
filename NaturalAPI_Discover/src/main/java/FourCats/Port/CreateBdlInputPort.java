package FourCats.Port;

import java.io.IOException;
import java.util.LinkedList;

public interface CreateBdlInputPort {
    void create(String nameBdl,LinkedList<String> titleList) throws IOException;
}
