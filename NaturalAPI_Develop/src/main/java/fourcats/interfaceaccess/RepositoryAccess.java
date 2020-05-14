package fourcats.interfaceaccess;

import fourcats.entity.API;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public interface RepositoryAccess {

    File openFile(String filename);
    String loadPLA(String filename) throws IOException;
    void writeApi(String path, API api);
    void addApi(API api);
    void addApiWithId(int id, API api);
    void deleteApi(int id);
    void deleteTests();
    API getApi(int id);
    Map<Integer, API> getApiMap();
    int getSize();
    void addTest(String s);
    String getAllTests();
    void updateApi(String oldApi,String newApi);
    void writePla(String file,String pla);
    void addCoupleBalPla(String bal,String pla);
    boolean isCoupleBalPlaPresent(String bal,String pla);
    boolean isThisApiPresent(API a);
    boolean isThisClassNamePresent(API a);
    boolean isThisTestPresent(String test);

}
