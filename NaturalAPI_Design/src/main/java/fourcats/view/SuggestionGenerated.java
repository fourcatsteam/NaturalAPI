package fourcats.view;


import fourcats.datastructure.observer.Observer;
import fourcats.interfaceadapters.Controller;
import fourcats.interfaceadapters.DataPresenterGUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.IOException;

public class SuggestionGenerated implements Observer{
    private JPanel mainPanel;
    private JPanel panelButtons;
    private JPanel panelSuggestions;
    private JPanel panelInScrollPanel;
    private JScrollPane scrollPanel;
    private JButton generateBalButton;
    private JButton addFeatureButton;
    private final Controller contr;
    private final DataPresenterGUI dataPresenter;
    private final String featureName;
    private final GridBagConstraints gridConstraint;
    private final GridBagLayout gridBagLayout;
    private int currentScenarioId;
    private int gridX = 0;
    private int gridY = 0;
    private boolean isBdlLoaded;

    public SuggestionGenerated(String featureName, String featurePath, Controller controller, DataPresenterGUI dataPresenter,boolean bdlLoaded){
        this.contr = controller;
        this.dataPresenter = dataPresenter;
        this.dataPresenter.attach(this);
        this.featureName = featureName;
        this.currentScenarioId = -1;
        this.isBdlLoaded = bdlLoaded;
        gridBagLayout = new GridBagLayout();
        gridConstraint = new GridBagConstraints();
        panelInScrollPanel.setLayout(gridBagLayout);
        gridConstraint.fill = GridBagConstraints.HORIZONTAL;

        scrollPanel = new JScrollPane(panelInScrollPanel,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        panelButtons.setLayout(new GridLayout(2,1));
        panelButtons.add(generateBalButton);
        panelButtons.add(addFeatureButton);

        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(5,5,5,5));
        mainPanel.add(scrollPanel,BorderLayout.CENTER);
        mainPanel.add(panelButtons,BorderLayout.SOUTH);


        generateBalButton.addActionListener(e -> {
            String balName = JOptionPane.showInputDialog("Enter the name for the BAL");
            if (balName != null && !balName.equals("")){
                try {
                    contr.generateBAL(balName);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
        contr.generateSuggestions(featurePath);
    }

    public void createAndShowGUI() {
        JFrame frame = new JFrame("NaturalAPI Design - Suggestions");
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.add(mainPanel);
        frame.setPreferredSize(new Dimension(1200,800));
        frame.pack();
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
        new ScenarioWidget(panelScenario, featureName, dataPresenter.getActor(),
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
        //suggestions can be updated for different reasons in the model but that doesn't mean we always need to create a new Suggestion widget
        if (dataPresenter.isSuggestionToAdd()) {
            new SuggestionWidget(panelSuggestions, contr, dataPresenter,isBdlLoaded);
        }

        if (!dataPresenter.getMessage().equals("")) {
            //check if the operation was successful than show information message or error message
            if (dataPresenter.isOkOperation())
                JOptionPane.showMessageDialog(null, dataPresenter.getMessage());
            else
                JOptionPane.showMessageDialog(null,dataPresenter.getMessage(),"Error!",JOptionPane.ERROR_MESSAGE);
        }

    }

}
