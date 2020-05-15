package fourcats.port;

import fourcats.entity.API;

import java.util.Map;

public interface ApiOutputPort {

    void showOutput(Map<Integer, API> mApi);
    void showMessage(String message);
}
