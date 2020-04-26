package fourcats.view;

import fourcats.interfaceadapters.Controller;
import fourcats.interfaceadapters.DataPresenterGUI;

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

    public SuggestionWidget(JPanel panelCenter, Controller contr, DataPresenterGUI dataPresenter){
        mainPanel = new JPanel();
        actionTypeComboBox.setSelectedItem(dataPresenter.getActionType());
        actionNameTextField.setText(dataPresenter.getActionName());
        objectTypeComboBox.setSelectedItem(dataPresenter.getObjectType());
        objectNameTextField.setText(dataPresenter.getObjectName());
        mainPanel.add(actionTypeComboBox);
        mainPanel.add(actionNameTextField);
        mainPanel.add(objectTypeComboBox);
        mainPanel.add(objectNameTextField);
        mainPanel.add(removeObjectButton);
        mainPanel.add(addObjectButton);
        mainPanel.add(removeSuggestionButton);
        panelCenter.add(mainPanel);


        removeSuggestionButton.addActionListener(e -> {
            contr.declineSuggestion(dataPresenter.getSuggestionId(),dataPresenter.getScenarioId());
            JOptionPane.showMessageDialog(null,dataPresenter.getMessage());
            mainPanel.setVisible(false);
        });
    }
}




