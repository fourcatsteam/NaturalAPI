package fourcats.interfaceadapters;

import fourcats.port.*;

public class Controller {

    private ApiInputPort apiInputPort;
    private GenerateInputPort generateInputPort;
    private ModifyInputPort modifyInputPort;
    private CreatePlaInputPort createPlaInputPort;
    private ModifyPlaInputPort modifyPlaInputPort;

    public Controller(ApiInputPort apiInputPort,GenerateInputPort generateInputPort,
                      ModifyInputPort modifyInputPort,
                      CreatePlaInputPort createPlaInputPort, ModifyPlaInputPort modifyPlaInputPort){
        this.apiInputPort = apiInputPort;
        this.generateInputPort = generateInputPort;
        this.modifyInputPort = modifyInputPort;
        this.createPlaInputPort = createPlaInputPort;
        this.modifyPlaInputPort = modifyPlaInputPort;
    }

    public void createApiSuggestion(String filenameBal,String filenamePla) {
        apiInputPort.create(filenameBal,filenamePla);
    }

    public void generateApi(String path) throws Exception{
        generateInputPort.generate(path);
    }

    public void modifyApi(int id1,int id2,String filenameBal,String filenamePla){ //modify for Cli: remove and then recreate the api
        modifyInputPort.modify(id1,id2);
        apiInputPort.create(filenameBal,filenamePla);
    }

    public void modifyApiGui(String oldApi,String newApi){
        modifyInputPort.modifyGui(oldApi,newApi);
    }

    public void createPla(String path,String extension,String pla){
        createPlaInputPort.create(path,extension,pla);
    }

    public void loadPlaToModify(String filename) {
        modifyPlaInputPort.loadPlaToModify(filename);
    }

    public void modifyPla(String filename,String text){
        modifyPlaInputPort.modify(filename,text);
    }
}
