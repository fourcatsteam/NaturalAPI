package fourcats.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class GuiPla {
    private JTextArea textArea1;
    private JButton createButton;
    private JButton cancelButton;
    private JPanel mainPanel;

    public void addLineTextArea1(String s) {
        textArea1.append(s);
    }

    public void setTextField1FromString(String s) {
        textField1.setText(s);
    }

    public void setTextField2FromString(String s) {
        textField2.setText(s);
    }

    private JTextField textField1;
    private JTextField textField2;

    public GuiPla(){
        JFrame frame = new JFrame("NaturalApi Develop");
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(500,500);
        try {
            frame.setIconImage(ImageIO.read(new File("./logo1.png")));
        }
        catch (Exception e){
            e.printStackTrace();
        }
        frame.setVisible(true);
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                File file = new File("./PLA/" + textField1.getText());
                if(file.exists()) {
                    int answer = JOptionPane.YES_NO_OPTION;
                    JOptionPane.showConfirmDialog(frame,"There is already a PLA with this name. Do you want to overwrite it?");
                    if(answer == JOptionPane.YES_OPTION){
                        try (FileWriter fw = new FileWriter(file)){
                            fw.write(textField2.getText() + "\n" + textArea1.getText());
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
                else{
                    try(FileWriter fw = new FileWriter(file)){
                        fw.write(textField2.getText() + "\n" + textArea1.getText());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }

            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); //close the frame
            }
        });
    }
}
