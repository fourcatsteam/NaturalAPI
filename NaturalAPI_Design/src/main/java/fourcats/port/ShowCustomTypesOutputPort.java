package fourcats.port;

import fourcats.entities.Type;

import java.util.Map;

public interface ShowCustomTypesOutputPort {
    void showCustomTypes(Map<Integer, Type> mTypes);
}
