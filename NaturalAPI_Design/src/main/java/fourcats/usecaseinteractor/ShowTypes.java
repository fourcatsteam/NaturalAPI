package fourcats.usecaseinteractor;

import fourcats.interfaceaccess.RepositoryAccess;
import fourcats.port.ShowTypesInputPort;
import fourcats.port.ShowTypesOutputPort;

public class ShowTypes implements ShowTypesInputPort {
    RepositoryAccess repo;
    ShowTypesOutputPort out;

    public ShowTypes(RepositoryAccess repositoryAccess, ShowTypesOutputPort outputPort){
        this.repo = repositoryAccess;
        this.out = outputPort;
    }

    @Override
    public void showTypes() {
        out.showTypes(repo.readTypes());
    }
}
