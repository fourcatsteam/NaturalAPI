package fourcats.gui;

import fourcats.interfaceadapters.Controller;
import fourcats.interfaceadapters.DataPresenterGui;
import fourcats.observer.Observer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Gui implements Observer {

    private Controller controller;
    private DataPresenterGui dataPresenterGui;

    private JFrame frame;
    private JButton addBalButton;
    private JButton addPlaButton;
    private JButton suggestionButton;
    private JTextArea textArea;
    private JPanel mainPanel;
    private JButton generateButton;
    private JButton modifyButton;
    private JButton modifyPLAButton;
    private JButton createPLAButton;
    private JComboBox<String> comboBox1;
    private JLabel messageLabel;
    Map<String,String> toView;
    private String bal;
    private String pla;
    private String message;

    public Gui(Controller c,DataPresenterGui d){

        controller = c;
        dataPresenterGui = d;
        dataPresenterGui.attach(this);
        bal = "";
        pla = "";
        message = "";
        toView = new HashMap<>();

        frame = new JFrame("NaturalApi Develop");
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(750,700);
        frame.setLocationRelativeTo(null);
        try {
            frame.setIconImage(ImageIO.read(new File("./bee.png")));
        }
        catch (IOException e){
            Logger logger = Logger.getAnonymousLogger();
            logger.log(Level.SEVERE, "IOException", e);
        }
        comboBox1.setVisible(false);
        DefaultListCellRenderer dlcr = new DefaultListCellRenderer();
        dlcr.setHorizontalAlignment(SwingConstants.CENTER);
        comboBox1.setRenderer(dlcr);
        
        addBalButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int valid = fileChooser.showOpenDialog(mainPanel);
            if (valid == JFileChooser.APPROVE_OPTION) {
                setBal(fileChooser.getSelectedFile().getAbsolutePath());
                if(bal.contains(".json")){
                    messageLabel.setText("Bal loaded!");
                    mainPanel.setBackground(Color.GREEN);
                }
                else{
                    mainPanel.setBackground(Color.RED);
                    messageLabel.setText("Ops! Something went wrong! Reload your Bal");
                }
            }
        });

        addPlaButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int valid = fileChooser.showOpenDialog(mainPanel);
            if (valid == JFileChooser.APPROVE_OPTION) {
                setPla(fileChooser.getSelectedFile().getAbsolutePath());
                if (pla.contains(".txt")) {
                    messageLabel.setText("Pla loaded!");
                    mainPanel.setBackground(Color.GREEN);
                } else {
                    mainPanel.setBackground(Color.RED);
                    messageLabel.setText("Ops! Something went wrong! Reload your Bal");
                }
            }
        });

        suggestionButton.addActionListener(e -> {
            try{
                if(bal.isEmpty() || pla.isEmpty()){
                    messageLabel.setText("Load bal and pla first!");
                    mainPanel.setBackground(Color.RED);
                }
                else {
                    c.createApiSuggestion(bal,pla);
                    comboBox1.setVisible(true);
                    messageLabel.setText(message);
                    if(message.equals("Suggestions created!")){
                        mainPanel.setBackground(Color.GREEN);
                    }
                    else if(message.equals("This couple of BAL and PLA is already generated!")){
                        mainPanel.setBackground(Color.RED);
                    }
                }
            }
            catch (Exception ex){
                messageLabel.setText("Ops! Something went wrong! " +
                        "Check that your BAL and PLA files are correctly formatted");
                mainPanel.setBackground(Color.RED);
            }
        });

        generateButton.addActionListener(e -> {
            try{
                if(comboBox1.getItemCount() == 0){
                    messageLabel.setText("There aren't suggestions to generate!");
                    mainPanel.setBackground(Color.RED);
                }
                else {
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                    int valid = fileChooser.showSaveDialog(mainPanel);
                    if(valid == JFileChooser.APPROVE_OPTION) {
                        c.generateApi(fileChooser.getSelectedFile().getAbsolutePath() + "\\");
                        messageLabel.setText(message);
                        mainPanel.setBackground(Color.GREEN);
                    }
                }
            }
            catch(Exception ex){
                messageLabel.setText("File not found!");
                mainPanel.setBackground(Color.RED);
            }
        });

        createPLAButton.addActionListener(e -> {
            GuiPla guiPla = new GuiPla(controller,dataPresenterGui);
            guiPla.showGuiPla();
        });

        modifyPLAButton.addActionListener(e -> {
            GuiModifyPla guiModifyPla = new GuiModifyPla(controller,dataPresenterGui);
            guiModifyPla.showGuiModifyPla();
        });

        modifyButton.addActionListener(e -> {
            try{
                if(toView.get(comboBox1.getSelectedItem().toString()).equals(textArea.getText())){
                    messageLabel.setText("There aren't changes!");
                    mainPanel.setBackground(Color.RED);
                }
                else {
                    controller.modifyApiGui(toView.get(comboBox1.getSelectedItem().toString()),textArea.getText());
                    toView.replace(comboBox1.getSelectedItem().toString(),textArea.getText());
                    messageLabel.setText("Api modified!");
                    mainPanel.setBackground(Color.GREEN);
                }
            }
            catch (Exception ex) {
                messageLabel.setText("There aren't suggestions to modify!");
                mainPanel.setBackground(Color.RED);
            }
        });

        comboBox1.addActionListener(e ->
            textArea.setText(toView.get(comboBox1.getSelectedItem().toString()))
        );

        Color myRed = new Color( 224,91,73);
        Color myBlue = new Color(58,84,105);

        createPLAButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                createPLAButton.setBackground(myRed);
                createPLAButton.setBorderPainted(false);
            }
        });
        createPLAButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                createPLAButton.setBackground(myBlue);
                createPLAButton.setBorderPainted(true);
            }
        });
        modifyPLAButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                modifyPLAButton.setBackground(myRed);
                modifyPLAButton.setBorderPainted(false);
            }
        });
        modifyPLAButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                modifyPLAButton.setBackground(myBlue);
                modifyPLAButton.setBorderPainted(true);
            }
        });
        addBalButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                addBalButton.setBackground(myRed);
                addBalButton.setBorderPainted(false);
            }
        });
        addBalButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                addBalButton.setBackground(myBlue);
                addBalButton.setBorderPainted(true);
            }
        });
        addPlaButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                addPlaButton.setBackground(myRed);
                addPlaButton.setBorderPainted(false);
            }
        });
        addPlaButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                addPlaButton.setBackground(myBlue);
                addPlaButton.setBorderPainted(true);
            }
        });
        suggestionButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                suggestionButton.setBackground(myRed);
                suggestionButton.setBorderPainted(false);
            }
        });
        suggestionButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                suggestionButton.setBackground(myBlue);
                suggestionButton.setBorderPainted(true);
            }
        });
        generateButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                generateButton.setBackground(myRed);
                generateButton.setBorderPainted(false);
            }
        });
        generateButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                generateButton.setBackground(myBlue);
                generateButton.setBorderPainted(true);
            }
        });
        modifyButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                modifyButton.setBackground(myRed);
                modifyButton.setBorderPainted(false);
            }
        });
        modifyButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                modifyButton.setBackground(myBlue);
                modifyButton.setBorderPainted(true);
            }
        });
    }

    private void setBal(String s) {
        bal = s;
    }

    private void setPla(String s) {
        pla = s;
    }

    public void showGui(){
        frame.setVisible(true);
    }

    public void showOutput(){
        if(((DefaultComboBoxModel)comboBox1.getModel()).getIndexOf(dataPresenterGui.getComboToShow()) == -1) {
            toView.put(dataPresenterGui.getComboToShow(), dataPresenterGui.getStringToShow());
            comboBox1.addItem(dataPresenterGui.getComboToShow());
        }
        else {
            if(dataPresenterGui.getComboToShow().equals("Test\\StepDefinitions.java")){
                toView.put(dataPresenterGui.getComboToShow(), dataPresenterGui.getStringToShow());
                comboBox1.setSelectedIndex(0);
            }
        }
        message = dataPresenterGui.getMessage();
    }

    @Override
    public void update() {
        showOutput();
    }

}
