//package fourcats.usecaseinteractor;
//
//import fourcats.entity.*;
//import fourcats.interfaceaccess.BalAnalyzer;
//import fourcats.interfaceaccess.RepositoryAccess;
//import fourcats.port.ModifyOutputPort;
//import fourcats.view.CLI;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.*;
//import org.mockito.junit.MockitoJUnitRunner;
//
//import static org.junit.Assert.*;
//import static org.mockito.ArgumentMatchers.*;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//import org.mockito.AdditionalMatchers;
//
//import java.io.File;
//import java.util.LinkedList;
//import java.util.List;
//
//@RunWith(MockitoJUnitRunner.class)
//public class ModifyApiTest {
//    @Mock
//    BalAnalyzer analyzerMock;
//    @Mock
//    RepositoryAccess repositoryMock;
//    @Mock
//    ModifyOutputPort outputMock;
//    @InjectMocks
//    ModifyApi modifyApi;
//
//    @Before
//    public void before(){
//        MockitoAnnotations.initMocks(this);
//    }
//
//    @Test
//    public void ModifyAPITestFromWrongInput() {
//
//        try{
//            modifyApi.modify(-1);
//            fail();
//        }
//        catch(Throwable e)
//        {
//            assertTrue(true);
//        }
//    }
//
//    @Test
//    public void correctlyCreatingModifyApi(){
//        assertNotNull(modifyApi);
//    }
//
//    @Test
//    public void testingCorrectlyMethodCallModifyGui(){
//        modifyApi.modifyGui("a","b");
//        verify(repositoryMock).updateApi("a","b");
//    }
//
//    @Test
//    public void testingModifyApi(){
//
//        modifyApi.modify(1);
//        verify(repositoryMock).deleteApi(anyInt());
//
//    }
//}