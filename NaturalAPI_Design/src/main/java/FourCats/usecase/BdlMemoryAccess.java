package FourCats.usecase;

import FourCats.entity.Bdl;
import FourCats.entity.Document;
import FourCats.interfaceAccess.DirectoryAccess;

import java.io.FileNotFoundException;
import java.util.LinkedList;

public class BdlMemoryAccess {
    Bdl bdl;

    public BdlMemoryAccess(Bdl b){
        bdl = b;
    }

    //Legge dalla memoria una BDL
    public LinkedList<Document> read() throws FileNotFoundException {
        //Questo metodo dovrà interfacciarsi con la directory e prendere i file di tipo CSV
        return null;
    }

    //Salvataggio Bdl su file utilizzando il metodo della bdl
    public void saveBdlToFile(String nameProject) throws FileNotFoundException {
        bdl.saveToFile(nameProject);
    }
}
