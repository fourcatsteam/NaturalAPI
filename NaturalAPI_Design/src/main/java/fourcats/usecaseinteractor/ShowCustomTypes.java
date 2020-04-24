package fourcats.usecaseinteractor;

import fourcats.interfaceaccess.RepositoryAccess;
import fourcats.port.ShowCustomTypesInputPort;
import fourcats.port.ShowCustomTypesOutputPort;

public class ShowCustomTypes implements ShowCustomTypesInputPort {
    RepositoryAccess repo;
    ShowCustomTypesOutputPort out;

    public ShowCustomTypes(RepositoryAccess repositoryAccess, ShowCustomTypesOutputPort outputPort){
        this.repo = repositoryAccess;
        this.out = outputPort;
    }

    @Override
    public void showTypes() {
        out.showCustomTypes(repo.readTypes());
    }
}
