package fourcats.view;

import fourcats.interfaceadapters.Controller;
import fourcats.interfaceadapters.DataPresenterGUI;

import javax.swing.*;
import java.awt.event.*;

public class ObjectParamWidget{
    private JComboBox<String> objectTypeComboBox;
    private JTextField objectNameTextField;
    private JPanel mainPanel;
    private JButton removeObjectButton;
    private String objectId;
    private static final String CREATE_CUSTOM = "CREATE CUSTOM";

    public ObjectParamWidget(SuggestionWidget suggWidget, Controller contr, DataPresenterGUI dataPresenter, String initialObjectId,
                             String objectType, String objectName, String suggestionId, String scenarioId){
        this.objectId = initialObjectId;
        mainPanel = new JPanel();
        mainPanel.add(objectTypeComboBox);
        mainPanel.add(objectNameTextField);
        mainPanel.add(removeObjectButton);

        objectTypeComboBox.setSelectedItem(objectType);
        objectNameTextField.setText(objectName);

        suggWidget.addObjectParamWidget(mainPanel);

        removeObjectButton.addActionListener(e->{
            contr.removeObject(suggestionId,scenarioId,
                    objectId);
            if (dataPresenter.isOkOperation()){
                suggWidget.removeObjectParamWidget(this,mainPanel);
            }
            JOptionPane.showMessageDialog(null,dataPresenter.getMessage());
        });


        objectTypeComboBox.addItemListener(e->{
            if (e.getStateChange() == ItemEvent.SELECTED && objectTypeComboBox.getSelectedItem()!=null){
                if(objectTypeComboBox.getSelectedItem().toString().equals(CREATE_CUSTOM)){
                    new CustomTypeCreation(contr);
                }
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

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if (objectTypeComboBox.getSelectedItem()!=null) {
                    contr.modifyObjectType(suggestionId, scenarioId,
                            objectId, objectTypeComboBox.getSelectedItem().toString());
                }
                if (!dataPresenter.isOkOperation()){
                    JOptionPane.showMessageDialog(null,dataPresenter.getMessage(),"Error!",JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        objectNameTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                contr.modifyObjectName(suggestionId,scenarioId,
                        objectId,objectNameTextField.getText());
            }
        });
    }

    public int getObjectId(){
        return Integer.parseInt(this.objectId);
    }
    public void setObjectId(int updatedObjectId){
        this.objectId = Integer.toString(updatedObjectId);
    }
}


