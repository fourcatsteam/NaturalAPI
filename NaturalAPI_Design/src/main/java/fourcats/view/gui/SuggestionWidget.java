package fourcats.view.gui;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import fourcats.interfaceadapters.Controller;
import fourcats.interfaceadapters.DataPresenterGUI;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Objects;


public class SuggestionWidget {
    private JComboBox<String> actionTypeComboBox;
    private JTextField actionNameTextField;
    private JButton addObjectButton;
    private JButton removeSuggestionButton;
    private JPanel mainPanel;
    private JLabel frequencyLabel;
    private final String suggestionId;
    private final String scenarioId;
    private final Box objectsBox;
    private final ArrayList<ObjectParamWidget> lObjectParamWidget;
    private static final String CREATE_CUSTOM = "CREATE CUSTOM";
    private static final String WORD_FREQUENCY = "Word Frequency: ";
    private CustomTypeCreation customType;
    private String currentActionName; //updated with the one in dataKeeper

    public SuggestionWidget(JPanel panelToUpdate, Controller contr, DataPresenterGUI dataPresenter) {
        lObjectParamWidget = new ArrayList<>();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.LINE_AXIS));
        objectsBox = Box.createVerticalBox();

        this.scenarioId = dataPresenter.getScenarioId();
        this.suggestionId = dataPresenter.getSuggestionId();

        //this is needed because setSelectedItem won't work if the item is not present
        if (!dataPresenter.getActionType().equals("void")) {
            initTypeComboBox(dataPresenter);
        }
        actionTypeComboBox.setSelectedItem(dataPresenter.getActionType());
        actionTypeComboBox.add(Box.createHorizontalBox());
        actionNameTextField.setText(dataPresenter.getActionName());

        mainPanel.add(actionTypeComboBox);
        mainPanel.add(actionNameTextField);
        if (dataPresenter.isBdlLoaded()) {
            mainPanel.add(frequencyLabel);
            frequencyLabel.setText(WORD_FREQUENCY + dataPresenter.getFrequencyInBdl());
            setActionNameColor(dataPresenter.getFrequencyInBdl());
        }

        mainPanel.add(objectsBox);

        addObjects(contr, dataPresenter);

        mainPanel.add(addObjectButton);
        mainPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        mainPanel.add(removeSuggestionButton);
        panelToUpdate.add(mainPanel);

        addObjectButton.addActionListener(e -> {
            String objectName = JOptionPane.showInputDialog(null, "Insert the name for the parameter",
                    "Parameter creation", JOptionPane.QUESTION_MESSAGE).trim();
            if (!objectName.equals("")) {
                if (Character.isDigit(objectName.charAt(0))) objectName = "_" + objectName;
                contr.addObject(suggestionId, scenarioId, objectName, "0");
                if (dataPresenter.isOkOperation()) {
                    lObjectParamWidget.add(new ObjectParamWidget(this, contr, dataPresenter, Integer.toString(lObjectParamWidget.size()),
                            "string", objectName, suggestionId, scenarioId));
                }
            } else {
                JOptionPane.showMessageDialog(null, "Abort: no name entered", "Invalid name", JOptionPane.WARNING_MESSAGE);
            }
        });

        removeSuggestionButton.addActionListener(e -> {
            if (JOptionPane.showConfirmDialog(null, "Are you sure you want to remove this suggestion?",
                    "Suggestion remove confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == 0) {
                //0=yes
                contr.declineSuggestion(suggestionId, scenarioId);
                panelToUpdate.remove(mainPanel);
                panelToUpdate.revalidate();
                panelToUpdate.repaint();
            }
        });

        actionTypeComboBox.addActionListener(e -> {
            if (Objects.equals(actionTypeComboBox.getSelectedItem(), CREATE_CUSTOM)) {
                customType = new CustomTypeCreation(contr, dataPresenter.getlTypes());
            }
        });

        actionTypeComboBox.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                ArrayList<String> currentItems = new ArrayList();
                // i = 1 because 0 is void (not present in dataKeeper),
                // itemCount-1 because last item is the CREATE CUSTOM option (not present in dataKeeper)
                for (int i = 1; i < actionTypeComboBox.getItemCount() - 1; i++) {
                    currentItems.add(actionTypeComboBox.getItemAt(i));
                }
                contr.showTypes();
                //update items if currentItems are different from the ones given by the dataPresenter
                if (!dataPresenter.getlTypes().equals(currentItems)) {
                    initTypeComboBox(dataPresenter);
                    //check if the selected item should be the custom type recently created
                    if (customType != null && customType.isCustomTypeCreated()) {
                        actionTypeComboBox.setSelectedIndex(actionTypeComboBox.getItemCount() - 2);
                    }
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if (actionTypeComboBox.getSelectedItem() != null && !actionTypeComboBox.getSelectedItem().toString().equals(CREATE_CUSTOM)) {
                    contr.modifyActionType(suggestionId, scenarioId, actionTypeComboBox.getSelectedItem().toString());
                }
            }
        });

        actionNameTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                currentActionName = actionNameTextField.getText();
            }

            @Override
            public void focusLost(FocusEvent e) {
                actionNameTextField.setText(currentActionName.replace(' ', '_'));
                if (actionNameTextField.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "You have to enter the name of the action", "Empty field", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        actionNameTextField.getDocument().addDocumentListener(new DocumentListener() {
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
                setActionNameColor(dataPresenter.getFrequencyInBdl());
            }

            public void setNewName() {
                //check that the current action really differs from the one inserted
                if (!actionNameTextField.getText().trim().equals(currentActionName)) {
                    contr.modifyActionName(suggestionId, scenarioId, actionNameTextField.getText());
                    //this is needed because the text in the field could be different from the one in dataPresenter if an error occurs
                    if (dataPresenter.isOkOperation()) {
                        currentActionName = actionNameTextField.getText().trim();
                        if (!currentActionName.equals("") && Character.isDigit(currentActionName.charAt(0)))
                            currentActionName = "_" + currentActionName;
                    }
                }
            }
        });
    }

    private void addObjects(Controller controller, DataPresenterGUI dataPresenter) {
        //init suggested objects
        for (String objId : dataPresenter.getlObjectId()) {
            int id = lObjectParamWidget.size();
            lObjectParamWidget.add(new ObjectParamWidget(this, controller, dataPresenter, objId, dataPresenter.getlObjectTypes().get(id),
                    dataPresenter.getlObjectNames().get(id), dataPresenter.getSuggestionId(), dataPresenter.getScenarioId()));
        }
    }

    public void removeObjectParamWidget(ObjectParamWidget objectParamWidget, JPanel panelToRemove) {
        //update all the outdated objectId by searching in the list all the objectParamWidget after the one given
        //this function needs to be call from all ObjectParamWidget after remove object button has been pressed
        for (int i = objectParamWidget.getObjectId() + 1; i < lObjectParamWidget.size(); i++) {
            lObjectParamWidget.get(i).setObjectId(i - 1);
        }
        objectsBox.remove(panelToRemove);
        objectsBox.revalidate();
        objectsBox.repaint();
        lObjectParamWidget.remove(objectParamWidget);
    }


    public void addObjectParamWidget(JPanel panelToAdd) {
        //this function needs to be call from all ObjectParamWidget in order to display the ObjectParamWidget
        objectsBox.add(panelToAdd);
        objectsBox.revalidate();
        objectsBox.repaint();
    }

    public void setActionNameColor(int isPresentInBdl) {
        actionNameTextField.setFont(new Font("", Font.BOLD, 12));
        if (isPresentInBdl != 0) { //if present in BDL then set text color to green
            actionNameTextField.setForeground(new Color(9, 148, 65));
        } else {
            actionNameTextField.setForeground(Color.RED);
        }
        frequencyLabel.setText(WORD_FREQUENCY + isPresentInBdl);
    }

    private void initTypeComboBox(DataPresenterGUI dataPresenter) {
        actionTypeComboBox.removeAllItems();
        actionTypeComboBox.addItem("void");
        for (String type : dataPresenter.getlTypes()) {
            actionTypeComboBox.addItem(type);
        }
        actionTypeComboBox.addItem(CREATE_CUSTOM);
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
        mainPanel.setLayout(new GridLayoutManager(1, 5, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLoweredBevelBorder(), "Suggestion", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        actionTypeComboBox = new JComboBox();
        actionTypeComboBox.setBackground(new Color(-1));
        actionTypeComboBox.setLightWeightPopupEnabled(true);
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("void");
        defaultComboBoxModel1.addElement("string");
        defaultComboBoxModel1.addElement("int");
        defaultComboBoxModel1.addElement("float");
        defaultComboBoxModel1.addElement("double");
        defaultComboBoxModel1.addElement("bool");
        defaultComboBoxModel1.addElement("CREATE CUSTOM");
        actionTypeComboBox.setModel(defaultComboBoxModel1);
        actionTypeComboBox.setOpaque(true);
        actionTypeComboBox.setRequestFocusEnabled(true);
        actionTypeComboBox.setSelectedIndex(0);
        mainPanel.add(actionTypeComboBox, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        actionNameTextField = new JTextField();
        actionNameTextField.setText("withdraw_cash");
        mainPanel.add(actionNameTextField, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        addObjectButton = new JButton();
        addObjectButton.setBorderPainted(true);
        addObjectButton.setText("+");
        mainPanel.add(addObjectButton, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        removeSuggestionButton = new JButton();
        removeSuggestionButton.setBorderPainted(true);
        removeSuggestionButton.setOpaque(true);
        removeSuggestionButton.setText("Remove Suggestion");
        mainPanel.add(removeSuggestionButton, new GridConstraints(0, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        frequencyLabel = new JLabel();
        frequencyLabel.setText("");
        mainPanel.add(frequencyLabel, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return mainPanel;
    }

}




