package fourcats.usecaseinteractor;

import fourcats.datastructure.AnalyzedData;
import fourcats.entities.Action;
import fourcats.entities.ObjectParam;
import fourcats.entities.Scenario;
import fourcats.interfaceaccess.RepositoryAccess;
import fourcats.interfaceaccess.TextAnalyzer;
import fourcats.port.GenerateBalSuggestionsInputPort;
import fourcats.port.GenerateBalSuggestionsOutputPort;
import org.apache.lucene.util.SetOnce;

import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.*;

import static java.util.stream.Collectors.toMap;

public class GenerateBalSuggestions implements GenerateBalSuggestionsInputPort {
    RepositoryAccess repo;
    TextAnalyzer textAnalyzer;
    GenerateBalSuggestionsOutputPort out;
    private static final String AS_A = "As a";

    public GenerateBalSuggestions(RepositoryAccess repositoryAccess, TextAnalyzer textAnalyzer, GenerateBalSuggestionsOutputPort outputPort)
    {
        this.repo = repositoryAccess;
        this.textAnalyzer = textAnalyzer;
        this.out = outputPort;
    }

    public void generateSuggestions(List<String> lFeatureFilePaths, boolean isForNewBal) {
        if (isForNewBal){
            repo.deleteScenarios(); //make sure that there are no old suggestions by deleting scenarios from repo
        }

        List<Scenario> lScenarios = new ArrayList<>();

        //read each feature file from repository
        for (String featurePath: lFeatureFilePaths) {
            String feature = "";
            try {
                feature = repo.read(featurePath);
            } catch (FileNotFoundException e) {
                out.showErrorFileLoad(false);
                return;
            }
            if (feature != null) {

                lScenarios = generateScenario(feature);
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


    protected List<Scenario> generateScenario(String feature){

        String actorName =extractActorName(feature);

        List<Scenario> scenarioList = new ArrayList<>();

        String[] arrScenarios = feature.split("Scenario:"); //split all scenarios to different strings
        for(String scenarioString : arrScenarios){
            scenarioString = scenarioString.trim();
            List<Action> actionList = generateAction(scenarioString);
            Map<Integer,Action> actionMap = actionList.stream()
                                            .collect(toMap(actionList::indexOf,action->action));

            String scenarioName = extractScenarioName(scenarioString);


            Scenario scenario = new Scenario(scenarioName,actionMap,scenarioString,actorName,feature);

            scenarioList.add(scenario);
        }

        return scenarioList;
    }

    protected List<Action> generateAction(String scenario)  {
        List<Action> lGeneratedActions = new ArrayList<>();
        String scenarioContent = extractScenarioContent(scenario);

        // extract steps from the scenario and analyze them
        List<String> lSteps = extractScenarioSteps(scenarioContent);
        for (String step : lSteps) {
            AnalyzedData analyzedData = textAnalyzer.parseDocumentContent(step);

            List<String> predicates = analyzedData.getDependenciesList()
                    .stream()
                    .filter(d->(d.getRelation().equals("dobj")))
                    .map(d-> d.getGov()+" "+d.getDep())
                    .collect(Collectors.toList());

            //add each suggestion to the actions list (lGeneratedActions) with a default parameter (the name in the action)
            for (String suggestedAction : predicates){
                //format the suggestion by adding "_" between words (ex. withdraw cash --> withdraw_cash)
                String formattedSuggestion = suggestedAction.replace(" ", "_");


                Action suggestion = new Action(formattedSuggestion, "void",extractScenarioName(scenario),step);
                suggestion.addObjectParam(generateObject(suggestion.getName()));
                lGeneratedActions.add(suggestion);
            }
        }

        return lGeneratedActions;

    }

    protected ObjectParam generateObject(String actionName){
        //generate default objectParameter (the first parameter in the Action)
        String[] splittedActionName = actionName.split("_");
        return new ObjectParam(splittedActionName[1],"string"); //object type default at string
    }

    protected String extractScenarioName(String scenario){
        //Pre-condition: All the scenario text after

        int indexScenarioEnd = scenario.contains(AS_A) ? scenario.indexOf(AS_A) : scenario.indexOf("Given");

        String scenarionName = scenario.substring(0,indexScenarioEnd).trim();

        return scenarionName;

    }

    protected String extractActorName(String feature){
        //extract actor name by picking the text between "As a" and the first '\n' found after it in the feature
        //if there isn't the keyword "As a" return "All"
        int indexActorStart = -1;
        int indexActorEnd = 0;
        if (feature.contains(AS_A)) {
            indexActorStart = feature.indexOf(AS_A)+5;
            indexActorEnd = feature.indexOf('\n',indexActorStart);
            if (indexActorStart <= indexActorEnd)
                return feature.substring(indexActorStart,indexActorEnd).trim();
        }
        return "All";

    }

    protected String extractScenarioContent(String scenario){
        //extract all text from the keyword "Given" in the scenario
        int indexGiven = 0;
        if (scenario.indexOf("Given")!=-1){
            indexGiven = scenario.indexOf("Given");
        }
        return scenario.substring(indexGiven);
    }

    protected List<String> extractScenarioSteps(String scenarioContent){
        List<String> lSteps = new ArrayList<>();
        //split whenever one of the keyword is found
        String[] steps = scenarioContent.split("Given|When|Then|And");
        String trimmedStep = "";
        for (String step : steps){
            trimmedStep = step.trim();
            if (!trimmedStep.equals("")) {
                lSteps.add(trimmedStep);
            }
        }
        return lSteps;
    }



}
