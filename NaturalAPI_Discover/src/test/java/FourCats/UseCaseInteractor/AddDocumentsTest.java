package FourCats.UseCaseInteractor;

import FourCats.Entities.Bdl;
import FourCats.Entities.Document;
import FourCats.InterfaceAccess.RepositoryAccess;
import FourCats.Port.AddDocumentsOutputPort;
import FourCats.UseCaseUtilities.AnalyzeDocument;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;

import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.*;

public class AddDocumentsTest {

    RepositoryAccess repositoryMock = Mockito.mock(RepositoryAccess.class);
    AnalyzeDocument analyzerMock = Mockito.mock(AnalyzeDocument.class);
    AddDocumentsOutputPort outputMock = Mockito.mock(AddDocumentsOutputPort.class);

    @InjectMocks
    AddDocuments interactor = new AddDocuments(repositoryMock,analyzerMock,outputMock);

    @Test
    public void testAddWithAssociation() {

        when(repositoryMock.readBdl(any(String.class))).thenReturn(new Bdl("example"));
        when(repositoryMock.readAssociation(any(String.class))).thenReturn(new LinkedList<>());
        when(repositoryMock.readDocument(any(String.class))).thenReturn(new Document("","word"));

        LinkedList<String> titleList = new LinkedList<>();
        titleList.add("doc1");
        titleList.add("doc2");
        titleList.add("doc3");
        interactor.add("example",titleList);

        verify(repositoryMock).readBdl(any(String.class));
        verify(repositoryMock).readAssociation(any(String.class));
        verify(repositoryMock, times(3)).readDocument(any(String.class));
        verify(analyzerMock, times(3)).addDocumentToBdl(any(Bdl.class),any(Document.class));
        verify(repositoryMock).updateBdl(any(Bdl.class));
        verify(repositoryMock).updateAssociation(any(String.class),any(List.class));
        verify(outputMock).showAddDocumentsOutput();

    }
}