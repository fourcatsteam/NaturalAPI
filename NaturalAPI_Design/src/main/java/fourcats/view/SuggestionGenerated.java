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
    private JPanel panelSuggestion;
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

    public SuggestionGenerated(String featureName, Controller controller, DataPresenterGUI dataPresenter){
        this.contr = controller;
        this.dataPresenter = dataPresenter;
        this.dataPresenter.attach(this);
        this.featureName = featureName;
        this.currentScenarioId = -1;

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

        contr.generateSuggestions(featureName);

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
        panelSuggestion = new JPanel();

        //add a vertical spacer if it's not the first scenario
        if (currentScenarioId!=-1) {
            gridConstraint.gridx = gridX;
            gridConstraint.gridy = gridY;
            Component box = Box.createRigidArea(new Dimension(0, 50));
            gridBagLayout.setConstraints(box, gridConstraint);
            panelInScrollPanel.add(box);
        }

        //add panel suggestion
        this.gridX = 0;
        this.gridY++;
        panelSuggestion.setLayout(new BoxLayout(panelSuggestion,BoxLayout.PAGE_AXIS));
        gridConstraint.gridx = this.gridX;
        gridConstraint.gridy = this.gridY;
        gridBagLayout.setConstraints(panelSuggestion, gridConstraint);
        panelInScrollPanel.add(panelSuggestion);

        //add panel scenario with a new scenarioWidget
        this.gridX++;
        gridConstraint.gridx = this.gridX;
        gridConstraint.gridy = this.gridY;
        gridBagLayout.setConstraints(panelScenario, gridConstraint);
        panelInScrollPanel.add(panelScenario);
        new ScenarioWidget(panelScenario, featureName, dataPresenter.getActor(),
                dataPresenter.getScenarioContent());


        //prepare next scenario by adding a "row" to the grid (gridY++)
        this.gridY++;
        currentScenarioId+=1;
    }

    @Override
    public void update() {
        if (Integer.parseInt(dataPresenter.getScenarioId())!=currentScenarioId) {
            initScenario();
        }
        if (dataPresenter.isSuggestionToAdd()) {
            new SuggestionWidget(panelSuggestion, contr, dataPresenter);
        }
        if (!dataPresenter.getMessage().equals("")) {
            if (dataPresenter.isOkOperation())
                JOptionPane.showMessageDialog(null, dataPresenter.getMessage());
            else
                JOptionPane.showMessageDialog(null,dataPresenter.getMessage(),"Error!",JOptionPane.ERROR_MESSAGE);
        }
    }

}
