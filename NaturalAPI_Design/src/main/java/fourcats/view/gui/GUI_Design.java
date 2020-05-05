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
    private List<String> featureFilesPath;
    private List<String> featureFilesName;


    public GUI_Design(Controller c, DataPresenterGUI dp){
        this.controller = c;
        this.dataPresenter = dp;
        this.fc = new JFileChooser("..\\NaturalAPI_Design\\gherkin_documents");
        this.fc.setMultiSelectionEnabled(true);
        this.log.append("Welcome to NaturalAPI Design!\n\n");
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
                log.append("\nPlease, load feature file first.");
            }
        });

        loadFeatureBtn.addActionListener(actionEvent -> {
            int returnVal = fc.showOpenDialog(GUI_Design.this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File[] files = fc.getSelectedFiles();
                for(File f: files){
                    //check if the file has already been uploaded
                    if(!featureFilesPath.contains(f.getAbsolutePath())) {
                        log.append("\nOpening: " + f.getName() + ".");
                        featureFilesPath.add(f.getAbsolutePath());
                        featureFilesName.add(f.getName());
                    }
                    else{
                        JOptionPane.showMessageDialog(GUI_Design.this, "The file '"+f.getName()+"' has already been uploaded!",
                                "Notice",JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            } else {
                log.append("\nOpen command cancelled by user.");
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
                        log.append("\nOpening: " + f.getName() + ".");
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

            } else {
                log.append("\nOpen command cancelled by user.");
            }
            log.setCaretPosition(log.getDocument().getLength());
        });

        removeFeatureBtn.addActionListener(e->{
            if (featureFilesPath.size()!=0){
                featureFilesPath.clear();
                featureFilesName.clear();
                log.append("\n"+ "Done! Feature files removed.");
            }
            else{
                log.append("\n"+"Nothing to remove: no feature files uploaded!");
            }
        });

        removeBdlBtn.addActionListener(e->{
            if (bdlNameFile!=null){
                bdlNameFile = null;
                controller.removeBdl();
            }
            else{
                log.append("\n"+"Nothing to remove: no BDL uploaded!");
            }
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
            log.append("\n" + dataPresenter.getMessage() + "\n");
        }
    }

}
