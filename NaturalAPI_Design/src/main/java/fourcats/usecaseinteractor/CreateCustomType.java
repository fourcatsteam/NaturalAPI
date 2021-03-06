package fourcats.usecaseinteractor;

import fourcats.interfaceaccess.RepositoryAccess;
import fourcats.port.CreateCustomTypeInputPort;
import fourcats.port.CreateCustomTypeOutputPort;

import java.util.Map;

public class CreateCustomType implements CreateCustomTypeInputPort {
    RepositoryAccess repo;
    CreateCustomTypeOutputPort out;

    public CreateCustomType(RepositoryAccess repositoryAccess, CreateCustomTypeOutputPort outputPort){
        this.repo = repositoryAccess;
        this.out = outputPort;
    }

    @Override
    public void createType(String typeName,Map<String, String> mAttributes) {
        try {
            if (!typeName.equals("")) {
                typeName = typeName.trim().replace(' ', '_');
                if (Character.isDigit(typeName.charAt(0))) typeName = "_" + typeName;
            }
            repo.createCustomType(typeName, mAttributes);
            out.showCustomTypeCreationStatus(true);
        }
        catch(Exception e){
            out.showCustomTypeCreationStatus(false);
        }
    }

}
