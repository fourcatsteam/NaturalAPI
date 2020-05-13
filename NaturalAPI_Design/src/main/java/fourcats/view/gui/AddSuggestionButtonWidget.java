package fourcats.view.gui;

import fourcats.interfaceadapters.Controller;

import javax.swing.*;

public class AddSuggestionButtonWidget {
    private JButton addSuggestionButton;

    public AddSuggestionButtonWidget(Controller controller, String scenarioId){
        addSuggestionButton = new JButton("+");
        addSuggestionButton.addActionListener(e->{
            String actionName = JOptionPane.showInputDialog(null,"Insert the name for the action","Add suggestion",JOptionPane.INFORMATION_MESSAGE).trim();
            if (!actionName.equals("")) {
                if (Character.isDigit(actionName.charAt(0))) actionName = "_" + actionName;
                controller.addSuggestion(scenarioId, actionName, "void");
            }
            else{
                JOptionPane.showMessageDialog(null,"Action name field can not be empty", "Error!", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
    JButton getButtonWidget(){
        return addSuggestionButton;
    }
}
