package fourcats.frameworks;

import fourcats.entity.Actor;
import fourcats.entity.BAL;
import fourcats.entity.Action;
import fourcats.entity.ObjectParam;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fourcats.interfaceAccess.BalAnalyzer;

import java.io.File;
import java.io.IOException;

public class BalAnalyzerImplementation implements BalAnalyzer {

    JsonNode jsonNode;

    public BalAnalyzerImplementation(){

    }

    public BalAnalyzerImplementation(BalAnalyzer b){
        this.jsonNode = b.getJsonNode();
    }

    public BalAnalyzerImplementation(File file){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            jsonNode = objectMapper.readValue(file,JsonNode.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setBalFile(File file){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            jsonNode = objectMapper.readValue(file,JsonNode.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BalAnalyzer get(String s){
        return new BalAnalyzerImplementation(this.get(s));
    }

    public JsonNode getJsonNode() {
        return jsonNode;
    }

    public BAL getBAL(){

        BAL bal = new BAL();

        JsonNode actors = jsonNode.get("actors");
        for(JsonNode actor : actors) {
            String actorName = actor.get("name").asText();
            Actor entityActor = new Actor(actorName);
            JsonNode operations = actor.get("operations");

            for (JsonNode op : operations) {

                String type = op.get("type").asText();
                String operation = op.get("name").asText();

                //camelCase operations
                String[] split = operation.split("_");
                operation = "";
                for(int i=0; i<split.length; i++) {
                    if(i>0) {
                        split[i] = split[i].substring(0,1).toUpperCase() + split[i].substring(1);
                    }
                    operation += split[i];
                }

                JsonNode par = op.get("parameters");

                Action entityAction = new Action(operation,type);
                for (JsonNode parameter : par) {
                    String typePar = parameter.get("type").asText();
                    String name = parameter.get("name").asText();
                    ObjectParam entityObjectParam = new ObjectParam(name,typePar);
                    entityAction.addObjectParam(entityObjectParam);
                }
                entityActor.addAction(entityAction);
            }
            bal.addUserToBAL(entityActor);
        }
        return bal;
    }
}
