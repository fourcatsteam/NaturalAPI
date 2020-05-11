package fourcats.usecaseinteractor;

import fourcats.datastructure.AnalyzedData;
import fourcats.entities.Action;
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
    private static final String GIVEN = "Given";

    public GenerateBalSuggestions(RepositoryAccess repositoryAccess, TextAnalyzer textAnalyzer, GenerateBalSuggestionsOutputPort outputPort) {
        this.repo = repositoryAccess;
        this.textAnalyzer = textAnalyzer;
        this.out = outputPort;
    }

    public void generateSuggestions(List<String> lFeatureFilePaths, boolean isForNewBal) {
        if (isForNewBal) {
            repo.deleteScenarios(); //make sure that there are no old suggestions by deleting scenarios from repo
        }

        List<Scenario> scenarioList = new ArrayList<>();

        //read each feature file from repository
        for (String featurePath : lFeatureFilePaths) {
            String feature = "";
            try {
                feature = repo.read(featurePath);
            } catch (FileNotFoundException e) {
                out.showErrorFileLoad(false);
                return;
            }
            if (feature != null) {
                List<Scenario> x = generateScenario(feature,featurePath);
                scenarioList.addAll(x);
            }
        }

        for (Scenario scenario : scenarioList) {
            //try to add each scenario (which contains the suggestions) to the repository
            try {
                repo.createScenario(scenario);
            } catch (SetOnce.AlreadySetException e) {
                out.showErrorFileLoad(true);
            }
        }
        out.showSuggestionsForScenario(repo.readScenarios());
    }


    protected List<Scenario> generateScenario(String feature,String featureName) {

        String actorName = extractActorName(feature);

        List<Scenario> scenarioList = new ArrayList<>();

        List<String> scenarioStringList = new ArrayList<>(Arrays.asList(feature.split("Scenario:")));

        scenarioStringList.remove(0);

        for (String scenarioString : scenarioStringList) {

            scenarioString = scenarioString.trim();
            String scenarioName = extractScenarioName(scenarioString);
            List<Action> actionList = generateAction(scenarioString);
            Map<Integer, Action> actionMap = actionList.stream()
                    .collect(toMap(actionList::indexOf, action -> action));

            Scenario scenario = new Scenario(scenarioName, actionMap, scenarioString, actorName, featureName);

            scenarioList.add(scenario);
        }

        return scenarioList;
    }

    protected List<Action> generateAction(String scenario) {
        List<Action> lGeneratedActions = new ArrayList<>();

        String scenarioName = extractScenarioName(scenario);
        String scenarioSteps = extractScenarioSteps(scenario);

        // extract steps from the scenario and analyze them
        List<String> lSteps = splitScenarioSteps(scenarioSteps);
        List<String> lStandardizedAndSteps = convertAndKeywords(lSteps);
        for (String step : lSteps) {
            AnalyzedData analyzedData = textAnalyzer.parseDocumentContent(step);

            List<String> predicates = analyzedData.getPredicates();

            //add each suggestion to the actions list (lGeneratedActions) with a default parameter (the name in the action)
            for (String predicate : predicates) {
                String actionName = predicate.replace(" ", "_");
                String objParName = predicate.split(" ")[1];

                Action action = new Action(actionName, "void", scenarioName, lStandardizedAndSteps.get(lSteps.indexOf(step)));
                action.addObjectParam(objParName, "string");

                lGeneratedActions.add(action);
            }
        }

        return lGeneratedActions;

    }

    protected String extractScenarioName(String scenario) {
        //Pre-condition: Scenario name followed by keyword As a or Given
        int indexScenarioEnd = scenario.contains(AS_A) ? scenario.indexOf(AS_A) : scenario.indexOf(GIVEN);

        return scenario.substring(0, indexScenarioEnd).trim();
    }

    protected String extractActorName(String feature) {
        //extract actor name by picking the text between "As a" and the first '\n' found after it in the feature
        //if there isn't the keyword "As a" return "All"
        if (!feature.contains(AS_A))
            return "All";

        int indexActorStart = feature.indexOf(AS_A) + 5;
        int indexActorEnd = feature.indexOf('\n', indexActorStart);

        return feature.substring(indexActorStart, indexActorEnd).trim();
    }

    protected String extractScenarioSteps(String scenario) {
        //remove the name of the scenario from the string
        int indexGiven = scenario.contains(GIVEN) ? scenario.indexOf(GIVEN) : 0;

        return scenario.substring(indexGiven);
    }

    protected List<String> splitScenarioSteps(String scenarioContent) {

        return Stream.of(scenarioContent.split("\n"))
                .map(String::trim)
                .collect(Collectors.toList());
    }

    protected List<String> convertAndKeywords(List<String> scenarioSteps){
        List<String> updatedSteps = new ArrayList<>();
        String lastStep = "";
        for (String step : scenarioSteps) {
            if (!lastStep.equals("") && step.startsWith("And")) {
                step = step.replaceFirst("And", lastStep);
            }
            else {
                lastStep = step.split(" ")[0];
            }
            updatedSteps.add(step);
        }
        return updatedSteps;
    }
}
