package fourcats.usecaseinteractor;

import fourcats.interfaceaccess.RepositoryAccess;
import fourcats.port.CreateCustomTypeOutputPort;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class CreateCustomTypeTest {

    @Mock
    RepositoryAccess repo;

    @Mock
    CreateCustomTypeOutputPort out;

    @InjectMocks
    CreateCustomType createCustomType;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void testCreateType() {

        Map<String, String> mAttributes = mock(HashMap.class);

        createCustomType.createType("tipo", mAttributes);
        repo.createCustomType("tipo", mAttributes);
        out.showCustomTypeCreationStatus(true);
    }
}