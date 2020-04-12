package FourCats.InterfaceAccess;
import FourCats.Entities.Action;
import FourCats.Entities.Bdl;
import FourCats.Entities.Scenario;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

public interface RepositoryAccess {
    String read(String titleFile) throws FileNotFoundException;
    //Ritorna la lista con i contenuti
    void createScenario(Scenario scenario);
    boolean deleteSuggestion(int idAction, int idScenario);
    Map<Integer, Scenario> readScenarios();
    void deleteScenarios();





}
