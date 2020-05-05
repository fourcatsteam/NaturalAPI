package fourcats.usecaseinteractor;

import fourcats.entity.API;
import fourcats.port.GenerateInputPort;
import fourcats.interfaceaccess.RepositoryAccess;

import java.util.Map;

public class GenerateApi implements GenerateInputPort {

    private RepositoryAccess repositoryAccess;

    public GenerateApi(RepositoryAccess ra){
        repositoryAccess = ra;
    }

    public void generate() throws Exception {

        if(repositoryAccess.getApiMap().isEmpty()){
            throw new Exception();
        }
        else{
            for(Map.Entry<Integer,API> mApi : repositoryAccess.getApiMap().entrySet()){
                repositoryAccess.writeApi(mApi.getValue());
            }
        }
    }
}
