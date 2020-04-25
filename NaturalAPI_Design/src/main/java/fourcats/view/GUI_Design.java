package fourcats.view;

import fourcats.datastructure.observer.Observer;
import fourcats.interfaceadapters.Controller;
import fourcats.interfaceadapters.DataPresenter;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class GUI_Design extends Component implements Observer {
    private Controller controller;
    private DataPresenter dataPresenter;

    private JLabel titleText;
    private JButton loadGherkinBtn;
    private JButton genSuggestBtn;
    private JTextArea log;
    private JPanel panel1;
    private JFileChooser fc;
    private Boolean areFilesLoaded;
    private String nameFeatureFile;

    public GUI_Design(Controller c,DataPresenter dp){
        this.controller = c;
        this.dataPresenter = dp;
        this.fc = new JFileChooser("..\\NaturalAPI_Design\\gherkin_documents");
        this.fc.setMultiSelectionEnabled(true);
        this.areFilesLoaded = false;
        this.dataPresenter.attach(this);
        genSuggestBtn.addActionListener(actionEvent -> new SuggestionGenerated(nameFeatureFile,controller).createAndShowGUI());

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

    private void showResults(){
        log.append(dataPresenter.getDataToShow()+"\n");
    }

    @Override
    public void update() {
        showResults();
    }
}
