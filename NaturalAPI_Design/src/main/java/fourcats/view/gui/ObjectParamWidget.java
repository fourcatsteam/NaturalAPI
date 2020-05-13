package fourcats.view.gui;

import fourcats.interfaceadapters.Controller;
import fourcats.interfaceadapters.DataPresenterGUI;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;

public class ObjectParamWidget {
    private JComboBox<String> objectTypeComboBox;
    private JTextField objectNameTextField;
    private JPanel mainPanel;
    private JButton removeObjectButton;
    private JLabel frequencyLabel;
    private String objectId;
    private CustomTypeCreation customType;
    private String currentObjectName; //updated with the one in dataKeeper
    private static final String CREATE_CUSTOM = "CREATE CUSTOM";
    private static final String WORD_FREQUENCY = "Word Frequency: ";


    public ObjectParamWidget(SuggestionWidget suggWidget, Controller contr, DataPresenterGUI dataPresenter, String initialObjectId,
                             String objectType, String objectName, String suggestionId, String scenarioId) {
        this.objectId = initialObjectId;
        mainPanel = new JPanel();
        mainPanel.add(objectTypeComboBox);
        mainPanel.add(objectNameTextField);

        if(dataPresenter.isBdlLoaded()) {
            mainPanel.add(frequencyLabel);
            frequencyLabel.setText(WORD_FREQUENCY + dataPresenter.getWordObjectFrequency(objectName));
            setObjectNameColor(dataPresenter.getWordObjectFrequency(objectName));
        }
        mainPanel.add(removeObjectButton);
        objectTypeComboBox.setSelectedItem(objectType);
        objectNameTextField.setText(objectName);
        objectNameTextField.setPreferredSize(new Dimension(100, 20));
        suggWidget.addObjectParamWidget(mainPanel);



        removeObjectButton.addActionListener(e -> {
            contr.removeObject(suggestionId, scenarioId,
                    objectId);
            if (dataPresenter.isOkOperation()) {
                suggWidget.removeObjectParamWidget(this, mainPanel);
            }

        });

        objectTypeComboBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED &&
                    objectTypeComboBox.getSelectedItem() != null &&
                    objectTypeComboBox.getSelectedItem().toString().equals(CREATE_CUSTOM)) {
                customType = new CustomTypeCreation(contr, dataPresenter.getlTypes());
            }
        });

        objectTypeComboBox.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                objectTypeComboBox.removeAllItems();
                contr.showTypes();
                for (String type : dataPresenter.getlTypes()) {
                    objectTypeComboBox.addItem(type);
                }
                //check if the selected item should be the custom type recently created
                if (customType!=null && customType.isCustomTypeCreated()){
                    objectTypeComboBox.setSelectedIndex(objectTypeComboBox.getItemCount()-1);
                }
                objectTypeComboBox.addItem(CREATE_CUSTOM);
            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if (objectTypeComboBox.getSelectedItem() != null && !objectTypeComboBox.getSelectedItem().toString().equals(CREATE_CUSTOM)) {
                    contr.modifyObjectType(suggestionId, scenarioId,
                            objectId, objectTypeComboBox.getSelectedItem().toString());
                }
            }
        });

        objectNameTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                currentObjectName = objectNameTextField.getText();
            }
            @Override
            public void focusLost(FocusEvent e) {
                objectNameTextField.setText(currentObjectName.replace(' ','_'));
                if (objectNameTextField.getText().equals("")){
                    JOptionPane.showMessageDialog(null,"You have to enter the name of the object","Empty field", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        objectNameTextField.getDocument().addDocumentListener(new DocumentListener() {

            public void changedUpdate(DocumentEvent e) {
                setNewName();
                if (dataPresenter.isBdlLoaded()) setColor();
            }

            public void removeUpdate(DocumentEvent e) {
                setNewName();
                if (dataPresenter.isBdlLoaded()) setColor();
            }

            public void insertUpdate(DocumentEvent e) {
                setNewName();
                if (dataPresenter.isBdlLoaded()) setColor();
            }

            public void setColor() {
                setObjectNameColor(dataPresenter.getFrequencyInBdl());
            }

            public void setNewName() {
                //check that the current objectParam really differs from the one inserted
                if (!objectNameTextField.getText().trim().equals(currentObjectName)) {
                    contr.modifyObjectName(suggestionId, scenarioId, objectId, objectNameTextField.getText());
                    //this is needed because the text in the field could be different from the one in dataPresenter if an error occurs
                    if (dataPresenter.isOkOperation())
                        currentObjectName = objectNameTextField.getText().trim();
                }
            }
        });
    }

    public int getObjectId(){
        return Integer.parseInt(this.objectId);
    }
    public void setObjectId(int updatedObjectId){
        this.objectId = Integer.toString(updatedObjectId);
    }


    private void setObjectNameColor(int isPresentInBdl){
        objectNameTextField.setFont(new Font("",Font.BOLD,12));
        if(isPresentInBdl!=0) { //if present in BDL then set text color to green
            objectNameTextField.setForeground(new Color(9,148,65));
        }else{
            objectNameTextField.setForeground(Color.RED);
        }
        frequencyLabel.setText(WORD_FREQUENCY+isPresentInBdl);
    }

}


