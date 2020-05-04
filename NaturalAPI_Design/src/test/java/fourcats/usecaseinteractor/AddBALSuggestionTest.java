package fourcats.usecaseinteractor;

import fourcats.interfaceaccess.RepositoryAccess;
import fourcats.port.AddBALSuggestionOutputPort;
import fourcats.port.CreateCustomTypeOutputPort;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class AddBALSuggestionTest {

    @Mock
    RepositoryAccess repo;

    @Mock
    AddBALSuggestionOutputPort out;

    @InjectMocks
    AddBALSuggestion addBALSuggestion;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void addSuggestion() {
        addBALSuggestion.addSuggestion(1,"suggName", "suggType");
        verify(repo).createSuggestion(1,"suggName", "suggType");
        verify(out).showAddedSuggestion(repo.readScenarios(), true);
    }

    @Test
    public void addSuggestionByIdType() {
        addBALSuggestion.addSuggestionByIdType(1,"suggName", 1);
        verify(repo).crateSuggestionByIdType(1,"suggName", 1);
        verify(out).showAddedSuggestion(repo.readScenarios(), true);
    }
}