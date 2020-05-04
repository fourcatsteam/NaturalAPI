package fourcats.interfaceadapters;

import fourcats.port.*;

public class Controller {

    private ApiInputPort apiInputPort;
    private GenerateInputPort generateInputPort;
    private ModifyInputPort modifyInputPort;
    private ModifyGuiInputPort modifyGuiInputPort;
    private CreatePlaInputPort createPlaInputPort;

    public Controller(ApiInputPort apiInputPort,GenerateInputPort generateInputPort,
                      ModifyInputPort modifyInputPort, ModifyGuiInputPort modifyGuiInputPort,
                      CreatePlaInputPort createPlaInputPort){
        this.apiInputPort = apiInputPort;
        this.generateInputPort = generateInputPort;
        this.modifyInputPort = modifyInputPort;
        this.modifyGuiInputPort = modifyGuiInputPort;
        this.createPlaInputPort = createPlaInputPort;
    }

    public void createApiSuggestion(String filenameBal,String filenamePla) {
        apiInputPort.create(filenameBal,filenamePla);
    }

    public void generateApi() {
        generateInputPort.generate();
    }

    public void modifyApi(int id,String filenameBal,String filenamePla){
        modifyInputPort.modify(id,filenameBal,filenamePla);
    }

    public void modifyApiGui(String oldApi,String newApi){
        modifyGuiInputPort.modifyGui(oldApi,newApi);
    }

    public void createPla(String title,String extension,String pla){
        createPlaInputPort.create(title,extension,pla);
    }
}
