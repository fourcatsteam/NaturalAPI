package fourcats.view.gui;

import javax.swing.*;
import java.awt.*;

public class ScenarioWidget extends JComponent{
    private JPanel mainPanel;
    private JTextArea featureTextArea;
    private JLabel featureLabel;
    private JLabel actorLabel;


    public ScenarioWidget(JPanel panelToUpdate, String featureName, String actor, String scenario) {
        JPanel panelNorth = new JPanel(new GridLayout(2, 1));
        panelNorth.add(featureLabel);
        panelNorth.add(actorLabel);
        actorLabel.setText("---Actor: " + actor);
        featureLabel.setText("---Feature: " + featureName);

        mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.PAGE_AXIS));
        mainPanel.add(Box.createHorizontalStrut(20));
        mainPanel.add(panelNorth);
        mainPanel.add(featureTextArea);
        featureTextArea.setText(scenario);

        panelToUpdate.add(mainPanel);

    }

}
