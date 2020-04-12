package FourCats.usecase;
import FourCats.DataStructure.LemmatizerData;
import FourCats.entity.Bdl;
import FourCats.entity.Document;
import FourCats.entity.Documents;

import java.io.FileNotFoundException;

public class BdlGenerator {
    //Tra i campi dati, oltre LoadDocument e DocumentParser ottenuti dal main, crea due oggetti
    //BdlMemoryAccess per salvare la bdl in memoria e il DataAnalyzer che si interfaccia con l'entità BDL
    LoadDocument ld;
    DocumentParser dp;
    BdlMemoryAccess bma;
    DataAnalyzer da;

    public BdlGenerator(DocumentParser d,LoadDocument l){
        dp = d;
        ld = l;
        da = new DataAnalyzer();
        //Il BdlMemoryAccess ha bisogno della BDL come parametro
        bma = new BdlMemoryAccess(da.getBdl());
    }

    public Bdl generateBDL(String namebdl){
        //Elabora i dati ricervuti da il Document Parser
        //Viene eseguito un ciclo che itera ogni documento
        for(Document d: ld.getList()){

            //Per ogni documento si richiama parseSentence di DocumentParser e si aggiunge alla bdl i predicati
            //restituiti da parseSentence tramite il metodo receivePredicate del DataAnalyzer
            //In pratica invia i predicati al DataAnalyzer il quale si occuperà dell'aggiunta nella BDL
            for(String s: dp.parseSentence(d.getContent())){
                da.receivePredicate(s);
            }

            //Per ogni documenti si richiama lemmatizeSentence, e si aggiunge alla bdl i nomi e i verbi sempre mediante
            //il DocumentAnalyzer
            //NB La classe WordTag è una classe PUBBLICA
            for(LemmatizerData.WordTag wtag : dp.lemmatizeSentence(d.getContent()).getList()){
                if(wtag.getTag().contains("NN")) {
                    da.receiveNoun(wtag.getLemma());
                }
                if(wtag.getTag().contains("VB")) {
                    da.receiveVerb(wtag.getLemma());
                }
            }
            //Richiamo il metodo del DocumentAnalyzer per ordinare la bdl
            da.orderBdl();
        }

        //Salvataggio della bdl utilizzando l'oggetto BdlMemoryAccess
        System.out.println("Salvataggio della BDL in memoria...");
        try {
            bma.saveBdlToFile(namebdl);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return da.getBdl();
    }





}
