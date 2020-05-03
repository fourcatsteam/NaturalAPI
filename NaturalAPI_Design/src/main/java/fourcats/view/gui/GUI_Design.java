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
    private JButton loadGherkinBtn;
    private JButton genSuggestBtn;
    private JTextArea log;
    private JPanel mainPanel;
    private JButton loadBDLButton;
    private JFileChooser fc;
    private boolean areFilesLoaded;
    private List<String> gherkinFilesPath;
    private List<String> gherkinFilesName;


    public GUI_Design(Controller c,DataPresenterGUI dp){
        this.controller = c;
        this.dataPresenter = dp;
        this.fc = new JFileChooser("..\\NaturalAPI_Design\\gherkin_documents");
        this.fc.setMultiSelectionEnabled(true);
        this.areFilesLoaded = false;
        this.log.append("Welcome to NaturalAPI Design!\n\n");
        this.dataPresenter.attach(this);
        this.gherkinFilesPath = new ArrayList<>();
        this.gherkinFilesName = new ArrayList<>();
        this.mainPanel.setBorder(new EmptyBorder(5,5,5,5));


        genSuggestBtn.addActionListener(actionEvent -> {
            if(areFilesLoaded) {
                new SuggestionGenerated(gherkinFilesName, gherkinFilesPath, controller, dataPresenter).createAndShowGUI();
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
                    //check if the file has already been uploaded
                    if(!gherkinFilesPath.contains(f.getAbsolutePath())) {
                        log.append("Opening: " + f.getName() + "." + "\n");
                        gherkinFilesPath.add(f.getAbsolutePath());
                        gherkinFilesName.add(f.getName());
                    }
                    else{
                        JOptionPane.showMessageDialog(GUI_Design.this, "The file '"+f.getName()+"' has already been uploaded!",
                                "Notice",JOptionPane.INFORMATION_MESSAGE);
                    }
                }
                this.areFilesLoaded = true;
            } else {
                log.append("\nOpen command cancelled by user." + "\n");
            }
            log.setCaretPosition(log.getDocument().getLength());
        });


        loadBDLButton.addActionListener(actionEvent -> {
            String[] bdlNameFile = new String[3];
            int i=0;
            int returnVal = fc.showOpenDialog(GUI_Design.this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File[] files = fc.getSelectedFiles();
                for(File f: files){
                    log.append("Opening: " + f.getName() + "." + "\n");
                    bdlNameFile[i] = f.getName();
                    i++;
                }
            } else {
                log.append("Open command cancelled by user." + "\n");
            }
            log.setCaretPosition(log.getDocument().getLength());
            try {
                controller.loadBdl(bdlNameFile);
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
