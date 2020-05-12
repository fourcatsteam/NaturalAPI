package Integration.CreateBDL;

import FourCats.Entities.Bdl;
import FourCats.Entities.Document;
import FourCats.Frameworks.FileSystemAccess;
import FourCats.Frameworks.StanfordNlp;
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

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CreateBDLAnalyzeIT {

    AnalyzeDocument analyzer;

    @Mock
    RepositoryAccess repo;

    @Mock
    DataPresenter data = Mockito.mock(DataPresenter.class);

    @InjectMocks
    CreateBdl creatorBDL;
    AnalyzeDocument spy;
    @Before
    public void before(){
        analyzer  = new AnalyzeDocument(new StanfordNlp());
        spy = Mockito.spy(analyzer);
        creatorBDL = new CreateBdl(repo,spy,data);
    }

    @Test
    public void verifyLinkFromDataPresenterAndCreateBDL(){
        Bdl bdl = new Bdl("nome");
        Document doc = new Document("titolo","contenuto");
        when(repo.createBdl(any(String.class))).thenReturn(bdl);
        when(repo.readDocument(any(String.class))).thenReturn(doc);
        List<String> titleList = new LinkedList<>();
        titleList.add("titolo");
        creatorBDL.create("nome",titleList);
        verify(spy).addDocumentToBdl(any(Bdl.class),any(Document.class));
    }
}
