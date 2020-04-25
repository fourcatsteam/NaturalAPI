package fourcats.view;

import fourcats.interfaceadapters.Controller;
import fourcats.interfaceadapters.DataPresenter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI_Design {
    private Controller controller;
    private DataPresenter datapresenter;

    private JLabel titleText;
    private JButton loadGherkinBtn;
    private JButton genSuggestBtn;
    private JTextArea log;
    private JPanel panel1;

    public GUI_Design(Controller c,DataPresenter dp){
        this.controller = c;
        this.datapresenter = dp;

        genSuggestBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                new SuggestionGenerated().createAndShowGUI();
            }
        });
    }

    public void createAndShowGUI() {
        JFrame frame = new JFrame("NaturalAPI Discover");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new GUI_Design(controller,datapresenter).panel1);
        frame.setPreferredSize(new Dimension(450,400));
        frame.pack();
        frame.setVisible(true);
    }



}
