package fourcats.port;

import java.util.Map;

public interface CreateCustomTypeInputPort {
    void createType(String typeName,Map<String, String> mAttributes);
}
