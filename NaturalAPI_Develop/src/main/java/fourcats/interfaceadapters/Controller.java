package fourcats.interfaceadapters;

import fourcats.port.ApiInputPort;
import fourcats.port.GenerateInputPort;
import fourcats.port.ModifyGuiInputPort;
import fourcats.port.ModifyInputPort;

import java.io.IOException;


public class Controller {

    private ApiInputPort apiInputPort;
    private GenerateInputPort generateInputPort;
    private ModifyInputPort modifyInputPort;
    private ModifyGuiInputPort modifyGuiInputPort;

    public Controller(ApiInputPort apiInputPort,GenerateInputPort generateInputPort,
                      ModifyInputPort modifyInputPort, ModifyGuiInputPort modifyGuiInputPort){
        this.apiInputPort = apiInputPort;
        this.generateInputPort = generateInputPort;
        this.modifyInputPort = modifyInputPort;
        this.modifyGuiInputPort = modifyGuiInputPort;
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
}
