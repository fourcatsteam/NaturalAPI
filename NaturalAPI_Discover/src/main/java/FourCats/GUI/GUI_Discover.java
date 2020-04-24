package FourCats.GUI;

import FourCats.InterfaceAdapters.Controller;
import FourCats.InterfaceAdapters.DataPresenter;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

public class GUI_Discover extends JPanel {
    private JButton loadFileButton;
    private JPanel panel1;
    private JFileChooser fc;
    private JTextArea log;
    private JButton createBdl;
    private JTextField nameBdl;
    private Controller controller;
    private DataPresenter datapresenter; //Concrete Subject
    private LinkedList<String> nameTitleList;

    public GUI_Discover(Controller c, DataPresenter d) {
        this.controller = c;
        this.datapresenter= d;
        nameTitleList = new LinkedList<>();
        fc = new JFileChooser("C:\\Users\\Simone\\Desktop\\NaturalAPI con gestione eccezioni bene\\NaturalAPI_Discover\\txt_documents");
        nameBdl.setText("Nome bdl");
        loadFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int returnVal = fc.showOpenDialog(GUI_Discover.this);

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    //This is where a real application would open the file.

                    log.append("Opening: " + file.getName() + "." + "\n");
                    nameTitleList.add(file.getName());
                } else {
                    log.append("Open command cancelled by user." + "\n");
                }
                log.setCaretPosition(log.getDocument().getLength());
            }
        });

        createBdl.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    controller.createBdl(nameBdl.getText(),nameTitleList);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("FileChooserDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Add content to the window.
        frame.add(new GUI_Discover(controller,datapresenter).panel1);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
}
