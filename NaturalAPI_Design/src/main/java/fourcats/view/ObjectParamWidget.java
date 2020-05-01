package fourcats.view;

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
        objectNameTextField.setPreferredSize(new Dimension(100,20));
        suggWidget.addObjectParamWidget(mainPanel);

        removeObjectButton.addActionListener(e->{
            contr.removeObject(suggestionId,scenarioId,
                    objectId);
            if (dataPresenter.isOkOperation()){
                suggWidget.removeObjectParamWidget(this,mainPanel);
            }

        });

        objectTypeComboBox.addItemListener(e->{
            if (e.getStateChange() == ItemEvent.SELECTED && objectTypeComboBox.getSelectedItem()!=null){
                if(objectTypeComboBox.getSelectedItem().toString().equals(CREATE_CUSTOM)){
                    new CustomTypeCreation(contr,dataPresenter.getlTypes());
                }
            }
        });

        objectTypeComboBox.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                objectTypeComboBox.removeAllItems();
                contr.showTypes();
                for (String type : dataPresenter.getlTypes()){
                    objectTypeComboBox.addItem(type);
                }
                objectTypeComboBox.addItem(CREATE_CUSTOM);
            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if (objectTypeComboBox.getSelectedItem()!=null && !objectTypeComboBox.getSelectedItem().toString().equals(CREATE_CUSTOM)) {
                    contr.modifyObjectType(suggestionId, scenarioId,
                            objectId, objectTypeComboBox.getSelectedItem().toString());
                }
            }
        });

      /*  objectNameTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                contr.modifyObjectName(suggestionId,scenarioId,
                        objectId,objectNameTextField.getText());
            }
        });*/

        objectNameTextField.getDocument().addDocumentListener(new DocumentListener() {

            public void changedUpdate(DocumentEvent e) {
                setNewName();
                if(dataPresenter.isBdlLoaded()) setColor();
            }
            public void removeUpdate(DocumentEvent e) {
                setNewName();
                if(dataPresenter.isBdlLoaded()) setColor();
            }
            public void insertUpdate(DocumentEvent e) {
                setNewName();
                if(dataPresenter.isBdlLoaded()) setColor();
            }

            public void setColor() {
                setObjectNameColor(dataPresenter.isPresentInBdl());
            }

            public void setNewName() {
                contr.modifyObjectName(suggestionId,scenarioId,objectId,objectNameTextField.getText());
            }
        });



    }

    public int getObjectId(){
        return Integer.parseInt(this.objectId);
    }
    public void setObjectId(int updatedObjectId){
        this.objectId = Integer.toString(updatedObjectId);
    }

    private void setObjectNameColor(boolean isPresentInBdl){
        if(isPresentInBdl) { //Se presente nella BDL posso metterlo di colore verde
            objectNameTextField.setForeground(Color.GREEN);
        }else{
            objectNameTextField.setForeground(Color.RED);
        }
    }

}


