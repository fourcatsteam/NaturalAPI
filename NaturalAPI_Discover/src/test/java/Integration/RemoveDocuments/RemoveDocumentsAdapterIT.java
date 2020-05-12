package Integration.AddDocuments;

import FourCats.Entities.Bdl;
import FourCats.Entities.Document;
import FourCats.InterfaceAccess.RepositoryAccess;
import FourCats.InterfaceAdapters.DataPresenter;
import FourCats.UseCaseInteractor.AddDocuments;
import FourCats.UseCaseInteractor.CreateBdl;
import FourCats.UseCaseInteractor.RemoveDocuments;
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
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RemoveDocumentsAdapterIT {
    @Mock
    RepositoryAccess repo = Mockito.mock(RepositoryAccess.class);

    @Mock
    AnalyzeDocument analyzer = Mockito.mock(AnalyzeDocument.class);

    DataPresenter data;

    @InjectMocks
    RemoveDocuments remDocuments;

    @Before
    public void before(){
        data = new DataPresenter();
        remDocuments = new RemoveDocuments(repo,analyzer,data);
    }

    @Test
    public void verifyLinkFromDataPresenterAndRemoveDocuments(){
        Bdl bdl = new Bdl("nome");
        Document doc = new Document("titolo","contenuto");
        when(repo.readBdl(any(String.class))).thenReturn(bdl);
        when(repo.readDocument(any(String.class))).thenReturn(doc);
        List<String> titleList = new LinkedList<>();
        titleList.add("titolo");
        remDocuments.remove("nome",titleList);
        assertEquals("Selected documents have been removed successfully",data.getMessage());
    }
}
