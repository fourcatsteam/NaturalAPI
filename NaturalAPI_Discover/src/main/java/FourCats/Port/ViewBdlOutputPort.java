package FourCats.Port;

import FourCats.Entities.Bdl;

public interface ViewBdlOutputPort {
    void showViewBdlOutput(Bdl b);
    void showError(String message);
}
