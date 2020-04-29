package fourcats.gui;

import fourcats.interfaceadapters.Controller;
import fourcats.interfaceadapters.DataPresenter;
import fourcats.observer.Observer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class Gui implements Observer {

    private Controller controller;
    private DataPresenter dataPresenter;

    private JFrame frame;
    private JButton addBalButton;
    private JButton addPlaButton;
    private JButton suggestionButton;
    private JTextArea textArea;
    private JPanel mainPanel;
    private JPanel secondPanel;
    private JPanel thirdPanel;
    private JButton generateButton;
    private JButton modifyButton;
    private JButton modifyPLAButton;
    private JButton createPLAButton;
    private JComboBox<String> comboBox1;

    public Gui(Controller c,DataPresenter d){

        controller = c;
        dataPresenter = d;
        dataPresenter.attach(this);
        final String[] bal = new String[1];
        final String[] pla = new String[1];

        frame = new JFrame("NaturalApi Develop");
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700,500);
        try {
            frame.setIconImage(ImageIO.read(new File("./logo1.png")));
        }
        catch (Exception e){
            e.printStackTrace();
        }
        comboBox1.setVisible(false);

        addBalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser("C:\\Users\\matte\\OneDrive\\Desktop\\" +
                        "NaturalAPI\\NaturalAPI_Develop\\BAL\\");
                fileChooser.showOpenDialog(mainPanel);
                bal[0] = fileChooser.getSelectedFile().getName();
            }
        });

        addPlaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser("C:\\Users\\matte\\OneDrive\\Desktop\\" +
                        "NaturalAPI\\NaturalAPI_Develop\\PLA\\");
                fileChooser.showOpenDialog(mainPanel);
                pla[0] = fileChooser.getSelectedFile().getName();
            }
        });
        suggestionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                c.createApiSuggestion(bal[0],pla[0]);
                comboBox1.setVisible(true);
            }
        });
        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                c.generateApi();
            }
        });
        createPLAButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GuiPla guiPla = new GuiPla();
            }
        });
        modifyPLAButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser("C:\\Users\\matte\\OneDrive\\Desktop\\" +
                        "NaturalAPI\\NaturalAPI_Develop\\PLA\\");
                fileChooser.showOpenDialog(mainPanel);
                try (BufferedReader br = new BufferedReader(new FileReader((fileChooser.getSelectedFile())))){
                    GuiPla guiPla = new GuiPla();
                    guiPla.setTextField1FromString(fileChooser.getSelectedFile().getName());
                    guiPla.setTextField2FromString(br.readLine());
                    String line;
                    while((line = br.readLine()) != null){
                        guiPla.addLineTextArea1(line);
                        guiPla.addLineTextArea1("\n");
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        modifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.modifyApiGui(comboBox1.getSelectedItem().toString(),textArea.getText());
                comboBox1.addItem(textArea.getText());
                comboBox1.removeItem(comboBox1.getSelectedItem());
            }
        });
        comboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.setText(comboBox1.getSelectedItem().toString());
            }
        });
    }

    public void showGui(){
        frame.setVisible(true);
    }

    public void showOutput(){
        comboBox1.addItem(dataPresenter.getStringToShow());
    }

    @Override
    public void update() {
        showOutput();
    }
}
