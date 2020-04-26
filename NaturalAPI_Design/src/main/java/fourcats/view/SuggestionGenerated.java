package fourcats.view;


import fourcats.datastructure.observer.Observer;
import fourcats.interfaceadapters.Controller;
import fourcats.interfaceadapters.DataPresenterGUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class SuggestionGenerated implements Observer{
    private JPanel panel1;
    private Controller contr;
    private DataPresenterGUI dataPresenter;
    private JPanel panelCenter;
    private String featureName;
    private int currentScenarioId;

    public SuggestionGenerated(String featureName, Controller controller, DataPresenterGUI dataPresenter){
        this.contr = controller;
        this.dataPresenter = dataPresenter;
        this.dataPresenter.attach(this);
        this.featureName = featureName;
        this.currentScenarioId = -1;
        JPanel panelNorth = new JPanel(new GridLayout(1,1));
       // panelNorth.setPreferredSize(new Dimension(1000,100));
        JLabel textTitle = new JLabel("NaturalAPI Design");
        textTitle.setHorizontalAlignment(JLabel.CENTER);
        panelNorth.add(textTitle);

        //add some labels and buttons

        panelCenter = new JPanel();
        panelCenter.setLayout(new BoxLayout(panelCenter,1));

        JButton generateBAlBtn = new JButton("Generate BAL");
        JButton addFeatureBtn = new JButton("Add another feature");

        JPanel panelSouth = new JPanel(new GridLayout(2,1));
        panelSouth.add(generateBAlBtn);
        panelSouth.add(addFeatureBtn);

        JScrollPane scrollPaneCenter = new JScrollPane(panelCenter,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        //panelCenter must be scrollable when too many panels are added to panelCenter

        panel1 = new JPanel(new BorderLayout(3,3));
        panel1.setBorder(new EmptyBorder(5,5,5,5));
        panel1.add(panelNorth,BorderLayout.NORTH);
        panel1.add(scrollPaneCenter,BorderLayout.CENTER);
        panel1.add(panelSouth,BorderLayout.SOUTH);


        contr.generateSuggestions(featureName);

    }

    public void createAndShowGUI() {
        JFrame frame = new JFrame("NaturalAPI Design");
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.add(panel1);
        frame.setPreferredSize(new Dimension(600,500));
        frame.pack();
        frame.setVisible(true);
    }


    @Override
    public void update() {
        if (Integer.parseInt(dataPresenter.getScenarioId())!=currentScenarioId) {
            panelCenter.add(new ScenarioWidget(panelCenter, featureName, dataPresenter.getActor(),
                    dataPresenter.getScenarioContent()));
            currentScenarioId +=1;
        }
        panelCenter.add(new SuggestionWidget(panelCenter,contr,dataPresenter.getActionType(),dataPresenter.getActionName(),
                dataPresenter.getObjectId(), dataPresenter.getObjectType(),dataPresenter.getObjectName(),
                dataPresenter.getScenarioId(),dataPresenter.getSuggestionId()));
    }
}
