package fourcats.view.gui;

import fourcats.datastructure.observer.Observer;
import fourcats.interfaceadapters.Controller;
import fourcats.interfaceadapters.DataPresenterGUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class SuggestionGenerated extends Component implements Observer{
    private JPanel mainPanel;
    private JPanel panelButtons;
    private JPanel panelSuggestions;
    private JPanel panelInScrollPanel;
    private JScrollPane scrollPanel;
    private JButton generateBalButton;
    private JButton addFeatureButton;
    private final Controller contr;
    private final DataPresenterGUI dataPresenter;
    private final GridBagConstraints gridConstraint;
    private final GridBagLayout gridBagLayout;
    private int currentScenarioId;
    private int gridX = 0;
    private int gridY = 0;
    private final List<String> lFeatureNames;
    private final List<String> lFeaturePaths;

    public SuggestionGenerated(List<String> featureNames, List<String> featurePaths, Controller controller, DataPresenterGUI dataPresenter){
        this.contr = controller;
        this.dataPresenter = dataPresenter;
        this.dataPresenter.attach(this);
        this.currentScenarioId = -1;
        this.lFeatureNames = new ArrayList<>();
        this.lFeaturePaths = new ArrayList<>();
        this.lFeatureNames.addAll(featureNames);
        this.lFeaturePaths.addAll(featurePaths);
        gridBagLayout = new GridBagLayout();
        gridConstraint = new GridBagConstraints();
        panelInScrollPanel.setLayout(gridBagLayout);
        gridConstraint.fill = GridBagConstraints.HORIZONTAL;

        scrollPanel = new JScrollPane(panelInScrollPanel,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        panelButtons.setLayout(new GridLayout(2,1));
        panelButtons.add(generateBalButton);
        panelButtons.add(addFeatureButton);

        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(5,5,5,5));
        mainPanel.add(scrollPanel,BorderLayout.CENTER);
        mainPanel.add(panelButtons,BorderLayout.SOUTH);


        generateBalButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser("..\\NaturalAPI_Design\\BAL");
            int returnVal = fileChooser.showSaveDialog(SuggestionGenerated.this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                String path = file.getAbsolutePath();
                if (!path.equals("")) {
                    try {
                        contr.generateBAL(path);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            }
        });

        addFeatureButton.addActionListener(e->{
            List<String> newPaths = new ArrayList<>(); //contains paths of new files
            JFileChooser fileChooser = new JFileChooser("..\\NaturalAPI_Design\\gherkin_documents");
            fileChooser.setMultiSelectionEnabled(true);
            int returnVal = fileChooser.showOpenDialog(SuggestionGenerated.this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File[] files = fileChooser.getSelectedFiles();
                for(File f: files){
                    if(!lFeaturePaths.contains(f.getAbsolutePath())) {
                        newPaths.add(f.getAbsolutePath());
                        this.lFeatureNames.add(f.getName());
                        this.lFeaturePaths.add(f.getAbsolutePath());
                    }
                    else{
                        JOptionPane.showMessageDialog(SuggestionGenerated.this,
                                "The file '"+f.getName()+"' has already been uploaded!",
                                "Notice",JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
            if (!newPaths.isEmpty()) {
                contr.generateSuggestions(newPaths, false); //generate suggestions only for the new files
            }

        });
        contr.generateSuggestions(featurePaths,true);
    }

    public void createAndShowGUI() {
        JFrame frame = new JFrame("NaturalAPI Design - Suggestions");
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                //detach from the subject so that the instance of the class will be destroy
                dataPresenter.detach(SuggestionGenerated.this);
            }
        });
        frame.add(mainPanel);
        frame.setPreferredSize(new Dimension(1400,1000));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void initScenario(){
        JPanel panelScenario = new JPanel();
        panelSuggestions = new JPanel();

        //add a vertical spacer in panelInScrollPanel if it's not the first scenario
        if (currentScenarioId!=-1) {
            gridConstraint.gridx = gridX;
            gridConstraint.gridy = gridY;
            Component box = Box.createRigidArea(new Dimension(0, 50));
            gridBagLayout.setConstraints(box, gridConstraint);
            panelInScrollPanel.add(box);
        }
        currentScenarioId++;

        //include panel suggestion in a box with a button which let to manually add a suggestion
        Box suggestionsAndAddButtonBox = Box.createVerticalBox();
        JButton addSuggestionButton = new AddSuggestionButtonWidget(contr,Integer.toString(currentScenarioId)).getButtonWidget();
        suggestionsAndAddButtonBox.add(panelSuggestions);
        suggestionsAndAddButtonBox.add(addSuggestionButton);

        //add the box to the panelInScroll panel after settings constraints x(column) and y(row) values
        this.gridX = 0;
        this.gridY++;
        gridConstraint.gridx = this.gridX;
        gridConstraint.gridy = this.gridY;
        panelSuggestions.setLayout(new BoxLayout(panelSuggestions,BoxLayout.PAGE_AXIS));
        gridBagLayout.setConstraints(suggestionsAndAddButtonBox, gridConstraint);
        panelInScrollPanel.add(suggestionsAndAddButtonBox);


        //add panel scenario in panelInScrollPanel with a new scenarioWidget
        this.gridX++;
        gridConstraint.gridx = this.gridX;
        gridConstraint.gridy = this.gridY;
        gridBagLayout.setConstraints(panelScenario, gridConstraint);
        panelInScrollPanel.add(panelScenario);
        new ScenarioWidget(panelScenario, getFeatureName(), dataPresenter.getActor(),
                dataPresenter.getScenarioContent());

        //get ready for next scenario by adding a "row" to the grid (gridY++)
        this.gridY++;
    }

    @Override
    public void update() {
        //if the suggestions widget needs a complete refresh to be updated with the model, then this needs to be execute
        //to fully recreate all suggestion widgets and scenario widgets in panelInScrollPanel
        if (dataPresenter.isSuggestionsRefreshNeeded()){
            panelInScrollPanel.removeAll();
            panelInScrollPanel.revalidate();
            panelInScrollPanel.repaint();
            currentScenarioId = -1;
        }
        //init a new scenario if the id of the current scenario is different from the one being read from the dataPresenter
        if (Integer.parseInt(dataPresenter.getScenarioId())!=currentScenarioId) {
            initScenario();
        }
        //the update method can be call for different reasons but that doesn't mean we always need to create a new Suggestion widget
        if (dataPresenter.isSuggestionToAdd()) {
            new SuggestionWidget(panelSuggestions, contr, dataPresenter);
        }

        if (!dataPresenter.getMessage().equals("") && !dataPresenter.isBdlLoaded() ) {
            //check if the operation was successful then show information message or error message
            if (dataPresenter.isOkOperation())
                JOptionPane.showMessageDialog(null, dataPresenter.getMessage());
            else
                JOptionPane.showMessageDialog(null, dataPresenter.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String getFeatureName(){
        //the "name" of the feature from the dataPresenter is coming as a path so this method it's useful to get the name of the feature file
        for (String fPath : lFeaturePaths){
            if (fPath.equals(dataPresenter.getFeaturePath())){
                return lFeatureNames.get(lFeaturePaths.indexOf(dataPresenter.getFeaturePath()));
            }
        }
        return "";
    }

}
