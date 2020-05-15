package fourcats.usecaseinteractor;

import fourcats.interfaceaccess.RepositoryAccess;
import fourcats.port.RemoveBdlInputPort;
import fourcats.port.RemoveBdlOutputPort;

public class RemoveBdl implements RemoveBdlInputPort {
    RepositoryAccess repo;
    RemoveBdlOutputPort out;

    public RemoveBdl(RepositoryAccess repo, RemoveBdlOutputPort output){
        this.repo = repo;
        this.out = output;
    }
    @Override
    public void removeLoadedBdl() {
        repo.deleteBdl();
        out.showRemoveBdlStatus();
    }
}
