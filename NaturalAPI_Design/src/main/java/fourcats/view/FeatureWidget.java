package fourcats.view;

import javax.swing.*;
import java.awt.*;

public class FeatureWidget extends JComponent{
    private JPanel panel1;
    private JTextArea featureTextArea;
    private JLabel filedLabel;
    private JLabel actorLabel;


    public FeatureWidget(JPanel panelCenter){

        JPanel panelNorth = new JPanel(new GridLayout(2,1));
        panelNorth.add(filedLabel);
        panelNorth.add(actorLabel);
        panel1 = new JPanel();
        panel1.add(panelNorth);
        panel1.add(featureTextArea);
        panel1.add(new SuggestionWidget(panelCenter));
        panel1.add(new SuggestionWidget(panelCenter));


        createAndShowGUI(panelCenter);

    }
    public void createAndShowGUI(JPanel panelCenter) {
        panelCenter.add(panel1);

    }

}
