package fourcats.view;

import fourcats.interfaceadapters.Controller;
import fourcats.interfaceadapters.DataPresenterGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Objects;


public class SuggestionWidget {
    private JComboBox<String> actionTypeComboBox;
    private JTextField actionNameTextField;
    private JButton addObjectButton;
    private JButton removeSuggestionButton;
    private JPanel mainPanel;
    private String suggestionId;
    private String scenarioId;
    private static final String CREATE_CUSTOM = "CREATE CUSTOM";

    public SuggestionWidget(JPanel panelToUpdate, Controller contr, DataPresenterGUI dataPresenter){
        mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.LINE_AXIS));
        Box objectsBox = Box.createVerticalBox();

        this.scenarioId = dataPresenter.getScenarioId();
        this.suggestionId = dataPresenter.getSuggestionId();

        actionTypeComboBox.setSelectedItem(dataPresenter.getActionType());
        actionTypeComboBox.add(Box.createHorizontalBox());
        actionNameTextField.setText(dataPresenter.getActionName());

        mainPanel.add(actionTypeComboBox);
        mainPanel.add(actionNameTextField);
        mainPanel.add(objectsBox);

        addObjects(contr,dataPresenter,objectsBox);

        mainPanel.add(addObjectButton);
        mainPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        mainPanel.add(removeSuggestionButton);
        panelToUpdate.add(mainPanel);

        addObjectButton.addActionListener(e->{
            String objectName = JOptionPane.showInputDialog(null,"Insert the name for the object",
                    "Object creation",JOptionPane.QUESTION_MESSAGE);
            if (!objectName.equals("")){
                contr.addObject(suggestionId,scenarioId,objectName,"0");
                if (dataPresenter.isOkOperation()){
                    new ObjectParamWidget(objectsBox,contr,dataPresenter,Integer.toString(dataPresenter.getlObjectId().size()),
                            "string",objectName,suggestionId,scenarioId);
                }
            }
            JOptionPane.showMessageDialog(null,dataPresenter.getMessage());
        });

        removeSuggestionButton.addActionListener(e -> {
            if (JOptionPane.showConfirmDialog(null,"Are you sure you want to remove this suggestion?",
                    "Suggestion remove confirmation",JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == 0) {
                //0=yes
                contr.declineSuggestion(suggestionId,scenarioId);
                panelToUpdate.remove(mainPanel);
            }
        });

        actionTypeComboBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED){
                if(Objects.equals(actionTypeComboBox.getSelectedItem(), CREATE_CUSTOM)){
                    new CustomTypeCreation(contr);
                }
                else{
                    contr.modifyActionType(suggestionId,scenarioId,actionTypeComboBox.getSelectedItem().toString());
                }
            }
        });

        actionTypeComboBox.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                contr.showTypes();
                actionTypeComboBox.removeAllItems();
                actionTypeComboBox.addItem("void");
                for (String type : dataPresenter.getlTypes()) {
                    actionTypeComboBox.addItem(type);
                }
                actionTypeComboBox.addItem(CREATE_CUSTOM);
            }
        });

        actionNameTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                contr.modifyActionName(suggestionId,scenarioId,actionNameTextField.getText());
            }
        });

    }

    private void addObjects(Controller controller, DataPresenterGUI dataPresenter, Box boxToUpdate){
        for (String objId : dataPresenter.getlObjectId()){
            int id = Integer.parseInt(objId);
            new ObjectParamWidget(boxToUpdate,controller,dataPresenter,objId,dataPresenter.getlObjectTypes().get(id),
                    dataPresenter.getlObjectNames().get(id),dataPresenter.getSuggestionId(),dataPresenter.getScenarioId());
        }

    }

}




