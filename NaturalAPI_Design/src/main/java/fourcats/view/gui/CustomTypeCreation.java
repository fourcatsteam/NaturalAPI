package fourcats.view.gui;

import fourcats.interfaceadapters.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.List;
import java.util.HashMap;
import java.util.Map;


public class CustomTypeCreation {
    public CustomTypeCreation(Controller controller, List<String> lAvailableTypes){
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
            if (!attributeNameField.getText().equals("")) {
                mAttributes.put(attributeNameField.getText(), attributeTypeComboBox.getSelectedItem().toString());
                attributeTypeComboBox.setSelectedIndex(0);
                attributeNameField.setText("");
            }
        });

        attributeTypeComboBox.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                attributeTypeComboBox.removeAllItems();
                for (String type : lAvailableTypes) {
                    attributeTypeComboBox.addItem(type);
                }
            }
        });

        int result = JOptionPane.showConfirmDialog(null, fields, "Create custom type", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            if (!attributeNameField.getText().equals("")){
                mAttributes.put(attributeNameField.getText(),attributeTypeComboBox.getSelectedItem().toString());
            }
            if (!typeNameField.getText().equals("")){
                if (mAttributes.size()!=0){
                    controller.createCustomType(typeNameField.getText(),mAttributes);
                }
                else{
                    JOptionPane.showMessageDialog(null,"Abort: custom types should have at least one attribute", "Error custom type", JOptionPane.ERROR_MESSAGE);
                }
            }
            else {
                JOptionPane.showMessageDialog(null, "Abort: no name given for the custom type", "Error custom type", JOptionPane.ERROR_MESSAGE);
            }

        }
    }
}
