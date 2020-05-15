package FourCats.Port;

import java.io.IOException;
import java.util.List;

public interface CreateBdlInputPort {
    void create(String nameBdl, List<String> titleList);
}
