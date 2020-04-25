package FourCats.GUI;

import FourCats.InterfaceAdapters.Controller;
import FourCats.InterfaceAdapters.DataPresenter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import FourCats.Observer.Observer;

public class GUI_Discover extends JPanel implements Observer{
    private JButton loadFileBtn;
    private JPanel panel1;
    private JFileChooser fc;
    private JTextArea log;
    private JButton createBDLbtn;
    private JButton addDocumentBtn;
    private JButton removeDococumentBtn;
    private JButton viewBDLBtn;
    private JScrollPane scrollNouns;
    private JScrollPane scrollVerbs;
    private JScrollPane scrollPredicates;
    private JTextPane panelVerbs;
    private JTextPane panelPredicates;
    private JTextPane panelNouns;
    private JScrollPane logScroll;
    private Boolean areFilesLoaded;

    private Controller controller;
    private DataPresenter datapresenter;
    private LinkedList<String> nameTitleList;

    public GUI_Discover(Controller c, DataPresenter d) {
        this.areFilesLoaded = false;
        this.controller = c;
        this.datapresenter= d;
        datapresenter.attach(this);
        nameTitleList = new LinkedList<>();
        fc = new JFileChooser("..\\NaturalAPI_Discover\\txt_documents");
        fc.setMultiSelectionEnabled(true);

        loadFileBtn.addActionListener(actionEvent -> {
            int returnVal = fc.showOpenDialog(GUI_Discover.this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File[] files = fc.getSelectedFiles();
                for(File f: files){
                    log.append("Opening: " + f.getName() + "." + "\n");
                    nameTitleList.add(f.getName());
                }
                this.areFilesLoaded = true;
            } else {
                log.append("Open command cancelled by user." + "\n");
            }
            log.setCaretPosition(log.getDocument().getLength());
        });

        createBDLbtn.addActionListener(actionEvent -> {
            String result = this.choosing(1);
            if(result!=null) {
                controller.createBdl(result, nameTitleList);
                nameTitleList.clear();
                this.areFilesLoaded = false;
            }else{
                if(this.areFilesLoaded) {
                    log.append("Your files are still available.");
                }
            }
        });

        addDocumentBtn.addActionListener(actionEvent -> {
            String result = this.choosing(2);
            controller.addDocument(result, nameTitleList);
        });

        removeDococumentBtn.addActionListener(actionEvent -> {
            String result = this.choosing(2);
            controller.removeDocument(result, nameTitleList);
        });

        viewBDLBtn.addActionListener(actionEvent -> {
            String result = (String)JOptionPane.showInputDialog(this,
                    "Choose the name of the bdl that you want to view",
                    "Choose name",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    null,
                    null );

            controller.viewBdl(result);
        });
    }

    private String choosing(int useCase){
        String result = null;
        String text = useCase == 1 ? "Choose a name for the BDL" : "Choose the name of the BDL that you want update";
        if(!this.areFilesLoaded){
            JOptionPane.showMessageDialog(this,"Please, load at least one .txt file, by pressing \"Load File\"","Load file error",JOptionPane.ERROR_MESSAGE);
        }else {
            result = (String)JOptionPane.showInputDialog(this,
                    text,
                    "Choose name",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    null,
                    null            );

            if(result != null && result.length() > 0){
                log.append("Name choosed: " + result +"\n");
            }else {
                log.append("No name choosed. Please choose one.\n");
                return null;
            }
        }
        return result;
    }

    private void clearing(){
        //Clear the TextArea
        log.selectAll();
        log.replaceSelection("");
    }

    public void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("NaturalAPI Discover");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Add content to the window.
        frame.add(new GUI_Discover(controller,datapresenter).panel1);
        frame.setPreferredSize(new Dimension(600,500));
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    private void showResult(){
        if(datapresenter.getData().startsWith("-- NOUNS --")){
            panelNouns.setText(datapresenter.getData());
        }else if(datapresenter.getData().startsWith("-- VERBS --")){
            panelVerbs.setText(datapresenter.getData());
        }else if(datapresenter.getData().startsWith("-- PREDICATES --")){
            panelPredicates.setText(datapresenter.getData());
        }else{
            log.append(datapresenter.getMessage());
        }
    }

    @Override
    public void update() {
        showResult();
    }


}
