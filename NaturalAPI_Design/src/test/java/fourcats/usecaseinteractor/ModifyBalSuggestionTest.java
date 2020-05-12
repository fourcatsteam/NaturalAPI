package fourcats.usecaseinteractor;

import fourcats.interfaceaccess.RepositoryAccess;
import fourcats.port.ModifyBalSuggestionOutputPort;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ModifyBalSuggestionTest {

    @Mock
    RepositoryAccess repo;

    @Mock
    ModifyBalSuggestionOutputPort out;

    @InjectMocks
    ModifyBalSuggestion modifyBALSuggestion;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testModifyActionType(){

        modifyBALSuggestion.modifyActionType(1,1, "tipo");
        verify(repo).updateActionType(1,1,"tipo");
        verify(out).showModifiedActionType(repo.readScenarios(),true);

    }

    @Test
    public void testModifyActionTypeById(){

        modifyBALSuggestion.modifyActionTypeById(1,1, 1);
        verify(repo).updateActionTypeById(1,1,1);
        verify(out).showModifiedActionType(repo.readScenarios(),true);

    }

    @Test
    public void testModifyActionName(){

        modifyBALSuggestion.modifyActionName(1,1, "tipo");
        verify(repo).updateActionName(1,1,"tipo");
        verify(out).showModifiedActionName(repo.readScenarios(),true,"tipo");
    }

    @Test
    public void testModifyObjectType(){

        modifyBALSuggestion.modifyObjectType(1,1, 1,"tipo");
        verify(repo).updateObjectType(1,1,1,"tipo");
        verify(out).showModifiedObjectType(repo.readScenarios(),true);
    }

    @Test
    public void testModifyObjectTypeById(){

        modifyBALSuggestion.modifyObjectTypeById(1,1, 1,1);
        verify(repo).updateObjectTypeById(1,1,1,1);
        verify(out).showModifiedObjectType(repo.readScenarios(),true);
    }

    @Test
    public void testModifyObjectName(){

        modifyBALSuggestion.modifyObjectName(1,1, 1,"tipo");
        verify(repo).updateObjectName(1,1,1,"tipo");
        verify(out).showModifiedObjectName(repo.readScenarios(),true,"tipo");
    }


    @Test
    public void testAddObject(){

        modifyBALSuggestion.addObject(1,1, "tipo",1);
        verify(repo).createObject(1,1,"tipo",1);
        verify(out).showAddedObject(repo.readScenarios(),true);
    }

    @Test
    public void testRemoveObject(){

        modifyBALSuggestion.removeObject(1,1, 1);
        verify(repo).deleteObject(1,1,1);
        verify(out).showRemovedObject(repo.readScenarios(),true);
    }



}