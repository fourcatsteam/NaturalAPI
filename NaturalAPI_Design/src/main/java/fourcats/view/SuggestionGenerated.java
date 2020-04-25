package fourcats.view;


import javax.swing.*;
import java.awt.*;

public class SuggestionGenerated {
    private JPanel panel1;

    public void createAndShowGUI() {
        JFrame frame = new JFrame("NaturalAPI Discover");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new SuggestionGenerated().panel1);
        frame.setPreferredSize(new Dimension(450,400));
        frame.pack();
        frame.setVisible(true);
    }
}
