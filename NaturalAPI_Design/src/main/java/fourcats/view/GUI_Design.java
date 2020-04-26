package fourcats.view;

import fourcats.interfaceadapters.Controller;
import fourcats.interfaceadapters.DataPresenterGUI;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class GUI_Design extends Component {
    private Controller controller;
    private DataPresenterGUI dataPresenter;
    private JLabel titleText;
    private JButton loadGherkinBtn;
    private JButton genSuggestBtn;
    private JTextArea log;
    private JPanel panel1;
    private JFileChooser fc;
    private boolean areFilesLoaded;
    private String nameFeatureFile;

    public GUI_Design(Controller c,DataPresenterGUI dp){
        this.controller = c;
        this.dataPresenter = dp;
        this.fc = new JFileChooser("..\\NaturalAPI_Design\\gherkin_documents");
        this.fc.setMultiSelectionEnabled(true);
        this.areFilesLoaded = false;
        this.log.append("Welcome to NaturalAPI Design!\n\n");

        genSuggestBtn.addActionListener(actionEvent -> {
            if(areFilesLoaded) {
                new SuggestionGenerated(nameFeatureFile, controller, dataPresenter).createAndShowGUI();
            }
            else{
                log.append("Please, load file first.\n");
            }
            });

        loadGherkinBtn.addActionListener(actionEvent -> {
            int returnVal = fc.showOpenDialog(GUI_Design.this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File[] files = fc.getSelectedFiles();
                for(File f: files){
                    log.append("Opening: " + f.getName() + "." + "\n");
                    nameFeatureFile = f.getName();
                }
                this.areFilesLoaded = true;
            } else {
                log.append("Open command cancelled by user." + "\n");
            }
            log.setCaretPosition(log.getDocument().getLength());
        });
    }

    public void createAndShowGUI() {
        JFrame frame = new JFrame("NaturalAPI Design");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new GUI_Design(controller,dataPresenter).panel1);
        frame.setPreferredSize(new Dimension(450,400));
        frame.pack();
        frame.setVisible(true);
    }

}
