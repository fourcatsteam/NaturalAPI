package fourcats.view.gui;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.List;

public class AttributeWidget {
    private JPanel mainPanel;
    private JComboBox<String> attributeTypeComboBox;
    private JTextField attributeNameField;
    private JButton removeAttributeButton;
    private String attrName;
    private String attrType;

    public AttributeWidget(List<String> lAvailableTypes){
        this.attrName = "";
        this.attrType = "string";
        attributeNameField.setMaximumSize(new Dimension(Integer.MAX_VALUE,30));
        attributeTypeComboBox.setMaximumSize(new Dimension(Integer.MAX_VALUE/2,30));
        mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.LINE_AXIS));
        mainPanel.add(attributeTypeComboBox);
        mainPanel.add(attributeNameField);
        mainPanel.add(removeAttributeButton);

        //populate types combo box
        for (String type : lAvailableTypes){
            attributeTypeComboBox.addItem(type);
        }

        removeAttributeButton.addActionListener(e->{
            attributeTypeComboBox.setSelectedItem("");
            attributeNameField.setText("");
            mainPanel.setVisible(false);
        });

        attributeTypeComboBox.addActionListener(e->
                attrType = attributeTypeComboBox.getSelectedItem().toString()
        );

        attributeNameField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                setAttrName();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                setAttrName();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                setAttrName();
            }
            private void setAttrName(){
                attrName = attributeNameField.getText();
            }
        });

    }

    public JPanel getAttributeWidgetPanel(){
        return mainPanel;
    }
    public String getAttributeName(){
        return attrName;
    }
    public String getAttributeType(){
        return attrType;
    }
}


