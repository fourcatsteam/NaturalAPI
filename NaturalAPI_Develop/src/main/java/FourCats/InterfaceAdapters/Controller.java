package fourcats.interfaceadapters;

import fourcats.port.ApiInputPort;
import fourcats.port.GenerateInputPort;
import fourcats.port.ModifyInputPort;

import java.io.IOException;


public class Controller {

    private ApiInputPort apiInputPort;
    private GenerateInputPort generateInputPort;
    private ModifyInputPort modifyInputPort;

    public Controller(ApiInputPort apiInputPort,GenerateInputPort generateInputPort,ModifyInputPort modifyInputPort){
        this.apiInputPort = apiInputPort;
        this.generateInputPort = generateInputPort;
        this.modifyInputPort = modifyInputPort;
    }

    public void createApiSuggestion(String filenameBal,String filenamePla) {
        apiInputPort.create(filenameBal,filenamePla);
    }

    public void generateApi() throws IOException {
        generateInputPort.generate();
    }

    public void modifyApi(int id,String filenameBal,String filenamePla){
        modifyInputPort.modify(id,filenameBal,filenamePla);
    }
}
