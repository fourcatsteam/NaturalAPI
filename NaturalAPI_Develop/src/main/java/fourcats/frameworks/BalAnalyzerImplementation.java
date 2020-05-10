package fourcats.frameworks;

import fourcats.entity.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fourcats.interfaceaccess.BalAnalyzer;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
            JsonNode actions = actor.get("actions");

            for (JsonNode ac : actions) {

                String actionName = ac.get("name").asText();
                JsonNode acType = ac.get("type");
                String actionType = acType.get("name").asText();
                String scenario = ac.get("scenario").asText();
                String step = ac.get("step").asText();

                //camelCase operations
                String[] split = actionName.split("_");
                StringBuilder sb = new StringBuilder();
                for(int i=0; i<split.length; i++) {
                    if(i>0) {
                        split[i] = split[i].substring(0,1).toUpperCase() + split[i].substring(1);
                    }
                    sb.append(split[i]);
                }

                JsonNode attributesAction = acType.get("attributes");

                Map<String,String> mAttributes = new HashMap<>();
                java.util.Iterator it = attributesAction.fieldNames();
                while(it.hasNext()){
                    String attributeName = it.next().toString();
                    String attributeType = attributesAction.get(attributeName).asText();
                    mAttributes.put(attributeName,attributeType);
                }
                Type entityType = new Type(actionType,mAttributes);

                actionName = sb.toString();
                Action entityAction = new Action(actionName,entityType,scenario,step);

                JsonNode parameters = ac.get("objectParams");

                for (JsonNode par : parameters) {
                    JsonNode typePar = par.get("type");
                    JsonNode attributes = typePar.get("attributes");

                    mAttributes = new HashMap<>();
                    it = attributes.fieldNames();
                    while(it.hasNext()){
                        String attributeName = it.next().toString();
                        String attributeType = attributes.get(attributeName).asText();
                        mAttributes.put(attributeName,attributeType);
                    }

                    Type entityTypeOb = new Type(typePar.get("name").asText(),mAttributes);
                    ObjectParam entityObjectParam = new ObjectParam(par.get("name").asText(),entityTypeOb);
                    entityAction.addObjectParam(entityObjectParam);
                }

                entityActor.addAction(entityAction);
            }
            bal.addUserToBAL(entityActor);
        }
        return bal;
    }
}
