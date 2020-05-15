package fourcats.usecaseinteractor;

import fourcats.interfaceaccess.RepositoryAccess;
import fourcats.port.LoadBdlInputPort;
import fourcats.port.LoadBdlOutputPort;

public class LoadBdl implements LoadBdlInputPort {
    RepositoryAccess repo;
    LoadBdlOutputPort out;

    public LoadBdl(RepositoryAccess repo, LoadBdlOutputPort output){
        this.repo = repo;
        this.out = output;
    }

    @Override
    public void loadBdlFiles(String[] pathBdl) {
        try {
            //read bdl from external source and store in dataKeeper
            repo.readAndCreateBdl(pathBdl);
            out.showBDLOutput(repo.readBdl(),true);
        }
        catch (Exception e){
            out.showBDLOutput(repo.readBdl(),false);
        }
    }
}
