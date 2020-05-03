package fourcats.usecaseinteractor;

import fourcats.datastructure.AnalyzedData;
import fourcats.entities.Action;
import fourcats.entities.ObjectParam;
import fourcats.entities.Scenario;
import fourcats.interfaceaccess.RepositoryAccess;
import fourcats.interfaceaccess.TextAnalyzer;
import fourcats.port.GenerateBALSuggestionsInputPort;
import fourcats.port.GenerateBALSuggestionsOutputPort;
import org.apache.lucene.util.SetOnce;

import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.*;

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

    public void generateSuggestions(List<String> lFeatureFilePaths, boolean isForNewBal) {
        if (isForNewBal){
            repo.deleteScenarios(); //make sure that there are no old suggestions by deleting scenarios from repo
        }
        Map<Integer,Action> mActions;
        //BlackList blackList = new BlackList();
        List<Scenario> lScenarios = new ArrayList<>();
        List<Action> lGeneratedActions;
        int id = 0;
        String feature = "";
        //read each feature file from repository
        for (String featurePath: lFeatureFilePaths) {
            try {
                feature = repo.read(featurePath);
            } catch (FileNotFoundException e) {
                out.showErrorFileLoad(false);
                return;
            }
            if (feature != null) {
                String[] arrScenarios = feature.split("Scenario:"); //split all scenarios to different strings
                for (String currentScenario : Arrays.asList(arrScenarios).subList(1, arrScenarios.length)) {
                    lGeneratedActions = generateAction(currentScenario);
                    mActions = new HashMap<>();
                    for (Action action : lGeneratedActions) {
                        mActions.put(id, action);
                        id++;
                    }
                    lScenarios.add(new Scenario(extractScenarioName(currentScenario), mActions, currentScenario, extractActorName(feature),featurePath)); //add Scenario with relative suggestions
                }
            }
        }
        boolean isScenarioDuplicated = false;
        for (Scenario scenario : lScenarios) {
            try {
                repo.createScenario(scenario); //add each scenario (which contains the suggestions) to the repository
            }
            catch (SetOnce.AlreadySetException e){
                isScenarioDuplicated = true;
            }
        }
        if(isScenarioDuplicated){
            out.showErrorFileLoad(true);
        }

        out.showSuggestionsForScenario(repo.readScenarios());
    }


    private List<Action> generateAction(String scenario)  {
        List<Action> lGeneratedActions = new ArrayList<>();
        String scenarioContent = extractScenarioContent(scenario);
        // analyze the content of the scenario
        AnalyzedData analyzedData = textAnalyzer.parseDocumentContent(scenarioContent);

        String formattedSuggestion="";
        Action suggestion = null;

        List<String> predicates = analyzedData.getDependenciesList()
                .stream()
                .filter(d->(d.getRelation().equals("dobj")))
                .map(d-> d.getGov()+" "+d.getDep())
                .collect(Collectors.toList());

        //add each suggestion to the actions list (lGeneratedActions) with a default parameter (the name in the action)
        for (String suggestedAction : predicates){
            //format the suggestion by adding "_" between words (ex. withdraw cash --> withdraw_cash)
            formattedSuggestion = suggestedAction.replace(" ", "_");
            suggestion = new Action((formattedSuggestion), "void");
            suggestion.addObjectParam(generateObject(suggestion.getName()));
            lGeneratedActions.add(suggestion);
        }
        return lGeneratedActions;

    }


    private ObjectParam generateObject(String actionName){
        //generate default objectParameter (the first parameter in the Action)
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

    private String extractScenarioContent(String scenario){
        //extract all text after the keyword "Given" in the scenario
        int indexGiven = 0;
        if (scenario.indexOf("Given")!=-1){
            indexGiven = scenario.indexOf("Given");
        }
        return scenario.substring(indexGiven);
    }



}
