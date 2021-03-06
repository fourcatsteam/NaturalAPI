package fourcats.view;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
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
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GuiModifyPla implements Observer {

    private JFrame frame;
    private JTextArea textArea1;
    private JTextArea textArea2;
    private JButton modifyButton;
    private JButton cancelButton;
    private JPanel mainPanel;
    private JButton loadButton;
    private JTextArea textArea3;
    private JLabel label1;
    private JLabel label2;
    private JLabel label3;
    private JFileChooser fileChooser;
    private Controller controller;
    private DataPresenterGui dataPresenterGui;
    private String message;
    private String modifyApiPla;
    private String modifyCustomPla;
    private String modifyTestPla;
    private int valid;

    public GuiModifyPla(Controller controller, DataPresenterGui dataPresenterGui) {

        this.controller = controller;
        this.dataPresenterGui = dataPresenterGui;
        this.dataPresenterGui.attach(this);
        message = "";
        modifyApiPla = "";
        modifyCustomPla = "";
        modifyTestPla = "";
        valid = JFileChooser.CANCEL_OPTION;

        frame = new JFrame("NaturalApi Develop");

        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);

        //set logo
        final URL url = Thread.currentThread().getContextClassLoader().getResource("images/logo.png");
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage(url));

        loadButton.addActionListener(e -> {
            fileChooser = new JFileChooser();
            valid = fileChooser.showOpenDialog(mainPanel);
            if (valid == JFileChooser.APPROVE_OPTION) {
                controller.loadPlaToModify(fileChooser.getSelectedFile().getAbsolutePath());
                textArea1.setText(modifyApiPla);
                textArea2.setText(modifyCustomPla);
                textArea3.setText(modifyTestPla);
            }
        });

        modifyButton.addActionListener(e -> {

            if (valid == JFileChooser.APPROVE_OPTION) {
                if (textArea1.getText().isEmpty() || textArea2.getText().isEmpty() || textArea3.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "There is an empty field!");
                } else {
                    if (textArea1.getText().concat(textArea2.getText() + textArea3.getText())
                            .equals(modifyApiPla + modifyCustomPla + modifyTestPla)) {
                        JOptionPane.showMessageDialog(frame, "There aren't changes!");
                    } else {
                        try {
                            controller.modifyPla(fileChooser.getSelectedFile().getAbsolutePath(), textArea1.getText() +
                                    "\ncustom class\n" + textArea2.getText() + "\ntest class\n" + textArea3.getText());
                            JOptionPane.showMessageDialog(frame, message);
                            frame.dispose();
                        } catch (IOException ex) {
                            Logger logger = Logger.getAnonymousLogger();
                            logger.log(Level.SEVERE, "IOException", e);
                        }
                    }
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Select a PLA to modify first!");
            }
        });

        cancelButton.addActionListener(e ->
                frame.dispose() //close the frame
        );

        Color myRed = new Color(224, 91, 73);
        Color myBlue = new Color(58, 84, 105);

        loadButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                loadButton.setBackground(myRed);
                loadButton.setBorderPainted(false);
            }
        });
        loadButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                loadButton.setBackground(myBlue);
                loadButton.setBorderPainted(true);
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

        label1.setBorder(new EmptyBorder(5, 0, 5, 0));
        label2.setBorder(new EmptyBorder(5, 0, 5, 0));
        label3.setBorder(new EmptyBorder(5, 0, 5, 0));

    }

    public void showGuiModifyPla() {
        frame.setVisible(true);
    }

    public void showOutput() {
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
