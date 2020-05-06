package fourcats.view.gui;

import fourcats.datastructure.observer.Observer;
import fourcats.interfaceadapters.Controller;
import fourcats.interfaceadapters.DataPresenterGUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GUI_Design extends Component implements Observer  {
    private Controller controller;
    private DataPresenterGUI dataPresenter;
    private JLabel titleText;
    private JButton loadFeatureBtn;
    private JButton genSuggestBtn;
    private JTextArea log;
    private JPanel mainPanel;
    private JButton loadBDLButton;
    private JButton removeFeatureBtn;
    private JButton removeBdlBtn;
    private JPanel removeFilesBtnPanel;
    private JFileChooser fc;
    private String[] bdlNameFile;
    private final List<String> featureFilesPath;
    private final List<String> featureFilesName;
    private static final String WELCOME = "Welcome to NaturalAPI Design!";
    private static final String ADD_FEATURE_FILES = "Please start by adding one or more feature files.";
    private static final String ADD_BDL = "Consider loading a BDL in order to take full advantage of the potential of NaturalAPI Design.";
    private static final String LOADED_FEATURE = "Currently loaded feature files: ";
    private static final String LOADED_BDL = "Currently loaded BDL files: ";


    public GUI_Design(Controller c, DataPresenterGUI dp){
        this.controller = c;
        this.dataPresenter = dp;
        this.fc = new JFileChooser("..\\NaturalAPI_Design\\gherkin_documents");
        this.fc.setMultiSelectionEnabled(true);
        this.log.append(WELCOME + "\n\n" + ADD_FEATURE_FILES + "\n" + ADD_BDL + "\n");
        this.dataPresenter.attach(this);
        this.featureFilesPath = new ArrayList<>();
        this.featureFilesName = new ArrayList<>();
        this.bdlNameFile = null;
        this.mainPanel.setBorder(new EmptyBorder(5,5,5,5));


        genSuggestBtn.addActionListener(actionEvent -> {
            if(featureFilesPath.size()!=0) {
                new SuggestionGenerated(featureFilesName, featureFilesPath, controller, dataPresenter).createAndShowGUI();
            }
            else{
                JOptionPane.showMessageDialog(null,"Please, load feature file first!", "No feature loaded", JOptionPane.WARNING_MESSAGE);
            }
        });

        loadFeatureBtn.addActionListener(actionEvent -> {
            int returnVal = fc.showOpenDialog(GUI_Design.this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File[] files = fc.getSelectedFiles();
                for(File f: files){
                    if(!featureFilesPath.contains(f.getAbsolutePath())) {
                        featureFilesPath.add(f.getAbsolutePath());
                        featureFilesName.add(f.getName());
                    }
                    else{
                        JOptionPane.showMessageDialog(GUI_Design.this, "The file '"+f.getName()+"' has already been uploaded!",
                                "Notice",JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                }
                logPrintStatus();
            }
            log.setCaretPosition(log.getDocument().getLength());
        });


        loadBDLButton.addActionListener(actionEvent -> {
            int i=0;
            int returnVal = fc.showOpenDialog(GUI_Design.this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File[] files = fc.getSelectedFiles();
                if (files.length==3){
                    bdlNameFile = new String[3];
                    for(File f: files){
                        bdlNameFile[i] = f.getName();
                        i++;
                    }
                    try {
                        controller.loadBdl(bdlNameFile);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return;
                }
                JOptionPane.showMessageDialog(GUI_Design.this, "Abort: you have to select 3 files: predicates,verbs and nouns", "Bdl not loaded", JOptionPane.ERROR_MESSAGE);
            }
            log.setCaretPosition(log.getDocument().getLength());
        });

        removeFeatureBtn.addActionListener(e->{
            if (!featureFilesPath.isEmpty()){
                featureFilesPath.clear();
                featureFilesName.clear();
                log.setText("Done! Feature files removed.\n\n");

            }
            else{
                log.setText("Nothing to remove: no feature files uploaded!\n\n");
            }
            logPrintStatus();
        });

        removeBdlBtn.addActionListener(e->{
            if (bdlNameFile!=null){
                bdlNameFile = null;
                controller.removeBdl();
            }
            else{
                log.setText("Nothing to remove: no BDL uploaded!\n\n");
            }
            logPrintStatus();
        });
    }

    public void createAndShowGUI() {
        JFrame frame = new JFrame("NaturalAPI Design");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new GUI_Design(controller,dataPresenter).mainPanel);
        frame.setPreferredSize(new Dimension(650,550));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    @Override
    public void update() {
        showOutput();
    }

    private void showOutput(){
        if (!dataPresenter.getMessage().equals("")) {
            log.setText("\n" + dataPresenter.getMessage() + "\n");
            logPrintStatus();
        }
    }

    private void logPrintStatus(){
        if (featureFilesName.isEmpty() && bdlNameFile!=null){
            log.append(ADD_FEATURE_FILES+"\n");
            log.append("\n"+LOADED_BDL+"\n\n");
            for (String bdlFile : bdlNameFile) {
                log.append("- "+ bdlFile +"\n");
            }
        }
        else if (featureFilesName.isEmpty()){
            log.append(ADD_FEATURE_FILES+"\n");
            log.append(ADD_BDL+"\n");
        }
        else if (bdlNameFile == null){
            log.setText(LOADED_FEATURE+"\n\n");
            featureFilesName.forEach(fileName->log.append("- " + fileName+"\n"));
            log.append("\n"+ADD_BDL);
        }
        else {
            log.setText(LOADED_FEATURE+"\n\n");
            featureFilesName.forEach(fileName->log.append("- " + fileName+"\n"));
            log.append("\n"+LOADED_BDL+"\n\n");
            for (String bdlFile : bdlNameFile) {
                log.append("- "+ bdlFile +"\n");
            }
            log.append("\nGood to go! Click the 'Generate suggestions for BAL' button.");
        }

    }

}
