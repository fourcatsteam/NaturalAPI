package fourcats.usecaseinteractor;

import fourcats.entity.API;
import fourcats.port.GenerateInputPort;
import fourcats.interfaceaccess.RepositoryAccess;
import fourcats.port.GenerateOutputPort;

import java.util.Map;

public class GenerateApi implements GenerateInputPort {

    private RepositoryAccess repositoryAccess;
    public GenerateOutputPort generateOutputPort;

    public GenerateApi(RepositoryAccess ra,GenerateOutputPort generateOutputPort){
        repositoryAccess = ra;
        this.generateOutputPort = generateOutputPort;
    }

    public void generate(String path) throws Exception {

        if(repositoryAccess.getApiMap().isEmpty()){
            throw new Exception();
        }
        else{
            for(Map.Entry<Integer,API> mApi : repositoryAccess.getApiMap().entrySet()){
                repositoryAccess.writeApi(path,mApi.getValue());
                generateOutputPort.showGenerationMessage("APIs generated!");
            }
        }
    }
}
