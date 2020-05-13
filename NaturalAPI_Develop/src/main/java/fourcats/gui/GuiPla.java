package fourcats.gui;

import fourcats.interfaceadapters.Controller;
import fourcats.interfaceadapters.DataPresenterGui;
import fourcats.observer.Observer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GuiPla implements Observer {

    private JFrame frame;
    private JTextArea textArea1;
    private JButton createButton;
    private JButton cancelButton;
    private JPanel mainPanel;
    private JTextField textField1;
    private JTextArea textArea2;
    private JTextArea textArea3;

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
            Logger logger = Logger.getAnonymousLogger();
            logger.log(Level.SEVERE, "File not found", e);
        }

        createButton.addActionListener(e -> {

            if(textField1.getText().isEmpty() || textArea1.getText().isEmpty() || textArea2.getText().isEmpty() || textArea3.getText().isEmpty()){
                JOptionPane.showMessageDialog(frame,"There is an empty field!");
            }
            else{
                JFileChooser savePla = new JFileChooser();
                savePla.showSaveDialog(mainPanel);
                controller.createPla(savePla.getSelectedFile().getAbsolutePath(),
                        textField1.getText(),
                        textArea1.getText() + "\ncustom class\n" + textArea2.getText() + "\ntest class\n" + textArea3.getText());
                JOptionPane.showMessageDialog(frame,message);
                if(message.equals("PLA created!")){
                    frame.dispose();
                }
            }
        });

        cancelButton.addActionListener(e ->
            frame.dispose() //close the frame
        );
    }

    public void showGuiPla(){
        frame.setVisible(true);
    }

    public void showOutput(){
        message = dataPresenterGui.getMessage();
    }

    @Override
    public void update() {
        showOutput();
    }
}
