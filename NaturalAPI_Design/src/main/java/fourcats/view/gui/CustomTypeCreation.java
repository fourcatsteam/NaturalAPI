package fourcats.view.gui;

import com.intellij.uiDesigner.core.GridLayoutManager;
import fourcats.interfaceadapters.Controller;
import fourcats.view.utilities.ViewUtility;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;


public class CustomTypeCreation {
    private boolean isCustomTypeCreated;

    public CustomTypeCreation(Controller controller, List<String> lAvailableTypes) {
        isCustomTypeCreated = false;
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
        mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

        JFrame frame = new JFrame("Create custom type");
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.add(mainPanel);
        frame.setPreferredSize(new Dimension(400, 380));
        frame.setIconImage(ViewUtility.getLogo());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        Map<String, String> mAttributes = new HashMap<>();
        List<AttributeWidget> lAttributeWidgets = new ArrayList<>(); //contains attributeWidgets instance
        JTextField customTypeNameField = new JTextField();
        customTypeNameField.setPreferredSize(new Dimension(Integer.MAX_VALUE, 25));
        customTypeNameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40)); //set max height for the field
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

        Box noticeVertBox = Box.createVerticalBox();
        noticeVertBox.add(new JLabel("------NOTICE: All attribute names must be unique------"));
        noticeVertBox.add(new JLabel("Otherwise, only the last one entered will be retained."));
        Box noticeHorBox = Box.createHorizontalBox();
        noticeHorBox.add(Box.createHorizontalGlue()); //* push from left
        noticeHorBox.add(noticeVertBox); //center aligned
        noticeHorBox.add(Box.createHorizontalGlue()); //* push from right
        mainPanel.add(noticeHorBox);
        mainPanel.add(Box.createVerticalStrut(10));

        mainPanel.add(attributeBox);
        mainPanel.add(Box.createVerticalStrut(10)); //space addAttributeButton from the fields
        Box addAttrBtnBox = Box.createHorizontalBox();
        addAttrBtnBox.add(Box.createHorizontalGlue()); //* push from left
        addAttrBtnBox.add(addAttributeButton); //center aligned
        addAttrBtnBox.add(Box.createHorizontalGlue()); //* push from right
        mainPanel.add(addAttrBtnBox);
        mainPanel.add(Box.createVerticalGlue()); //this allow push the ok_cancel buttons at the bottom of the panel
        mainPanel.add(okCancelBtnBox);
        mainPanel.add(Box.createVerticalStrut(10)); //space ok_cancel buttons from bottom

        addAttributeButton.addActionListener(e -> {
            AttributeWidget attrWidget = new AttributeWidget(lAvailableTypes);
            lAttributeWidgets.add(attrWidget); //add to attrWidget list
            attributeBox.add(attrWidget.getAttributeWidgetPanel());
            attributeBox.revalidate();
            attributeBox.repaint();
        });

        okButton.addActionListener(e -> {
            //check for customType name
            if (!customTypeNameField.getText().equals("")) {
                for (AttributeWidget attrW : lAttributeWidgets) {
                    if (!attrW.getAttributeName().equals("")) {
                        String attrName = attrW.getAttributeName().trim().replace(' ', '_');
                        if (Character.isDigit(attrName.charAt(0))) attrName = "_" + attrName;
                        mAttributes.put(attrName, attrW.getAttributeType());
                    }
                }
                controller.createCustomType(customTypeNameField.getText().trim().replace(' ', '_'), mAttributes);
                isCustomTypeCreated = true;
                frame.dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Please enter the name for the custom type", "No name given", JOptionPane.WARNING_MESSAGE);
            }
        });
        cancelButton.addActionListener(e -> frame.dispose());
    }

    public boolean isCustomTypeCreated() {
        return isCustomTypeCreated;
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
    }
}
