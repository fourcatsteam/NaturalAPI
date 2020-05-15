package fourcats.port;

import fourcats.entities.Type;

import java.util.Map;

public interface ShowTypesOutputPort {
    void showTypes(Map<Integer, Type> mTypes);
}
