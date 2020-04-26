package fourcats.view;

import javax.swing.*;
import java.awt.*;

public class ScenarioWidget extends JComponent{
    private JPanel panel1;
    private JTextArea featureTextArea;
    private JLabel featureLabel;
    private JLabel actorLabel;


    public ScenarioWidget(JPanel panelCenter, String featureName, String actor, String scenario) {
        JPanel panelNorth = new JPanel(new GridLayout(2, 1));
        panelNorth.add(featureLabel);
        panelNorth.add(actorLabel);
        panel1 = new JPanel();
        actorLabel.setText("Actor: " + actor);
        featureLabel.setText("Feature: " + featureName);
        panel1.add(panelNorth);
        featureTextArea.setText(scenario);
        panel1.add(featureTextArea);
        panelCenter.add(panel1);

    }

}
