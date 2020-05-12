package Integration.CreateBDL;

import FourCats.Entities.Bdl;
import FourCats.Entities.Document;
import FourCats.Frameworks.FileSystemAccess;
import FourCats.InterfaceAccess.RepositoryAccess;
import FourCats.InterfaceAdapters.DataPresenter;
import FourCats.InterfaceAdapters.Repository;
import FourCats.UseCaseInteractor.CreateBdl;
import FourCats.UseCaseUtilities.AnalyzeDocument;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CreateBDLRepoAccessIT {

    RepositoryAccess repo;

    @Mock
    AnalyzeDocument analyzer = Mockito.mock(AnalyzeDocument.class);

    @Mock
    DataPresenter data = Mockito.mock(DataPresenter.class);

    @InjectMocks
    CreateBdl creatorBDL;

    RepositoryAccess spyrepo;

    @Before
    public void before(){
        repo = new Repository(new FileSystemAccess());
        spyrepo = Mockito.spy(repo);
        creatorBDL = new CreateBdl(spyrepo,analyzer,data);
    }

    @Test
    public void verifyLinkFromRepoAndCreateBDL(){
        List<String> titleList = new LinkedList<>();
        titleList.add("titolo");
        when(spyrepo.readDocument(any(String.class))).thenReturn(new Document("titolo","contenuto"));
        creatorBDL.create("nome",titleList);
        verify(spyrepo).readDocument(any(String.class));
        verify(spyrepo).updateBdl(any(Bdl.class));
        verify(spyrepo).updateAssociation(any(String.class),any(List.class));
        assertNotNull(spyrepo.createBdl("nome"));
    }
}
