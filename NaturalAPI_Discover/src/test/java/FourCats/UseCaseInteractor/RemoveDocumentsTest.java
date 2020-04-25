package FourCats.UseCaseInteractor;

import FourCats.Entities.Bdl;
import FourCats.Entities.Document;
import FourCats.InterfaceAccess.RepositoryAccess;
import FourCats.Port.RemoveDocumentsOutputPort;
import FourCats.UseCaseUtilities.AnalyzeDocument;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;

import java.util.LinkedList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class RemoveDocumentsTest {

    RepositoryAccess repositoryMock = Mockito.mock(RepositoryAccess.class);
    AnalyzeDocument analyzerMock = Mockito.mock(AnalyzeDocument.class);
    RemoveDocumentsOutputPort outputMock = Mockito.mock(RemoveDocumentsOutputPort.class);

    @InjectMocks
    RemoveDocuments interactor = new RemoveDocuments(repositoryMock,analyzerMock,outputMock);

    @Test
    public void testRemoveWithDocumentFound() {
        when(repositoryMock.readBdl(any(String.class))).thenReturn(new Bdl("example"));
        when(repositoryMock.readDocument(any(String.class))).thenReturn(new Document("","word"));

        LinkedList<String> titleList = new LinkedList<>();
        titleList.add("doc1");
        interactor.remove("example",titleList);

        verify(repositoryMock).readBdl(any(String.class));
        verify(repositoryMock,times(1)).readDocument(any(String.class));
        verify(analyzerMock,times(1)).removeDocumentFromBdl(any(Bdl.class),any(Document.class));
        verify(repositoryMock).updateBdl(any(Bdl.class));
        verify(repositoryMock).updateAssociation(any(String.class),any(List.class));
        verify(outputMock).showRemoveDocumentOutputPort();

    }
}