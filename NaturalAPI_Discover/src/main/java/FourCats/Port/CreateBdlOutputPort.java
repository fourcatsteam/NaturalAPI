package FourCats.Port;

import FourCats.Entities.Bdl;

public interface CreateBdlOutputPort {
    void showCreateBdlOutput(Bdl bdl);
    void showError(String message);
    void showWarning(String message);

}
