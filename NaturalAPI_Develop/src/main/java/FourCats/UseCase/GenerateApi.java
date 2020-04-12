package UseCase;

import Entity.API;
import Port.GenerateInputPort;
import InterfaceAccess.RepositoryAccess;

import java.util.Map;

public class GenerateApi implements GenerateInputPort {

    private RepositoryAccess repositoryAccess;

    public GenerateApi(RepositoryAccess ra){
        repositoryAccess = ra;
    }

    public void generate(){
        for(Map.Entry<Integer,API> mApi : repositoryAccess.getApiMap().entrySet()){
            repositoryAccess.writeApi(mApi.getValue());
        }
    }
}
