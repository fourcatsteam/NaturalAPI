package fourcats.view.gui;

import fourcats.interfaceadapters.Controller;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;


public class CustomTypeCreation extends Component{

    public CustomTypeCreation(Controller controller, List<String> lAvailableTypes){
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.PAGE_AXIS));
        mainPanel.setBorder(new EmptyBorder(5,5,5,5));

        JFrame frame = new JFrame("Create custom type");
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.add(mainPanel);
        frame.setPreferredSize(new Dimension(400,350));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        Map<String,String> mAttributes = new HashMap<>();
        List<AttributeWidget> lAttributeWidgets = new ArrayList<>(); //contains attributeWidgets instance
        JTextField customTypeNameField = new JTextField();
        customTypeNameField.setPreferredSize(new Dimension(Integer.MAX_VALUE,25));
        customTypeNameField.setMaximumSize(new Dimension(Integer.MAX_VALUE,40)); //set max height for the field
        JButton addAttributeButton = new JButton("+");
        JLabel customTypeNameLabel = new JLabel("Custom type name:");
        JButton okButton = new JButton("OK");
        JButton cancelButton = new JButton("Cancel");


        Box okCancelBtnBox = Box.createHorizontalBox();
        okCancelBtnBox.add(okButton);
        okCancelBtnBox.add(Box.createHorizontalStrut(10)); //space horizontally the two buttons
        okCancelBtnBox.add(cancelButton);

        Box attributeBox = Box.createVerticalBox();
        AttributeWidget attributeWidget = new AttributeWidget(lAvailableTypes);
        lAttributeWidgets.add(attributeWidget); //add to attribute widgets list
        attributeBox.add(attributeWidget.getAttributeWidgetPanel()); //add to panel to show widget

        Box labelBox = Box.createHorizontalBox();
        labelBox.add(customTypeNameLabel);
        labelBox.add(Box.createHorizontalGlue()); //push the label to the left of the box
        mainPanel.add(labelBox);
        mainPanel.add(customTypeNameField);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(attributeBox);
        mainPanel.add(Box.createVerticalStrut(10)); //space addAttributeButton from the fields
        mainPanel.add(addAttributeButton);
        mainPanel.add(Box.createVerticalGlue()); //this allow push the ok_cancel buttons at the bottom of the panel
        mainPanel.add(okCancelBtnBox);
        mainPanel.add(Box.createVerticalStrut(10)); //space ok_cancel buttons from bottom

        addAttributeButton.addActionListener(e->{
            AttributeWidget attrWidget = new AttributeWidget(lAvailableTypes);
            lAttributeWidgets.add(attrWidget); //add to attrWidget list
            attributeBox.add(attrWidget.getAttributeWidgetPanel());
            attributeBox.revalidate();
            attributeBox.repaint();
        });

        okButton.addActionListener(e->{
            //check for customType name
            if (!customTypeNameField.getText().equals("")){
                for (AttributeWidget attrW : lAttributeWidgets){
                    if (!attrW.getAttributeName().equals("")){
                        mAttributes.put(attrW.getAttributeName(),attrW.getAttributeType());
                    }
                }
                if (mAttributes.size()!=0){
                    controller.createCustomType(customTypeNameField.getText(),mAttributes);
                    frame.dispose();
                    return;
                }
                JOptionPane.showMessageDialog(null,"Custom types should have at least one attribute", "No attributes", JOptionPane.WARNING_MESSAGE);
            }
            else {
                JOptionPane.showMessageDialog(null, "Please enter the name for the custom type", "No name given", JOptionPane.WARNING_MESSAGE);
            }

        });

        cancelButton.addActionListener(e->frame.dispose())
        ;
    }
}
