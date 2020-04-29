package fourcats.frameworks;

import fourcats.entity.API;
import fourcats.interfaceaccess.RepositoryAccess;

import java.io.File;
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

    public String loadPLA(String filename) {
        return fileSystem.loadPLA(filename);
    }

    public void writeApi(API api){
        fileSystem.writeApi(api);
    }

    public void addApi(API api){
        dataKeeper.addApi(api);
    }

    public void addApiWithId(int id,API api){
        dataKeeper.addApiWithId(id,api);
    }

    public void deleteApi(int id){
        dataKeeper.deleteApi(id);
    }

    public API getApi(int id){
        return dataKeeper.getApi(id);
    }

    public Map<Integer,API> getApiMap(){
        return dataKeeper.getApiMap();
    }

    public int getSize(){
        return dataKeeper.getSize();
    }

    public void updateApi(String oldApi,String newApi){
        dataKeeper.updateApi(oldApi,newApi);
    }
}
