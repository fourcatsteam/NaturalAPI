package FourCats.Port;

import FourCats.Entities.Bdl;

public interface RemoveDocumentsOutputPort {
    void showRemoveDocumentOutputPort(Bdl bdl);
    void showError(String message);
    void showWarning(String message);
}
