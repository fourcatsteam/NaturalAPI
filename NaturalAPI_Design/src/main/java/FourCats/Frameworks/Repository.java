package FourCats.Frameworks;

import FourCats.Entities.Scenario;
import FourCats.InterfaceAccess.PersistentMemoryAccess;
import FourCats.InterfaceAccess.RepositoryAccess;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

public class Repository implements RepositoryAccess  {
    DataKeeper dataKeeper;
    PersistentMemoryAccess memoryAccess;

    public Repository(DataKeeper dataKeeper, PersistentMemoryAccess memoryAccess){
        this.dataKeeper = dataKeeper;
        this.memoryAccess = memoryAccess;
    }


    @Override
    public String read(String fileName) throws FileNotFoundException {
        return memoryAccess.readFile(fileName);
    }

    @Override
    public void createScenario(Scenario scenario) {
        dataKeeper.addScenarioToMap(scenario);
    }


    @Override
    public void deleteSuggestion(int idAction, int idScenario) {
        dataKeeper.removeAction(idScenario,idAction);
    }

    @Override
    public Map<Integer, Scenario> readScenarios() {
        return dataKeeper.getScenarioMap();
    }


    @Override
    public void deleteScenarios() {
        dataKeeper.clearScenariosMap();
    }

    @Override
    public void createBAL(String bal, String filename) throws IOException {
        memoryAccess.writeFile(bal,filename);
    }


}
