package fourcats.view;

import fourcats.datastructure.observer.Observer;
import fourcats.interfaceadapters.Controller;
import fourcats.interfaceadapters.DataPresenterGUI;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class GUI_Design extends Component implements Observer  {
    private Controller controller;
    private DataPresenterGUI dataPresenter;
    private JLabel titleText;
    private JButton loadGherkinBtn;
    private JButton genSuggestBtn;
    private JTextArea log;
    private JPanel mainPanel;
    private JButton loadBDLButton;
    private JFileChooser fc;
    private boolean areFilesLoaded;
    private String nameFeatureFile;
    private String pathFeatureFile;
    private boolean isBdlLoaded;


    public GUI_Design(Controller c,DataPresenterGUI dp){
        this.controller = c;
        this.dataPresenter = dp;
        this.fc = new JFileChooser("..\\NaturalAPI_Design\\gherkin_documents");
        this.fc.setMultiSelectionEnabled(true);
        this.areFilesLoaded = false;
        this.isBdlLoaded = false;
        this.log.append("Welcome to NaturalAPI Design!\n\n");
        this.dataPresenter.attach(this);

        genSuggestBtn.addActionListener(actionEvent -> {
            if(areFilesLoaded) {
                new SuggestionGenerated(nameFeatureFile, pathFeatureFile, controller, dataPresenter,isBdlLoaded).createAndShowGUI();
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
                    pathFeatureFile = f.getAbsolutePath();
                    nameFeatureFile = f.getName();

                }
                this.areFilesLoaded = true;
            } else {
                log.append("\nOpen command cancelled by user." + "\n");
            }
            log.setCaretPosition(log.getDocument().getLength());
        });



        loadBDLButton.addActionListener(actionEvent -> {
            String bdlnameFile[] = new String[3];
            int i=0;
            int returnVal = fc.showOpenDialog(GUI_Design.this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File[] files = fc.getSelectedFiles();
                for(File f: files){
                    log.append("Opening: " + f.getName() + "." + "\n");
                    bdlnameFile[i] = f.getName();
                    i++;
                }
                this.isBdlLoaded = true;
            } else {
                log.append("Open command cancelled by user." + "\n");
            }
            log.setCaretPosition(log.getDocument().getLength());
            try {
                controller.loadBdl(bdlnameFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void createAndShowGUI() {
        JFrame frame = new JFrame("NaturalAPI Design");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new GUI_Design(controller,dataPresenter).mainPanel);
        frame.setPreferredSize(new Dimension(450,400));
        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public void update() {
        showOutput();
    }

    private void showOutput(){
        log.append(dataPresenter.getMessage());
    }
}
