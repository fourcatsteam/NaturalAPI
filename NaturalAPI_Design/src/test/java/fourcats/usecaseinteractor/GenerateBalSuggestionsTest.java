package fourcats.usecaseinteractor;

import fourcats.datastructure.AnalyzedData;
import fourcats.entities.Action;
import fourcats.entities.Scenario;
import fourcats.interfaceaccess.RepositoryAccess;
import fourcats.interfaceaccess.TextAnalyzer;
import fourcats.port.GenerateBalSuggestionsOutputPort;
import org.apache.lucene.util.SetOnce;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;


public class GenerateBalSuggestionsTest {

    RepositoryAccess repositoryMock ;

    TextAnalyzer analyzerMock ;

    GenerateBalSuggestionsOutputPort outputMock ;

    GenerateBalSuggestions generateBALSuggestions;

    @Before
    public void setup(){
         repositoryMock = mock(RepositoryAccess.class);

         analyzerMock = mock(TextAnalyzer.class);

         outputMock = mock(GenerateBalSuggestionsOutputPort.class);

         generateBALSuggestions =
                new GenerateBalSuggestions(repositoryMock, analyzerMock, outputMock);
    }

    @Test
    public void testRepoDeleteScenarioWithNewBALOption(){
        List<String> listString = new ArrayList<>();
        generateBALSuggestions.generateSuggestions(listString,true);
        verify(repositoryMock,times(1) ).deleteScenarios();
    }

    @Test
    public void testEmptyPathOutInvocation() throws FileNotFoundException {
        List<String> listString = Arrays.asList("String");
        when(repositoryMock.read("String")).thenThrow(FileNotFoundException.class);
        generateBALSuggestions.generateSuggestions(listString, true);
        verify(outputMock).showErrorFileLoad(false);
    }

    @Test
    public void testExtractScenarioName(){
        String scenario = "" ;
        String scenarioName = "";

        scenario = "This is the name\nGiven some text" ;
        scenarioName = generateBALSuggestions.extractScenarioName(scenario);
        assertEquals("This is the name",scenarioName);

        scenario = "This is another name\nAs a Actor" ;
        scenarioName = generateBALSuggestions.extractScenarioName(scenario);
        assertEquals("This is another name",scenarioName);

    }

    @Test
    public void testExtractActorName(){
        String feature;
        String actorName;

        feature = "As a Actor\nI do things";
        actorName = generateBALSuggestions.extractActorName(feature);
        assertEquals("Actor",actorName);

        feature = "Randome text\nI do things";
        actorName = generateBALSuggestions.extractActorName(feature);
        assertEquals("All",actorName);
    }

    @Test
    public void testExtractScenarioSteps(){
        String scenario;
        String scenarioContent;

        scenario = "SomeName\nGiven tuttoilresto";
        scenarioContent = generateBALSuggestions.extractScenarioSteps(scenario);
        assertEquals("Given tuttoilresto",scenarioContent);

        scenario = "Given tuttoilresto";
        scenarioContent = generateBALSuggestions.extractScenarioSteps(scenario);
        assertEquals("Given tuttoilresto",scenarioContent);

    }

    @Test
    public void testSplitScenarioSteps(){
        String inputString;
        List<String> outputList;

        inputString = "Given A"+"\n"+"When B"+"\n"+"Then C"+"\n"+"And D";
        outputList = generateBALSuggestions.splitScenarioSteps(inputString);
        assertEquals(Arrays.asList("Given A", "When B", "Then C","And D"), outputList);

        inputString = "Given A"+"\n"+"When B"+"\n"+"Then C";
        outputList = generateBALSuggestions.splitScenarioSteps(inputString);
        assertEquals(Arrays.asList("Given A", "When B", "Then C"), outputList);

        inputString =
        "Given the account balance is \"$100\"" + "\n"
        +"And the machine contains enough money" + "\n"
        +"And we introduce a \"AMEX\" card" + "\n"
        +"And the card is valid" + "\n"
        +"When the Account Holder requests \"$50\"" + "\n"
        +"Then the ATM should dispense \"$50\"" + "\n"
        +"And the account balance should be \"$50\"" + "\n"
        +"And the card should be returned";

        outputList = generateBALSuggestions.splitScenarioSteps(inputString);

        List<String> expectedOutput = Arrays.asList(
            "Given the account balance is \"$100\"",
            "And the machine contains enough money",
            "And we introduce a \"AMEX\" card",
            "And the card is valid",
            "When the Account Holder requests \"$50\"",
            "Then the ATM should dispense \"$50\"" ,
            "And the account balance should be \"$50\"",
            "And the card should be returned"
        );

        assertEquals(expectedOutput, outputList);


    }

    @Test
    public void testGenerateAction(){
        String inputString;
        inputString =  "nome dello scenario" +"\n"
                       +"Given no character with name \"Arya\" exists in the currente project" +"\n"
                       +"When I try to create a character with name \"Arya\""+"\n"
                       +"Then character with name \"Arya\" is created"+"\n"
                       +"And I can lookup the character with name \"Arya\" in the current project";

        AnalyzedData analyzedDataMock = mock(AnalyzedData.class);

        when(analyzerMock.parseDocumentContent(anyString())).thenReturn(analyzedDataMock);
        when(analyzedDataMock.getPredicates())
                .thenReturn(Arrays.asList())
                .thenReturn(Arrays.asList("create character"))
                .thenReturn(Arrays.asList())
                .thenReturn(Arrays.asList("lookup character"));

        List<Action> actionList = generateBALSuggestions.generateAction(inputString);
        assertEquals(2, actionList.size());
    }

    @Test
    public void testGenerateScenario(){
        String inputString;
        inputString = "Feature " +"\n"
                     +"Scenario: nome_scenario_1" +"\n"
                     +"Given text1" + "\n"
                     +"When  text1" + "\n"
                     +"Then  text1" + "\n"
                     +"Scenario: nome_scenario_2" +"\n"
                     +"Given text2" + "\n"
                     +"When  text2" + "\n"
                     +"Then  text2" + "\n";


        AnalyzedData analyzedDataMock = mock(AnalyzedData.class);

        when(analyzerMock.parseDocumentContent(anyString())).thenReturn(analyzedDataMock);
        when(analyzedDataMock.getPredicates())
                .thenReturn(Arrays.asList("do things"))
                .thenReturn(Arrays.asList("do things"))
                .thenReturn(Arrays.asList("do things"))
                .thenReturn(Arrays.asList("do things"))
                .thenReturn(Arrays.asList("do things"))
                .thenReturn(Arrays.asList("do things"));

        List<Scenario> scenarioList = generateBALSuggestions.generateScenario(inputString,"featureName");
        assertEquals(2,scenarioList.size());
    }

    @Test
    public void testGenerateSuggestions() throws FileNotFoundException {
        String inputString;
        inputString = "Feature " +"\n"
                +"Scenario: nome_scenario_1" +"\n"
                +"Given text1" + "\n"
                +"When  text1" + "\n"
                +"Then  text1" + "\n";

        List<String> filePath = Arrays.asList("FilePath");
        when(repositoryMock.read(anyString())).thenReturn(inputString);
        AnalyzedData analyzedDataMock = mock(AnalyzedData.class);

        when(analyzerMock.parseDocumentContent(anyString())).thenReturn(analyzedDataMock);
        when(analyzedDataMock.getPredicates())
                .thenReturn(Arrays.asList("do things"))
                .thenReturn(Arrays.asList("do things"))
                .thenReturn(Arrays.asList("do things"));


        generateBALSuggestions.generateSuggestions(filePath,true);
        verify(repositoryMock,times(1)).createScenario(any(Scenario.class));

    }

    /*@Test(expected = SetOnce.AlreadySetException.class)
    public void testGenerateSuggestionsExpection() throws FileNotFoundException {
        String inputString;
        inputString = "Feature " +"\n"
                +"Scenario: nome_scenario_1" +"\n"
                +"Given text1" + "\n"
                +"When  text1" + "\n"
                +"Then  text1" + "\n"
                +"Scenario: nome_scenario_1" +"\n"
                +"Given text1" + "\n"
                +"When  text1" + "\n"
                +"Then  text1" + "\n";;

        List<String> filePath = Arrays.asList("FilePath");
        when(repositoryMock.read(anyString())).thenReturn(inputString);
        AnalyzedData analyzedDataMock = mock(AnalyzedData.class);

        when(analyzerMock.parseDocumentContent(anyString())).thenReturn(analyzedDataMock);
        when(analyzedDataMock.getPredicates())
                .thenReturn(Arrays.asList("do things"))
                .thenReturn(Arrays.asList("do things"))
                .thenReturn(Arrays.asList("do things"))
                .thenReturn(Arrays.asList("do things"))
                .thenReturn(Arrays.asList("do things"))
                .thenReturn(Arrays.asList("do things"));;


        generateBALSuggestions.generateSuggestions(filePath,true);
        verify(repositoryMock,times(1)).createScenario(any(Scenario.class));

    }*/



}