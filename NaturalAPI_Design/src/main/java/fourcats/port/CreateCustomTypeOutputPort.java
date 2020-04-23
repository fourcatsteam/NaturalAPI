package fourcats.port;

import fourcats.entities.Type;

import java.util.Map;

public interface CreateCustomTypeOutputPort {
    void showCustomTypes(Map<Integer, Type> mType);
}
