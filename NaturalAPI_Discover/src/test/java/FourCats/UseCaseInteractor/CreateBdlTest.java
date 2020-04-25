package FourCats.UseCaseInteractor;

import FourCats.Entities.Bdl;
import FourCats.Entities.Document;
import FourCats.InterfaceAccess.RepositoryAccess;
import FourCats.Port.CreateBdlOutputPort;
import FourCats.UseCaseUtilities.AnalyzeDocument;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;

import java.util.LinkedList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CreateBdlTest {

    RepositoryAccess repositoryMock = Mockito.mock(RepositoryAccess.class);
    AnalyzeDocument analyzeMock = Mockito.mock(AnalyzeDocument.class);
    CreateBdlOutputPort outputMock = Mockito.mock(CreateBdlOutputPort.class);

    @InjectMocks
    CreateBdl interactor = new CreateBdl(repositoryMock,analyzeMock,outputMock);

    @Test
    public void testCreateWithDocuments() {

        when(repositoryMock.createBdl(any(String.class))).thenReturn(new Bdl("example"));
        when(repositoryMock.readDocument(any(String.class))).thenReturn(new Document("","word"));

        LinkedList<String> titleList = new LinkedList<>();
        titleList.add("doc1");
        titleList.add("doc2");
        titleList.add("doc3");
        interactor.create("example", titleList);


        verify(repositoryMock).createBdl(any(String.class));
        verify(repositoryMock,times(3)).readDocument(any(String.class));
        verify(analyzeMock,times(3)).addDocumentToBdl(any(Bdl.class),any(Document.class));
        verify(repositoryMock).updateBdl(any(Bdl.class));
        verify(repositoryMock).updateAssociation(any(String.class),any(List.class));
        verify(outputMock).showCreateBdlOutput();
    }
}