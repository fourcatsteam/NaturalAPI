package fourcats.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class FeatureWidget extends JComponent{
    private JTextPane textPane1;
    private JPanel panel1;

    public FeatureWidget(JPanel panelCenter){


        JPanel panelNorth = new JPanel(new GridLayout(2,2));
        panelNorth.add(new JLabel("Feature"));
        panelNorth.add(new JLabel("NameF"));
        panelNorth.add(new JLabel("Actor"));
        panelNorth.add(new JLabel("NameA"));
        panel1 = new JPanel();
        panel1.add(panelNorth);
        createAndShowGUI(panelCenter);

    }
    public void createAndShowGUI(JPanel panelCenter) {
        panelCenter.add(panel1);

    }
}
