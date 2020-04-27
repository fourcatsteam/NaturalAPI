package fourcats.view;

import fourcats.interfaceadapters.Controller;
import fourcats.interfaceadapters.DataPresenterGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;


public class SuggestionWidget extends JComponent {
    private JComboBox actionTypeComboBox;
    private JTextField actionNameTextField;
    private JComboBox objectTypeComboBox;
    private JTextField objectNameTextField;
    private JButton addObjectButton;
    private JButton removeSuggestionButton;
    private JButton removeObjectButton;
    private JPanel mainPanel;
    private String actionId;
    private String actionType;
    private String actionName;
    private String objectId;
    private String objectType;
    private String objectName;
    private String suggestionId;
    private String scenarioId;
    private final String CREATE_CUSTOM = "CREATE CUSTOM";

    public SuggestionWidget(JPanel panelToUpdate, Controller contr, DataPresenterGUI dataPresenter){
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.LINE_AXIS));

        getValuesFromDataPresenter(dataPresenter);

        actionTypeComboBox.setSelectedItem(dataPresenter.getActionType());
        actionTypeComboBox.add(Box.createHorizontalBox());
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

        panelToUpdate.add(mainPanel);


        removeSuggestionButton.addActionListener(e -> {
            if (JOptionPane.showConfirmDialog(null,"Are you sure you want to remove this suggestion?",
                    "Suggestion remove confirmation", JOptionPane.WARNING_MESSAGE) == 0) {
                //0=yes
                contr.declineSuggestion(suggestionId,scenarioId);
                mainPanel.setVisible(false);
            }
        });

        removeObjectButton.addActionListener(e->{
            contr.removeObject(suggestionId,scenarioId,objectId);
            JOptionPane.showMessageDialog(null,dataPresenter.getMessage());
            objectTypeComboBox.setVisible(false);
            objectNameTextField.setVisible(false);
        });

        actionTypeComboBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED){
                if(actionTypeComboBox.getSelectedItem().equals(CREATE_CUSTOM)){
                    createCustomType(contr);
                }
                else{
                    contr.modifyActionType(suggestionId,scenarioId,actionTypeComboBox.getSelectedItem().toString());
                }
            }
        });

        actionNameTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                contr.modifyActionName(suggestionId,scenarioId,actionNameTextField.getText());
            }
        });

        actionTypeComboBox.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                contr.showTypes();
                actionTypeComboBox.removeAllItems();
                actionTypeComboBox.addItem("void");
                for (String type : dataPresenter.getlTypes()){
                    actionTypeComboBox.addItem(type);
                }
                actionTypeComboBox.addItem(CREATE_CUSTOM);
            }
        });

        objectTypeComboBox.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                contr.showTypes();
                objectTypeComboBox.removeAllItems();
                for (String type : dataPresenter.getlTypes()){
                    objectTypeComboBox.addItem(type);
                }
                objectTypeComboBox.addItem(CREATE_CUSTOM);
            }
        });
        objectTypeComboBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED){
                if(objectTypeComboBox.getSelectedItem().equals(CREATE_CUSTOM)){
                    createCustomType(contr);
                }
                else{
                    contr.modifyObjectType(suggestionId,scenarioId,objectId,objectTypeComboBox.getSelectedItem().toString());
                }
            }
        });

        objectNameTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                contr.modifyObjectName(suggestionId,scenarioId,objectId,objectNameTextField.getText());
            }
        });
    }

    private void getValuesFromDataPresenter(DataPresenterGUI dataPresenter){
        this.actionId = dataPresenter.getActionId();
        this.actionType = dataPresenter.getActionType();
        this.actionName = dataPresenter.getActionName();
        this.objectId = dataPresenter.getObjectId();
        this.objectType = dataPresenter.getObjectType();
        this.objectName = dataPresenter.getObjectName();
        this.scenarioId = dataPresenter.getScenarioId();
        this.suggestionId = dataPresenter.getSuggestionId();
    }

    private void createCustomType(Controller controller){
        Map<String,String> mAttributes = new HashMap<>();

        JPanel fields = new JPanel(new GridLayout(5, 1));
        JLabel typeNameLabel = new JLabel();
        JLabel attributeNameLabel = new JLabel();
        JTextField typeNameField = new JTextField();
        JTextField attributeNameField = new JTextField();
        JButton addAttributeButton = new JButton();
        addAttributeButton.setText("+");
        typeNameLabel.setText("Type name:");
        attributeNameLabel.setText("Attribute name:");
        JComboBox<String> attributeTypeComboBox = new JComboBox<>(new String[]{"string","int","float","double","bool"});

        fields.add(typeNameLabel);
        fields.add(typeNameField);
        fields.add(attributeNameLabel);
        JPanel attributePanel = new JPanel(new GridLayout(1,2));
        attributePanel.add(attributeTypeComboBox);
        attributePanel.add(attributeNameField);
        fields.add(attributePanel);
        fields.add(addAttributeButton);

        addAttributeButton.addActionListener(e->{
            mAttributes.put(attributeNameField.getText(),attributeTypeComboBox.getSelectedItem().toString());
            attributeTypeComboBox.setSelectedIndex(0);
            attributeNameField.setText("");
        });

        int result = JOptionPane.showConfirmDialog(null, fields, "Create custom type", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            if (!attributeNameField.getText().equals("")){
                mAttributes.put(attributeNameField.getText(),attributeTypeComboBox.getSelectedItem().toString());
            }
            if (mAttributes.size()!=0){
                controller.createCustomType(typeNameField.getText(),mAttributes);
            }
            else{
                JOptionPane.showMessageDialog(null,"Abort: custom type should have at least one attribute", "Error custom type", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

}




