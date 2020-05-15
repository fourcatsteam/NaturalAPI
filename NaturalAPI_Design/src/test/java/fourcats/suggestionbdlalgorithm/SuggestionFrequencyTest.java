package fourcats.suggestionbdlalgorithm;

import fourcats.datastructure.WordCounter;
import fourcats.entities.Bdl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SuggestionFrequencyTest {

    @Mock
    Bdl bdl;

    @Mock
    WordCounter wordCounter;

    ArrayList<WordCounter> wordCountersList;

    SuggestionFrequency suggestionFrequency;

    @Before
    public void CreateSuggestionFrequency() {
        suggestionFrequency = new SuggestionFrequency(bdl);
        wordCountersList = new ArrayList<>();
        wordCountersList.add(wordCounter);
    }

    @Test
    public void SuggestionFrequencyFindActionIdActionFounded() {
        when(bdl.getPredicates()).thenReturn(wordCountersList);
        when(wordCounter.getWord()).thenReturn("Action name");
        when(wordCounter.getCount()).thenReturn(1);
        assertEquals(1, suggestionFrequency.findActionInBdl("Action_name"));
    }

    @Test
    public void SuggestionFrequencyFindActionIdActionNotFound() {
        when(bdl.getPredicates()).thenReturn(wordCountersList);
        when(wordCounter.getWord()).thenReturn("Action name");
        assertEquals(0, suggestionFrequency.findActionInBdl("Not such action"));
    }

    @Test
    public void SuggestionFrequencyFindObjectInBDLAndReturnFrequency() {
        when(bdl.getNouns()).thenReturn(wordCountersList);
        when(wordCounter.getWord()).thenReturn("Object name");
        when(wordCounter.getCount()).thenReturn(1);
        assertEquals(1, suggestionFrequency.findObjectInBdl("Object name"));
    }

    @Test
    public void SuggestionFrequencyFindObjectInBDLWthNoFundingObject() {
        when(bdl.getNouns()).thenReturn(wordCountersList);
        when(wordCounter.getWord()).thenReturn("Object name");
        assertEquals(0, suggestionFrequency.findObjectInBdl("No such object"));
    }

}