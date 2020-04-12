package FourCats.interfaceFrameworks;

import FourCats.entity.Document;
import FourCats.interfaceAccess.DirectoryAccess;

import java.io.*;
import java.util.LinkedList;

public class DirectoryAccessBoundary implements DirectoryAccess {
    //Il boundary contiene un campo dati che simbolegga il nome dei titoli inseriti dall'utente
    private LinkedList<String> nameTitleList;

    public DirectoryAccessBoundary(){
        nameTitleList = new LinkedList<String>();
    }

    //Funzione che ti permette di scegliere file, chiude il buffer solo se l'input inserito è EXIT
    public String chooseFile() throws IOException {
        System.out.println("Choose a namefile, digit EXIT to exit lol");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String r = br.readLine();
        if(r.equals("EXIT")) {
            br.close();
        }else {
            nameTitleList.add(r);
        }
        return r;
    }

    //Questo metoto ritorna una LinkedList di Document
    //Cicla tutti i titoli che abbiamo nel campo dati nameTitleList
    //E per ogni titolo crea un documento tramite il metodo makeDocument
    //NB autore,anno e lingua sono a caso
    public LinkedList<Document> read() throws FileNotFoundException {
        LinkedList<Document> ls = new LinkedList<Document>();
        for(int i=0;i<nameTitleList.size();i++){
            //Posso fare questo?
            //Nel senso, posso creare un Document?
            ls.add(makeDocument(nameTitleList.get(i),read(nameTitleList.get(i))));
            //read(String) richiama il metodo privato di questa classe che ritorna il contenuto del documento
        }
        return ls;
    }



    //Creazione del documento da un titolo e il contenuto
    public Document makeDocument(String title,String cont) {
        return new Document(title,"me",2020,cont,"ita");
    }

    //Ritorna una stringa con il contenuto del documento con il nome filename
    private String read(String filename) throws FileNotFoundException {
        String filepath = "txt_documents/" + filename;
        String s, fileContent = new String();
        try{
            BufferedReader input = new BufferedReader(new FileReader(filepath));
            while((s=input.readLine()) != null){
                fileContent += s + " ";
            }
            input.close();

        }catch(IOException e){
            //modificare gestione eccezioni
            fileContent="file non trovato";
        }
        return fileContent;
    }


}
