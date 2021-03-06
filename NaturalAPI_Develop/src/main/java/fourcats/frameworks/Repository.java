package fourcats.frameworks;

import fourcats.entity.API;
import fourcats.interfaceaccess.RepositoryAccess;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class Repository implements RepositoryAccess {

    private DataKeeper dataKeeper;
    private FileSystem fileSystem;

    public Repository(DataKeeper datakeeper,FileSystem fileSystem){
        this.dataKeeper = datakeeper;
        this.fileSystem = fileSystem;
    }

    public File openFile(String filename) {
        return fileSystem.openFile(filename);
    }

    public String loadPLA(String filename) throws IOException {
        return fileSystem.loadPLA(filename);
    }

    public void writeApi(String path, API api){
        fileSystem.writeApi(path,api);
    }

    public void addApi(API api){
        dataKeeper.addApi(api);
    }

    public void addApiWithId(int id, API api){
        dataKeeper.addApiWithId(id,api);
    }

    public void deleteApi(int id){
        dataKeeper.deleteApi(id);
    }

    public void deleteTests() {
        dataKeeper.deleteTests();
    }

    public API getApi(int id){
        return dataKeeper.getApi(id);
    }

    public Map<Integer, API> getApiMap(){
        return dataKeeper.getApiMap();
    }

    public int getSize(){
        return dataKeeper.getSize();
    }

    public void addTest(String s) {
        dataKeeper.addTest(s);
    }

    public String getAllTests() {
        return dataKeeper.getAllTests();
    }

    public void updateApi(String oldApi,String newApi){
        dataKeeper.updateApi(oldApi,newApi);
    }

    public void writePla(String file,String pla){
        fileSystem.writePla(file,pla);
    }

    public void addCoupleBalPla(String bal,String pla){
        dataKeeper.addCoupleBalPla(bal,pla);
    }

    public boolean isCoupleBalPlaPresent(String bal,String pla){
        return dataKeeper.isCoupleBalPlaPresent(bal,pla);
    }

    public boolean isThisApiPresent(API a){
        return dataKeeper.isThisApiPresent(a);
    }

    public boolean isThisClassNamePresent(API a){
        return dataKeeper.isThisClassNamePresent(a);
    }

    public boolean isThisTestPresent(String test) {
        return dataKeeper.isThisTestPresent(test);
    }
}
