package fourcats.usecaseinteractor;

import fourcats.entity.API;
import fourcats.interfaceaccess.RepositoryAccess;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GenerateApiTest {
    @Mock
    RepositoryAccess repositoryMock;

    /*@Mock
    Repository repo;*/
   // RepositoryAccess repositoryMock = Mockito.mock(RepositoryAccess.class);

    @InjectMocks
    GenerateApi generator;

    @Before
    public void before(){
    }

    @Test
    public void GenerateAPITestCorrectCreation() {
       // GenerateApi correctGenerator = new GenerateApi(repositoryMock);
        assertNotNull(generator);
    }

    @Test
    public void generateAPI(){
        Map<Integer, API> map = new HashMap<>();
        map.put(1,new API());
        when(repositoryMock.getApiMap()).thenReturn(map);

        generator.generate();
        verify(repositoryMock).writeApi(any(API.class));
    }

}