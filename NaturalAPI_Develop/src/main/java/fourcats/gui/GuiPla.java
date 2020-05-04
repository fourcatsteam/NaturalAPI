package fourcats.gui;

import fourcats.interfaceadapters.Controller;
import fourcats.interfaceadapters.DataPresenterGui;
import fourcats.observer.Observer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;

public class GuiPla implements Observer {

    private JFrame frame;
    private JTextArea textArea1;
    private JButton createButton;
    private JButton cancelButton;
    private JPanel mainPanel;
    private JTextField textField1;
    private JTextField textField2;

    String message;

    private Controller controller;
    private DataPresenterGui dataPresenterGui;

    public GuiPla(Controller c,DataPresenterGui d){

        controller = c;
        dataPresenterGui = d;
        dataPresenterGui.attach(this);
        message = "";

        frame = new JFrame("NaturalApi Develop");
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setSize(500,500);
        try {
            frame.setIconImage(ImageIO.read(new File("./bee.png")));
        }
        catch (Exception e){
            e.printStackTrace();
        }
        createButton.addActionListener(e -> {

            controller.createPla(textField1.getText(),textField2.getText(),textArea1.getText());
            JOptionPane.showMessageDialog(frame,message);
        });

        cancelButton.addActionListener(e -> {
            frame.dispose(); //close the frame
        });
    }

    public void showGuiPla(){
        frame.setVisible(true);
    }

    public void addLineTextArea1(String s) {
        textArea1.append(s);
    }

    public void setTextField1FromString(String s) {
        textField1.setText(s);
    }

    public void setTextField2FromString(String s) {
        textField2.setText(s);
    }

    public void showOutput(){
        message = dataPresenterGui.getMessagePla();
    }

    @Override
    public void update() {
        showOutput();
    }
}
