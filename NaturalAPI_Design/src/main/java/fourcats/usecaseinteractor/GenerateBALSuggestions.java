package fourcats.usecaseinteractor;

import fourcats.datastructure.AnalyzedData;
import fourcats.entities.Action;
import fourcats.entities.ObjectParam;
import fourcats.entities.Scenario;
import fourcats.interfaceaccess.RepositoryAccess;
import fourcats.interfaceaccess.TextAnalyzer;
import fourcats.port.GenerateBALSuggestionsInputPort;
import fourcats.port.GenerateBALSuggestionsOutputPort;

import java.io.FileNotFoundException;
import java.util.*;

public class GenerateBALSuggestions implements GenerateBALSuggestionsInputPort {
    RepositoryAccess repo;
    TextAnalyzer textAnalyzer;
    GenerateBALSuggestionsOutputPort out;
    private static final String AS_A = "As a";

    public GenerateBALSuggestions(RepositoryAccess repositoryAccess, TextAnalyzer textAnalyzer, GenerateBALSuggestionsOutputPort outputPort)
    {
        this.repo = repositoryAccess;
        this.textAnalyzer = textAnalyzer;
        this.out = outputPort;
    }

    public void generateSuggestions(String featureFileName) {
        Map<Integer,Action> mActions;
        //BlackList blackList = new BlackList();
        List<Scenario> lScenarios = new ArrayList<>();
        List<Action> lGeneratedActions;
        int id = 0;
        String feature = "";
        //read feature file from repository
        try {
            feature = repo.read(featureFileName);
        }
        catch(FileNotFoundException e){
            out.showErrorFileLoad();
            return;
        }
        if(feature!=null) {
            String[] arrScenarios = feature.split("Scenario:"); //split all scenarios to different strings
            for (String currentScenario : Arrays.asList(arrScenarios).subList(1, arrScenarios.length)) {
                lGeneratedActions = generateAction(currentScenario);
                mActions = new HashMap<>();
                for (Action action : lGeneratedActions) {
                    mActions.put(id, action);
                    id++;
                }

                lScenarios.add(new Scenario(extractScenarioName(currentScenario), mActions, currentScenario, extractActorName(feature))); //add Scenario with relative suggestions
            }
            for (Scenario scenario : lScenarios) {
                repo.createScenario(scenario); //add each scenario (which contains the suggestions) to the repository
            }
            out.showSuggestionsForScenario(repo.readScenarios());
        }

    }

    private List<Action> generateAction(String scenario)  {
        List<Action> lGeneratedAction = new ArrayList<>();
        // analyze the content of the retrieved file
        AnalyzedData analyzedData = textAnalyzer.parseDocumentContent(scenario);
        //add each suggestion to the suggestions list with a default parameter (the name in the action)
        //format the suggestion by adding "_" between words
        //ex. withdraw cash --> withdraw_cash
        String formattedSuggestion="";
        Action suggestion = null;
        for (String suggestedAction : analyzedData.getParseList()){
            formattedSuggestion = suggestedAction.replace(" ", "_");
            suggestion = new Action((formattedSuggestion), "void");
            suggestion.addObjectParam(generateObject(suggestion.getName()));
            lGeneratedAction.add(suggestion);
        }
        return lGeneratedAction;

    }

    //generate default objectParameter (the first parameter in the Action)
    private ObjectParam generateObject(String actionName){
        String[] splittedActionName = actionName.split("_");
        return new ObjectParam(splittedActionName[1],"string"); //object type default at string
    }

    private String extractScenarioName(String feature){
//extract scenario name by picking the text between first row and "Given:" in the feature file
        int indexFeatureStart = 1;
        int indexFeatureEnd = -1;
        if (feature.indexOf(AS_A)!=-1)
            indexFeatureEnd = feature.indexOf(AS_A);
        else
            indexFeatureEnd = feature.indexOf("Given");
        return feature.substring(indexFeatureStart,indexFeatureEnd).trim();

    }

    private String extractActorName(String feature){
        //extract actor name by picking the text between keywords "As a" and "Scenario" in the feature file
        //if there isn't the keyword "As as" return "All"
        int indexActorStart = -1;
        int indexActorEnd = 0;
        if (feature.indexOf(AS_A)!=-1) {
            indexActorStart = feature.indexOf(AS_A)+5;
            indexActorEnd = feature.indexOf("Scenario");
            if (indexActorStart <= indexActorEnd)
                return feature.substring(indexActorStart,indexActorEnd).trim();
        }
        return "All";

    }



}
