package fourcats.usecaseinteractor;

import fourcats.frameworks.Repository;
import fourcats.port.ShowTypesOutputPort;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ShowTypesTest {

    ShowTypes showTypes;

    @Mock
    Repository repository;

    @Mock
    ShowTypesOutputPort showTypesOutputPort;

    @Before
    public void CreateShowType() {
        showTypes = new ShowTypes(repository, showTypesOutputPort);
    }

    @Test
    public void ShowTypesShowTypes() {
        showTypes.showTypes();
        verify(showTypesOutputPort, times(1)).showTypes(repository.readTypes());
    }

}