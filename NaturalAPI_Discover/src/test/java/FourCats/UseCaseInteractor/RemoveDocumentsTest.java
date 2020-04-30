package FourCats.UseCaseInteractor;

import FourCats.Entities.Bdl;
import FourCats.Entities.Document;
import FourCats.InterfaceAccess.RepositoryAccess;
import FourCats.Port.RemoveDocumentsOutputPort;
import FourCats.UseCaseUtilities.AnalyzeDocument;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.LinkedList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RemoveDocumentsTest {

    @Mock
    RepositoryAccess repositoryMock;

    @Mock
    AnalyzeDocument analyzerMock;

    @Mock
    RemoveDocumentsOutputPort outputMock;

    @InjectMocks
    RemoveDocuments interactor;

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
        verify(outputMock).showRemoveDocumentOutputPort(any(Bdl.class));

    }
}