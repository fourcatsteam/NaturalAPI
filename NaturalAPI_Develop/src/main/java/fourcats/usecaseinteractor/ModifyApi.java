package fourcats.usecaseinteractor;

import fourcats.entity.*;
import fourcats.interfaceaccess.BalAnalyzer;
import fourcats.interfaceaccess.RepositoryAccess;
import fourcats.port.ModifyGuiInputPort;
import fourcats.port.ModifyInputPort;
import fourcats.port.ModifyOutputPort;


public class ModifyApi implements ModifyInputPort, ModifyGuiInputPort {

    BalAnalyzer balAnalyzer;
    RepositoryAccess repositoryAccess;
    ModifyOutputPort modifyOutputPort;

    public ModifyApi(BalAnalyzer b,RepositoryAccess r,ModifyOutputPort m){
        balAnalyzer = b;
        repositoryAccess = r;
        modifyOutputPort = m;
    }

    public void modifyGui(String oldApi,String newApi){
        repositoryAccess.updateApi(oldApi,newApi);
    }

    public void modify(int id){
        repositoryAccess.deleteApi(id);
        //modify for cli: only delete api and create the controller creates an api with different bal or pla
    }
}
