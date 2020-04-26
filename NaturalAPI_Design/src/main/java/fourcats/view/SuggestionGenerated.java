package fourcats.view;


import fourcats.datastructure.observer.Observer;
import fourcats.interfaceadapters.Controller;
import fourcats.interfaceadapters.DataPresenterGUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class SuggestionGenerated implements Observer{
    private JPanel mainPanel;
    private JPanel panelButtons;
    private JPanel panelScenario;
    private JPanel panelSuggestion;
    private JPanel panelInScrollPanel;
    private JScrollPane scrollPanel;
    private JButton generateBalButton;
    private JButton addFeatureButton;
    private Controller contr;
    private DataPresenterGUI dataPresenter;
    private String featureName;
    private GridBagConstraints gridConstraint;
    private GridBagLayout gridBagLayout;
    private int currentScenarioId;
    private int gridX = 0;
    private int gridY = 0;

    public SuggestionGenerated(String featureName, Controller controller, DataPresenterGUI dataPresenter){
        this.contr = controller;
        this.dataPresenter = dataPresenter;
        this.dataPresenter.attach(this);
        this.featureName = featureName;
        this.currentScenarioId = -1;

        panelInScrollPanel = new JPanel();
        gridBagLayout = new GridBagLayout();
        gridConstraint = new GridBagConstraints();
        panelInScrollPanel.setLayout(gridBagLayout);
        gridConstraint.fill = GridBagConstraints.HORIZONTAL;

        scrollPanel = new JScrollPane(panelInScrollPanel,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        panelButtons = new JPanel(new GridLayout(2,1));
        panelButtons.add(generateBalButton);
        panelButtons.add(addFeatureButton);

        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(5,5,5,5));
        mainPanel.add(scrollPanel,BorderLayout.CENTER);
        mainPanel.add(panelButtons,BorderLayout.SOUTH);

        contr.generateSuggestions(featureName);
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
        panelScenario = new JPanel();
        panelSuggestion = new JPanel();
        panelSuggestion.setLayout(new BoxLayout(panelSuggestion,BoxLayout.PAGE_AXIS));
        gridConstraint.gridx = this.gridX;
        gridConstraint.gridy = this.gridY;
        gridBagLayout.setConstraints(panelSuggestion, gridConstraint);
        panelInScrollPanel.add(panelSuggestion);

        this.gridX++;
        gridConstraint.gridx = this.gridX;
        gridConstraint.gridy = this.gridY;
        gridBagLayout.setConstraints(panelScenario, gridConstraint);
        panelInScrollPanel.add(panelScenario);

        new ScenarioWidget(panelScenario, featureName, dataPresenter.getActor(),
                dataPresenter.getScenarioContent());
        this.gridX=0;
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
    }
}
