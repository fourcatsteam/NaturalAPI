package fourcats.view.gui;

import fourcats.interfaceadapters.Controller;
import fourcats.interfaceadapters.DataPresenterGUI;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Objects;

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

        if (dataPresenter.isBdlLoaded()) {
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

        objectTypeComboBox.addActionListener(e -> {
            if (Objects.equals(objectTypeComboBox.getSelectedItem(), CREATE_CUSTOM)) {
                customType = new CustomTypeCreation(contr, dataPresenter.getlTypes());
            }
        });

        objectTypeComboBox.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                ArrayList<String> currentItems = new ArrayList();
                // itemCount-1 because last item is the CREATE CUSTOM option (not present in dataKeeper)
                for (int i = 0; i < objectTypeComboBox.getItemCount() - 1; i++) {
                    currentItems.add(objectTypeComboBox.getItemAt(i));
                }
                contr.showTypes();
                //update items if currentItems are different from the ones given by the dataPresenter
                if (!dataPresenter.getlTypes().equals(currentItems)) {
                    objectTypeComboBox.removeAllItems();
                    for (String type : dataPresenter.getlTypes()) {
                        objectTypeComboBox.addItem(type);
                    }
                    //check if the selected item should be the custom type recently created
                    if (customType != null && customType.isCustomTypeCreated()) {
                        objectTypeComboBox.setSelectedIndex(objectTypeComboBox.getItemCount() - 1);
                    }
                    objectTypeComboBox.addItem(CREATE_CUSTOM);
                }
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
                objectNameTextField.setText(currentObjectName.replace(' ', '_'));
                if (objectNameTextField.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "You have to enter the name of the object", "Empty field", JOptionPane.WARNING_MESSAGE);
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
                    if (!currentObjectName.equals("") && Character.isDigit(currentObjectName.charAt(0)))
                        currentObjectName = "_" + currentObjectName;
                }
            }
        });
    }

    public int getObjectId() {
        return Integer.parseInt(this.objectId);
    }

    public void setObjectId(int updatedObjectId) {
        this.objectId = Integer.toString(updatedObjectId);
    }


    private void setObjectNameColor(int isPresentInBdl) {
        objectNameTextField.setFont(new Font("", Font.BOLD, 12));
        if (isPresentInBdl != 0) { //if present in BDL then set text color to green
            objectNameTextField.setForeground(new Color(9, 148, 65));
        } else {
            objectNameTextField.setForeground(Color.RED);
        }
        frequencyLabel.setText(WORD_FREQUENCY + isPresentInBdl);
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
        mainPanel = new JPanel();
        mainPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 4, new Insets(0, 0, 0, 0), -1, -1));
        objectTypeComboBox = new JComboBox();
        objectTypeComboBox.setBackground(new Color(-1));
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("string");
        defaultComboBoxModel1.addElement("int");
        defaultComboBoxModel1.addElement("float");
        defaultComboBoxModel1.addElement("double");
        defaultComboBoxModel1.addElement("bool");
        defaultComboBoxModel1.addElement("CREATE CUSTOM");
        objectTypeComboBox.setModel(defaultComboBoxModel1);
        mainPanel.add(objectTypeComboBox, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        objectNameTextField = new JTextField();
        objectNameTextField.setBackground(new Color(-1));
        mainPanel.add(objectNameTextField, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        removeObjectButton = new JButton();
        Font removeObjectButtonFont = this.$$$getFont$$$(null, -1, -1, removeObjectButton.getFont());
        if (removeObjectButtonFont != null) removeObjectButton.setFont(removeObjectButtonFont);
        removeObjectButton.setText("-");
        mainPanel.add(removeObjectButton, new com.intellij.uiDesigner.core.GridConstraints(0, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        frequencyLabel = new JLabel();
        frequencyLabel.setText("");
        mainPanel.add(frequencyLabel, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        return new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return mainPanel;
    }
}


