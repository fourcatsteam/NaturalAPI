package fourcats.view;

import fourcats.interfaceadapters.Controller;
import fourcats.interfaceadapters.DataPresenterGUI;

import javax.swing.*;
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
    private final String suggestionId;
    private final String scenarioId;
    private final Box objectsBox;
    private final ArrayList <ObjectParamWidget> lObjectParamWidget;
    private static final String CREATE_CUSTOM = "CREATE CUSTOM";


    public SuggestionWidget(JPanel panelToUpdate, Controller contr, DataPresenterGUI dataPresenter){
        lObjectParamWidget = new ArrayList<>();
        mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.LINE_AXIS));
        objectsBox = Box.createVerticalBox();

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
                    lObjectParamWidget.add(new ObjectParamWidget(this,contr,dataPresenter,Integer.toString(lObjectParamWidget.size()),
                            "string",objectName,suggestionId,scenarioId));
                }
                JOptionPane.showMessageDialog(null,dataPresenter.getMessage());
            }
            else{
                JOptionPane.showMessageDialog(null,"Abort: no name entered", "Invalid name",JOptionPane.WARNING_MESSAGE);
            }
        });

        removeSuggestionButton.addActionListener(e -> {
            if (JOptionPane.showConfirmDialog(null,"Are you sure you want to remove this suggestion?",
                    "Suggestion remove confirmation",JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == 0) {
                //0=yes
                contr.declineSuggestion(suggestionId,scenarioId);
                panelToUpdate.remove(mainPanel);
                panelToUpdate.revalidate();
                panelToUpdate.repaint();
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
        //init suggested objects
        for (String objId : dataPresenter.getlObjectId()){
            int id = lObjectParamWidget.size();
            lObjectParamWidget.add(new ObjectParamWidget(this,controller,dataPresenter,objId,dataPresenter.getlObjectTypes().get(id),
                    dataPresenter.getlObjectNames().get(id),dataPresenter.getSuggestionId(),dataPresenter.getScenarioId()));
        }
    }

    public void removeObjectParamWidget(ObjectParamWidget objectParamWidget, JPanel panelToRemove){
        //update all the outdated objectId by searching in the list all the objectParamWidget after the one given
        //this function needs to be call from all ObjectParamWidget after remove object button has been pressed
        for (int i=objectParamWidget.getObjectId()+1; i<lObjectParamWidget.size(); i++){
            lObjectParamWidget.get(i).setObjectId(i-1);
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
}




