package fourcats.frameworks;

import fourcats.entity.Actor;
import fourcats.entity.BAL;
import fourcats.entity.Action;
import fourcats.entity.ObjectParam;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fourcats.interfaceaccess.BalAnalyzer;

import java.io.File;
import java.io.IOException;

public class BalAnalyzerImplementation implements BalAnalyzer {

    private JsonNode jsonNode;

    public void setBalFile(File file){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            jsonNode = objectMapper.readValue(file,JsonNode.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
                StringBuilder sb = new StringBuilder();
                for(int i=0; i<split.length; i++) {
                    if(i>0) {
                        split[i] = split[i].substring(0,1).toUpperCase() + split[i].substring(1);
                    }
                    sb.append(split[i]);
                }
                operation = sb.toString();

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