package fourcats.interfaceadapters;

import fourcats.port.*;

public class Controller {

    private ApiInputPort apiInputPort;
    private GenerateInputPort generateInputPort;
    private ModifyInputPort modifyInputPort;
    private ModifyGuiInputPort modifyGuiInputPort;
    private CreatePlaInputPort createPlaInputPort;
    private ModifyPlaInputPort modifyPlaInputPort;

    public Controller(ApiInputPort apiInputPort,GenerateInputPort generateInputPort,
                      ModifyInputPort modifyInputPort, ModifyGuiInputPort modifyGuiInputPort,
                      CreatePlaInputPort createPlaInputPort, ModifyPlaInputPort modifyPlaInputPort){
        this.apiInputPort = apiInputPort;
        this.generateInputPort = generateInputPort;
        this.modifyInputPort = modifyInputPort;
        this.modifyGuiInputPort = modifyGuiInputPort;
        this.createPlaInputPort = createPlaInputPort;
        this.modifyPlaInputPort = modifyPlaInputPort;
    }

    public void createApiSuggestion(String filenameBal,String filenamePla) {
        apiInputPort.create(filenameBal,filenamePla);
    }

    public void generateApi(String path) throws Exception{
        generateInputPort.generate(path);
    }

    public void modifyApi(int id,String filenameBal,String filenamePla){ //modify for Cli: remove and then recreate the api
        modifyInputPort.modify(id);
        apiInputPort.create(filenameBal,filenamePla);
    }

    public void modifyApiGui(String oldApi,String newApi){
        modifyGuiInputPort.modifyGui(oldApi,newApi);
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
