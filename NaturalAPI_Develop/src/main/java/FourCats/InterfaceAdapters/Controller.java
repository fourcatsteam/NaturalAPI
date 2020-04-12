package InterfaceAdapters;

import Port.ApiInputPort;
import Port.GenerateInputPort;
import Port.ModifyInputPort;
import UseCase.GenerateApi;
import UseCase.ModifyApi;

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

    public void generateApi(){
        generateInputPort.generate();
    }

    public void modifyApi(int id,String filenameBal,String filenamePla){
        modifyInputPort.modify(id,filenameBal,filenamePla);
    }
}
