package fourcats.GUI;

import fourcats.interfaceadapters.Controller;
import fourcats.interfaceadapters.DataPresenter;
import fourcats.observer.Observer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class Gui implements Observer {

    private Controller controller;
    private DataPresenter dataPresenter;

    private JButton addBalButton;
    private JButton addPlaButton;
    private JButton suggestionButton;
    private JTextArea textArea;
    private JPanel mainPanel;
    private JPanel secondPanel;
    private JPanel thirdPanel;
    private JButton generateButton;
    private JButton modifyButton;

    public Gui(Controller c,DataPresenter d){

        controller = c;
        dataPresenter = d;
        dataPresenter.attach(this);
        final String[] bal = new String[1];
        final String[] pla = new String[1];

        JFrame frame = new JFrame("NaturalApi Develop");
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700,900);
        try {
            frame.setIconImage(ImageIO.read(new File("./logo1.png")));
        }
        catch (Exception e){
            e.printStackTrace();
        }
        frame.setVisible(true);

        addBalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser("C:\\Users\\matte\\OneDrive\\Desktop\\" +
                        "NaturalAPI\\NaturalAPI_Develop\\");
                fileChooser.showOpenDialog(mainPanel);
                bal[0] = fileChooser.getSelectedFile().getName();
            }
        });

        addPlaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser("C:\\Users\\matte\\OneDrive\\Desktop\\" +
                        "NaturalAPI\\NaturalAPI_Develop\\");
                fileChooser.showOpenDialog(mainPanel);
                pla[0] = fileChooser.getSelectedFile().getName();
            }
        });
        suggestionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                c.createApiSuggestion(bal[0],pla[0]);
            }
        });
        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                c.generateApi();
            }
        });
    }

    public void showOutput(){
        textArea.append(dataPresenter.getStringToShow());
    }

    @Override
    public void update() {
        showOutput();
    }
}
