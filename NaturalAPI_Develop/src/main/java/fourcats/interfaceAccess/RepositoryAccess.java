package fourcats.interfaceAccess;

import fourcats.entity.API;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public interface RepositoryAccess {

    File openFile(String filename);
    String loadPLA(String filename);
    void writeApi(API api) throws IOException;
    void addApi(API api);
    void addApiWithId(int id,API api);
    void deleteApi(int id);
    API getApi(int id);
    Map<Integer,API> getApiMap();
    int getSize();

}
