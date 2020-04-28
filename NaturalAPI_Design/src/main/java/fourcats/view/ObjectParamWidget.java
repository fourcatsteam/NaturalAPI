package fourcats.view;

import fourcats.interfaceadapters.Controller;
import fourcats.interfaceadapters.DataPresenterGUI;

import javax.swing.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;

public class ObjectParamWidget{
    private JComboBox objectTypeComboBox;
    private JTextField objectNameTextField;
    private JPanel mainPanel;
    private JButton removeObjectButton;
    private static final String CREATE_CUSTOM = "CREATE CUSTOM";

    public ObjectParamWidget(Box boxToUpdate, Controller contr, DataPresenterGUI dataPresenter, String objectId,
                             String objectType, String objectName, String suggestionId, String scenarioId){
        mainPanel = new JPanel();
        mainPanel.add(objectTypeComboBox);
        mainPanel.add(objectNameTextField);
        mainPanel.add(removeObjectButton);

        objectTypeComboBox.setSelectedItem(objectType);
        objectNameTextField.setText(objectName);

        boxToUpdate.add(mainPanel);
        boxToUpdate.revalidate();
        boxToUpdate.repaint();

        removeObjectButton.addActionListener(e->{
            contr.removeObject(suggestionId,scenarioId,
                    objectId);
            JOptionPane.showMessageDialog(null,dataPresenter.getMessage());
            boxToUpdate.remove(mainPanel);
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
                    new CustomTypeCreation(contr);
                }
                else{
                    contr.modifyObjectType(suggestionId,scenarioId,
                            objectId,objectTypeComboBox.getSelectedItem().toString());
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
}


