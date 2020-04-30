package fourcats.usecaseinteractor;

import fourcats.interfaceaccess.RepositoryAccess;
import fourcats.port.LoadBDLInputPort;
import fourcats.port.LoadBDLOutputPort;

public class LoadBdl implements LoadBDLInputPort {
    RepositoryAccess repo;
    LoadBDLOutputPort out;

    public LoadBdl(RepositoryAccess repo, LoadBDLOutputPort output){
        this.repo = repo;
        this.out = output;
    }

    @Override
    public void loadingBdl(String nameBdl) {

    }
}
