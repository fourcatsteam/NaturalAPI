package fourcats.view;

import javax.swing.*;

public class SuggestionWidget extends JComponent {
    private JComboBox actionTypeComboBox;
    private JTextField actionNameTextField;
    private JComboBox objectTypeComboBox;
    private JTextField objectNameTextField;
    private JButton addObjectButton;
    private JButton removeSuggestionButton;
    private JButton removeObjectButton;
    private JPanel mainPanel;

    public SuggestionWidget(JPanel panelCenter){
        mainPanel = new JPanel();
        mainPanel.add(actionTypeComboBox);
        mainPanel.add(actionNameTextField);
        mainPanel.add(objectTypeComboBox);
        mainPanel.add(objectNameTextField);
        mainPanel.add(removeObjectButton);
        mainPanel.add(addObjectButton);
        mainPanel.add(removeSuggestionButton);
        panelCenter.add(mainPanel);
    }
}




