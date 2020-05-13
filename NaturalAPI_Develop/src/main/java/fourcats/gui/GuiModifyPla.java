package fourcats.gui;

import fourcats.interfaceadapters.Controller;
import fourcats.interfaceadapters.DataPresenterGui;
import fourcats.observer.Observer;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;

public class GuiModifyPla implements Observer {

    JFrame frame;
    private JTextArea textArea1;
    private JTextArea textArea2;
    private JButton modifyButton;
    private JButton cancelButton;
    private JPanel mainPanel;
    private JButton loadButton;
    private JTextArea textArea3;
    JFileChooser fileChooser;

    private Controller controller;
    private DataPresenterGui dataPresenterGui;
    String message;
    String modifyApiPla;
    String modifyCustomPla;

    String modifyTestPla;

    public GuiModifyPla(Controller controller, DataPresenterGui dataPresenterGui){

        this.controller = controller;
        this.dataPresenterGui = dataPresenterGui;
        this.dataPresenterGui.attach(this);
        message = "";
        modifyApiPla = "";
        modifyCustomPla = "";
        modifyTestPla = "";

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

        loadButton.addActionListener(e -> {
            fileChooser = new JFileChooser();
            fileChooser.showOpenDialog(mainPanel);
            controller.loadPlaToModify(fileChooser.getSelectedFile().getAbsolutePath());
            textArea1.setText(modifyApiPla);
            textArea2.setText(modifyCustomPla);
            textArea3.setText(modifyTestPla);
        });

        modifyButton.addActionListener(e -> {

            if(textArea1.getText().isEmpty() || textArea2.getText().isEmpty() || textArea3.getText().isEmpty()){
                JOptionPane.showMessageDialog(frame,"There is an empty field!");
            }
            else{
                controller.modifyPla(fileChooser.getSelectedFile().getAbsolutePath(),textArea1.getText() +
                        "\ncustom class\n" + textArea2.getText() + "\ntest class\n" + textArea3.getText());
                JOptionPane.showMessageDialog(frame,message);
                frame.dispose();
            }
        });

        cancelButton.addActionListener(e ->
            frame.dispose() //close the frame
        );
    }

    public void showGuiModifyPla(){
        frame.setVisible(true);
    }

    public void showOutput(){
        modifyApiPla = dataPresenterGui.getModifyApiPla();
        modifyCustomPla = dataPresenterGui.getModifyCustomPla();
        modifyTestPla = dataPresenterGui.getModifyTestPla();
        message = dataPresenterGui.getMessage();
    }

    @Override
    public void update() {
        showOutput();
    }
}
