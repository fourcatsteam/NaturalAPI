package fourcats.usecaseinteractor;

import fourcats.entities.Bdl;
import fourcats.interfaceaccess.RepositoryAccess;
import fourcats.port.LoadBDLInputPort;
import fourcats.port.LoadBDLOutputPort;

import java.io.IOException;

public class LoadBdl implements LoadBDLInputPort {
    RepositoryAccess repo;
    LoadBDLOutputPort out;

    public LoadBdl(RepositoryAccess repo, LoadBDLOutputPort output){
        this.repo = repo;
        this.out = output;
    }

    @Override
    public void loadingBdl(String[] nameBdl) throws IOException {
        Bdl bdl = repo.readBdl(nameBdl);
        if(bdl!=null){
            out.showBDLOutput("Tutto a posto, BDL caricata");
        }
    }
}
