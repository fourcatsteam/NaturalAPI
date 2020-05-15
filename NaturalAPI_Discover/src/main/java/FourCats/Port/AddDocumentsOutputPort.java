package FourCats.Port;

import FourCats.Entities.Bdl;

public interface AddDocumentsOutputPort {
    void showAddDocumentsOutput(Bdl bdl);
    void showError(String message);
    void showWarning(String message);
}
