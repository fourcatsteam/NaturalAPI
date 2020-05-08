package fourcats.interfaceaccess;

import fourcats.entity.API;

import java.io.File;
import java.util.Map;

public interface RepositoryAccess {

    File openFile(String filename);
    String loadPLA(String filename);
    void writeApi(String path,API api);
    void addApi(API api);
    void addApiWithId(int id,API api);
    void deleteApi(int id);
    API getApi(int id);
    Map<Integer,API> getApiMap();
    int getSize();
    void updateApi(String oldApi,String newApi);
    void writePla(String file,String pla);
    void addCoupleBalPla(String bal,String pla);
    boolean isCoupleBalPlaPresent(String bal,String pla);

}
