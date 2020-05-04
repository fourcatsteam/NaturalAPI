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

    public Gui(Controller c,DataPresenterGui d){

        controller = c;
        dataPresenterGui = d;
        dataPresenterGui.attach(this);
        final String[] bal = new String[1];
        final String[] pla = new String[1];
        toView = new HashMap<>();

        frame = new JFrame("NaturalApi Develop");
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700,500);
        try {
            frame.setIconImage(ImageIO.read(new File("./bee.png")));
        }
        catch (Exception e){
            e.printStackTrace();
        }
        comboBox1.setVisible(false);
        DefaultListCellRenderer dlcr = new DefaultListCellRenderer();
        dlcr.setHorizontalAlignment(SwingConstants.CENTER);
        comboBox1.setRenderer(dlcr);
        
        addBalButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.showOpenDialog(mainPanel);
            bal[0] = fileChooser.getSelectedFile().getName();
            if(bal[0].contains(".json")){
                messageLabel.setText("Bal loaded!");
                mainPanel.setBackground(Color.GREEN);
            }
            else{
                mainPanel.setBackground(Color.RED);
                messageLabel.setText("Ops! Something went wrong! Reload your Bal");
            }
        });

        addPlaButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.showOpenDialog(mainPanel);
            pla[0] = fileChooser.getSelectedFile().getName();
            if(pla[0].contains(".txt")){
                messageLabel.setText("Pla loaded!");
                mainPanel.setBackground(Color.GREEN);
            }
            else{
                mainPanel.setBackground(Color.RED);
                messageLabel.setText("Ops! Something went wrong! Reload your Bal");
            }
        });

        suggestionButton.addActionListener(e -> {
            try{
                c.createApiSuggestion(bal[0],pla[0]);
                comboBox1.setVisible(true);
                mainPanel.setBackground(Color.GREEN);
                messageLabel.setText("Suggestions created!");
            }
            catch (Exception ex){
                messageLabel.setText("Ops! Something went wrong! " +
                        "Check that your BAL and PLA files are correctly formatted");
                mainPanel.setBackground(Color.RED);
            }
        });

        generateButton.addActionListener(e -> {
            try{
                c.generateApi();
                messageLabel.setText("APIs generated!");
                mainPanel.setBackground(Color.GREEN);
            }
            catch(Exception ex){
                messageLabel.setText("Ops! Something went wrong!");
                mainPanel.setBackground(Color.RED);
            }
        });

        createPLAButton.addActionListener(e -> {
            GuiPla guiPla = new GuiPla(controller,dataPresenterGui);
            guiPla.showGuiPla();
        });

        modifyPLAButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser("C:\\Users\\matte\\OneDrive\\Desktop\\" +
                    "NaturalAPI\\NaturalAPI_Develop\\PLA\\");
            fileChooser.showOpenDialog(mainPanel);
            try (BufferedReader br = new BufferedReader(new FileReader((fileChooser.getSelectedFile())))){
                GuiPla guiPla = new GuiPla(controller,dataPresenterGui);
                guiPla.showGuiPla();
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
        });

        modifyButton.addActionListener(e -> {
            controller.modifyApiGui(toView.get(comboBox1.getSelectedItem().toString()),textArea.getText());
            toView.replace(comboBox1.getSelectedItem().toString(),textArea.getText());
        });

        comboBox1.addActionListener(e -> {
            textArea.setText(toView.get(comboBox1.getSelectedItem().toString()));
        });
        createPLAButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                createPLAButton.setBackground(new Color( 224,91,73));
                createPLAButton.setBorderPainted(false);
            }
        });
        createPLAButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                createPLAButton.setBackground(new Color(58,84,105));
                createPLAButton.setBorderPainted(true);
            }
        });
        modifyPLAButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                modifyPLAButton.setBackground(new Color( 224,91,73));
                modifyPLAButton.setBorderPainted(false);
            }
        });
        modifyPLAButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                modifyPLAButton.setBackground(new Color(58,84,105));
                modifyPLAButton.setBorderPainted(true);
            }
        });
        addBalButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                addBalButton.setBackground(new Color( 224,91,73));
                addBalButton.setBorderPainted(false);
            }
        });
        addBalButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                addBalButton.setBackground(new Color(58,84,105));
                addBalButton.setBorderPainted(true);
            }
        });
        addPlaButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                addPlaButton.setBackground(new Color( 224,91,73));
                addPlaButton.setBorderPainted(false);
            }
        });
        addPlaButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                addPlaButton.setBackground(new Color(58,84,105));
                addPlaButton.setBorderPainted(true);
            }
        });
        suggestionButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                suggestionButton.setBackground(new Color( 224,91,73));
                suggestionButton.setBorderPainted(false);
            }
        });
        suggestionButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                suggestionButton.setBackground(new Color(58,84,105));
                suggestionButton.setBorderPainted(true);
            }
        });
        generateButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                generateButton.setBackground(new Color( 224,91,73));
                generateButton.setBorderPainted(false);
            }
        });
        generateButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                generateButton.setBackground(new Color(58,84,105));
                generateButton.setBorderPainted(true);
            }
        });
        modifyButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                modifyButton.setBackground(new Color( 224,91,73));
                modifyButton.setBorderPainted(false);
            }
        });
        modifyButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                modifyButton.setBackground(new Color(58,84,105));
                modifyButton.setBorderPainted(true);
            }
        });
    }

    public void showGui(){
        frame.setVisible(true);
    }

    public void showOutput(){
        toView.put(dataPresenterGui.getComboToShow(),dataPresenterGui.getStringToShow());
        comboBox1.addItem(dataPresenterGui.getComboToShow());
    }

    @Override
    public void update() {
        showOutput();
    }

}
