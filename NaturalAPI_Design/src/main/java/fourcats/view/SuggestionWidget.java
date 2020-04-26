package fourcats.view;

import fourcats.interfaceadapters.Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SuggestionWidget extends JComponent {
    private JComboBox actionTypeComboBox;
    private JTextField actionNameTextField;
    private JComboBox objectTypeComboBox;
    private JTextField objectNameTextField;
    private JButton addObjectButton;
    private JButton removeSuggestionButton;
    private JButton removeObjectButton;
    private JPanel mainPanel;

    public SuggestionWidget(JPanel panelCenter, Controller contr, String actionType, String actionName, String objectId,
                            String objectType, String objectName, String scenarioId, String suggestionId){
        mainPanel = new JPanel();
        actionTypeComboBox.setSelectedItem(actionType);
        actionNameTextField.setText(actionName);
        objectTypeComboBox.setSelectedItem(objectType);
        objectNameTextField.setText(objectName);
        mainPanel.add(actionTypeComboBox);
        mainPanel.add(actionNameTextField);
        mainPanel.add(objectTypeComboBox);
        mainPanel.add(objectNameTextField);
        mainPanel.add(removeObjectButton);
        mainPanel.add(addObjectButton);
        mainPanel.add(removeSuggestionButton);
        panelCenter.add(mainPanel);


        removeSuggestionButton.addActionListener(e -> {
            contr.declineSuggestion(suggestionId,scenarioId);
            JOptionPane.showMessageDialog(null,"Suggestion removed! (TODO Find the way to refresh the list)");
            //TODO
        });
    }
}




