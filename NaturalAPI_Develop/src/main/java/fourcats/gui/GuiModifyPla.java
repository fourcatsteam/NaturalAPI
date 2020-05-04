package fourcats.gui;

import fourcats.interfaceadapters.Controller;
import fourcats.interfaceadapters.DataPresenter;
import fourcats.interfaceadapters.DataPresenterGui;
import fourcats.observer.Observer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class GuiModifyPla implements Observer {

    JFrame frame;
    private JTextArea textArea1;
    private JTextArea textArea2;
    private JButton modifyButton;
    private JButton cancelButton;
    private JPanel mainPanel;
    private JButton loadButton;
    JFileChooser fileChooser;

    private Controller controller;
    private DataPresenterGui dataPresenterGui;
    String message;
    String modifyApiPla;
    String modifyCustomPla;

    public GuiModifyPla(Controller controller, DataPresenterGui dataPresenterGui){

        this.controller = controller;
        this.dataPresenterGui = dataPresenterGui;
        this.dataPresenterGui.attach(this);
        message = "";
        modifyApiPla = "";
        modifyCustomPla = "";

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
            fileChooser = new JFileChooser("C:\\Users\\matte\\OneDrive\\Desktop\\" +
                    "NaturalAPI\\NaturalAPI_Develop\\PLA\\");
            fileChooser.showOpenDialog(mainPanel);
            controller.loadPlaToModify(fileChooser.getSelectedFile().getAbsolutePath());
            textArea1.setText(modifyApiPla);
            textArea2.setText(modifyCustomPla);
        });

        modifyButton.addActionListener(e -> {
            controller.modifyPla(fileChooser.getSelectedFile().getAbsolutePath(),textArea1.getText() +
                    "\ncustom class\n" + textArea2.getText());
            JOptionPane.showMessageDialog(frame,message);
            frame.dispose();
        });

        cancelButton.addActionListener(e -> {
            frame.dispose(); //close the frame
        });
    }

    public void showGuiModifyPla(){
        frame.setVisible(true);
    }

    public void showOutput(){
        modifyApiPla = dataPresenterGui.getModifyApiPla();
        modifyCustomPla = dataPresenterGui.getModifyCustomPla();
        message = dataPresenterGui.getMessagePla();
    }

    @Override
    public void update() {
        showOutput();
    }
}
