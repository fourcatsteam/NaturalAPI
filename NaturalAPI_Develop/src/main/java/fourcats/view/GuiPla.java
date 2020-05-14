package fourcats.view;

import fourcats.interfaceadapters.Controller;
import fourcats.interfaceadapters.DataPresenterGui;
import fourcats.observer.Observer;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
    private JLabel label1;
    private JLabel label2;
    private JLabel label3;
    private JLabel label4;

    private String message;

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
        frame.setLocationRelativeTo(null);
        try {
            frame.setIconImage(ImageIO.read(new File("./bee.png")));
        }
        catch (Exception e){
            Logger logger = Logger.getAnonymousLogger();
            logger.log(Level.SEVERE, "IOException", e);
        }

        createButton.addActionListener(e -> {

            if(textField1.getText().isEmpty() || textArea1.getText().isEmpty() || textArea2.getText().isEmpty() || textArea3.getText().isEmpty()){
                JOptionPane.showMessageDialog(frame,"There is an empty field!");
            }
            else{
                JFileChooser savePla = new JFileChooser();
                int valid = savePla.showSaveDialog(mainPanel);
                if(valid == JFileChooser.APPROVE_OPTION) {
                    controller.createPla(savePla.getSelectedFile().getAbsolutePath(),
                            textField1.getText(),
                            textArea1.getText() + "\ncustom class\n" + textArea2.getText() + "\ntest class\n" + textArea3.getText());
                    JOptionPane.showMessageDialog(frame, message);
                    if (message.equals("PLA created!")) {
                        frame.dispose();
                    }
                }
            }
        });

        cancelButton.addActionListener(e ->
            frame.dispose() //close the frame
        );

        Color myRed = new Color( 224,91,73);
        Color myBlue = new Color(58,84,105);

        createButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                createButton.setBackground(myRed);
                createButton.setBorderPainted(false);
            }
        });
        createButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                createButton.setBackground(myBlue);
                createButton.setBorderPainted(true);
            }
        });

        cancelButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                cancelButton.setBackground(myRed);
                cancelButton.setBorderPainted(false);
            }
        });
        cancelButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                cancelButton.setBackground(myBlue);
                cancelButton.setBorderPainted(true);
            }
        });

        label1.setBorder(new EmptyBorder(5,0,5,0));
        label2.setBorder(new EmptyBorder(5,0,5,0));
        label3.setBorder(new EmptyBorder(5,0,5,0));
        label4.setBorder(new EmptyBorder(5,0,5,0));

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
