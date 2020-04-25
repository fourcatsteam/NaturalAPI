package fourcats.view;


import fourcats.interfaceadapters.Controller;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class SuggestionGenerated {
    private JPanel panel1;
    private Controller contr;

    public SuggestionGenerated(String featureName, Controller controller){
        contr = controller;
        JPanel panelNorth = new JPanel(new GridLayout(1,1));
       // panelNorth.setPreferredSize(new Dimension(1000,100));
        JLabel textTitle = new JLabel("NaturalAPI Design");
        textTitle.setHorizontalAlignment(JLabel.CENTER);
        panelNorth.add(textTitle);

        //add some labels and buttons

        JPanel panelCenter = new JPanel();
        panelCenter.setLayout(new BoxLayout(panelCenter,1));

        contr.generateSuggestions(featureName);

        panelCenter.add(new FeatureWidget(panelCenter));
        panelCenter.add(new FeatureWidget(panelCenter));


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

    }

    public void createAndShowGUI() {
        JFrame frame = new JFrame("NaturalAPI Design");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel1);
        frame.setPreferredSize(new Dimension(600,500));
        frame.pack();
        frame.setVisible(true);
    }

}
