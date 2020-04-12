package fourcats.usecase;

import fourcats.entity.API;
import fourcats.port.GenerateInputPort;
import fourcats.interfaceAccess.RepositoryAccess;

import java.io.IOException;
import java.util.Map;

public class GenerateApi implements GenerateInputPort {

    private RepositoryAccess repositoryAccess;

    public GenerateApi(RepositoryAccess ra){
        repositoryAccess = ra;
    }

    public void generate() throws IOException {
        for(Map.Entry<Integer,API> mApi : repositoryAccess.getApiMap().entrySet()){
            repositoryAccess.writeApi(mApi.getValue());
        }
    }
}
