package fourcats.view.gui;

import fourcats.datastructure.observer.Observer;
import fourcats.interfaceadapters.Controller;
import fourcats.interfaceadapters.DataPresenterGUI;
import fourcats.view.utilities.ViewUtility;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class GuiDesign implements Observer {
    private final Controller controller;
    private final DataPresenterGUI dataPresenter;
    private JLabel titleText;
    private JButton loadFeatureBtn;
    private JButton genSuggestBtn;
    private JTextArea log;
    private JPanel mainPanel;
    private JButton loadBDLButton;
    private JButton removeFeatureBtn;
    private JButton removeBdlBtn;
    private JPanel removeFilesBtnPanel;
    private JPanel buttonsPanel;
    private final JFileChooser fc;
    private String[] bdlNameFile;
    private boolean isBdlUploaded;
    private final List<String> featureFilesPath;
    private final List<String> featureFilesName;
    private static final String WELCOME = "Welcome to NaturalAPI Design!";
    private static final String ADD_FEATURE_FILES = "Please add one or more feature files.";
    private static final String ADD_BDL = "Consider loading a BDL in order to take full advantage of the potential of NaturalAPI Design.";
    private static final String LOADED_FEATURE = "Currently loaded feature files: ";
    private static final String LOADED_BDL = "Currently loaded BDL files: ";


    public GuiDesign(Controller c, DataPresenterGUI dp) {
        this.controller = c;
        this.dataPresenter = dp;
        this.fc = new JFileChooser("..\\NaturalAPI_Design\\gherkin_documents");
        this.fc.setMultiSelectionEnabled(true);
        this.log.setText(WELCOME + "\n\n" + ADD_FEATURE_FILES + "\n" + ADD_BDL + "\n");
        this.dataPresenter.attach(this);
        this.featureFilesPath = new ArrayList<>();
        this.featureFilesName = new ArrayList<>();
        this.bdlNameFile = new String[3];
        this.isBdlUploaded = false;

        genSuggestBtn.addActionListener(actionEvent -> {
            if (!featureFilesPath.isEmpty()) {
                new SuggestionGenerated(featureFilesName, featureFilesPath, controller, dataPresenter).createAndShowGUI();
            } else {
                JOptionPane.showMessageDialog(null, "Please, load feature file first!", "No feature loaded", JOptionPane.WARNING_MESSAGE);
            }
        });

        genSuggestBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                genSuggestBtn.setBackground(new Color(224, 91, 73));
            }
        });
        genSuggestBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                genSuggestBtn.setBackground(new Color(58, 84, 105));
            }
        });

        loadFeatureBtn.addActionListener(actionEvent -> {
            int returnVal = fc.showOpenDialog(null);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File[] files = fc.getSelectedFiles();
                for (File f : files) {
                    if (!featureFilesPath.contains(f.getAbsolutePath())) {
                        if (ViewUtility.isFeaturePathValid(f.getAbsolutePath())) {
                            featureFilesPath.add(f.getAbsolutePath());
                            featureFilesName.add(f.getName());
                        } else {
                            JOptionPane.showMessageDialog(null, "The file '" + f.getName() + "' is not a feature file.",
                                    "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "The file '" + f.getName() + "' has already been uploaded!",
                                "Notice", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                }
                logPrintStatus();
            }
        });

        loadFeatureBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                loadFeatureBtn.setBackground(new Color(224, 91, 73));
            }
        });
        loadFeatureBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                loadFeatureBtn.setBackground(new Color(58, 84, 105));
            }
        });


        loadBDLButton.addActionListener(actionEvent -> {
            int returnVal = fc.showOpenDialog(null);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File[] files = fc.getSelectedFiles();
                if (files.length == 3) {
                    for (int i = 0; i < files.length; i++) {
                        bdlNameFile[i] = files[i].getAbsolutePath();
                    }
                    controller.loadBdl(bdlNameFile);
                } else {
                    JOptionPane.showMessageDialog(null, "Abort: you have to select 3 files: predicates,verbs and nouns", "Bdl not loaded", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        loadBDLButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                loadBDLButton.setBackground(new Color(224, 91, 73));
            }
        });
        loadBDLButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                loadBDLButton.setBackground(new Color(58, 84, 105));
            }
        });

        removeFeatureBtn.addActionListener(e -> {
            if (!featureFilesPath.isEmpty()) {
                featureFilesPath.clear();
                featureFilesName.clear();
            } else {
                JOptionPane.showMessageDialog(null, "Nothing to remove: no feature files uploaded!\n", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
            logPrintStatus();
        });

        removeFeatureBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                removeFeatureBtn.setBackground(new Color(224, 91, 73));
            }
        });
        removeFeatureBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                removeFeatureBtn.setBackground(new Color(58, 84, 105));
            }
        });

        removeBdlBtn.addActionListener(e -> {
            if (isBdlUploaded) {
                controller.removeBdl();
            } else {
                JOptionPane.showMessageDialog(null, "Nothing to remove: no BDL uploaded!\n", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
            logPrintStatus();
        });

        removeBdlBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                removeBdlBtn.setBackground(new Color(224, 91, 73));
            }
        });
        removeBdlBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                removeBdlBtn.setBackground(new Color(58, 84, 105));
            }
        });
    }

    public void createAndShowGUI() {
        JFrame frame = new JFrame("NaturalAPI Design");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(mainPanel);
        frame.setPreferredSize(new Dimension(650, 550));
        frame.setIconImage(ViewUtility.getLogo());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    @Override
    public void update() {
        if (!dataPresenter.getMessage().equals(""))
            showOutput();
    }

    private void showOutput() {
        //if there are items in the array of bdl files but the bdl is not really being uploaded
        //or if the state of isBdlUpload differs from the one in the dataPresenter
        if ((!dataPresenter.isBdlLoaded() && Arrays.stream(bdlNameFile).allMatch(Objects::nonNull))
                || (dataPresenter.isBdlLoaded() != isBdlUploaded)) {
            isBdlUploaded = dataPresenter.isBdlLoaded();
            logPrintStatus();
            log.setText(dataPresenter.getMessage() + "\n\n" + log.getText());
        }
    }

    private void logPrintStatus() {

        if (featureFilesName.isEmpty() && isBdlUploaded) {
            log.setText(ADD_FEATURE_FILES + "\n");
            log.append("\n" + LOADED_BDL + "\n\n");
            for (int i = 0; i < bdlNameFile.length; i++) {
                log.append("- " + bdlNameFile[i].substring(bdlNameFile[i].lastIndexOf(File.separatorChar) + 1) + "\n");
            }
        } else if (featureFilesName.isEmpty()) {
            log.setText(ADD_FEATURE_FILES + "\n");
            log.append(ADD_BDL + "\n");
        } else if (!isBdlUploaded) {
            log.setText(LOADED_FEATURE + "\n\n");
            featureFilesName.forEach(fileName -> log.append("- " + fileName + "\n"));
            log.append("\n" + ADD_BDL);
        } else {
            log.setText(LOADED_FEATURE + "\n\n");
            featureFilesName.forEach(fileName -> log.append("- " + fileName + "\n"));
            log.append("\n" + LOADED_BDL + "\n\n");
            for (int i = 0; i < bdlNameFile.length; i++) {
                log.append("- " + bdlNameFile[i].substring(bdlNameFile[i].lastIndexOf(File.separatorChar) + 1) + "\n");
            }
            log.append("\nGood to go! Click the 'Generate suggestions for BAL' button.");
        }
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(6, 1, new Insets(0, 0, 0, 0), -1, 2));
        mainPanel.setAlignmentX(0.5f);
        mainPanel.setAlignmentY(0.5f);
        mainPanel.setAutoscrolls(false);
        mainPanel.setBackground(new Color(-1));
        mainPanel.setEnabled(true);
        mainPanel.setForeground(new Color(-1));
        log = new JTextArea();
        log.setAutoscrolls(true);
        log.setBackground(new Color(-1));
        log.setEditable(false);
        Font logFont = this.$$$getFont$$$(null, Font.BOLD, -1, log.getFont());
        if (logFont != null) log.setFont(logFont);
        log.setMargin(new Insets(5, 5, 5, 5));
        mainPanel.add(log, new com.intellij.uiDesigner.core.GridConstraints(5, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(4, 1, new Insets(0, 0, 0, 0), -1, -1));
        buttonsPanel.setBackground(new Color(-12954519));
        mainPanel.add(buttonsPanel, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 4, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        loadFeatureBtn = new JButton();
        loadFeatureBtn.setBackground(new Color(-12954519));
        Font loadFeatureBtnFont = this.$$$getFont$$$(null, -1, 14, loadFeatureBtn.getFont());
        if (loadFeatureBtnFont != null) loadFeatureBtn.setFont(loadFeatureBtnFont);
        loadFeatureBtn.setForeground(new Color(-1));
        loadFeatureBtn.setText("Load Feature File");
        buttonsPanel.add(loadFeatureBtn, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        genSuggestBtn = new JButton();
        genSuggestBtn.setBackground(new Color(-12954519));
        Font genSuggestBtnFont = this.$$$getFont$$$(null, -1, 14, genSuggestBtn.getFont());
        if (genSuggestBtnFont != null) genSuggestBtn.setFont(genSuggestBtnFont);
        genSuggestBtn.setForeground(new Color(-1));
        genSuggestBtn.setText("Generate suggestion for BAL");
        buttonsPanel.add(genSuggestBtn, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        loadBDLButton = new JButton();
        loadBDLButton.setBackground(new Color(-12954519));
        Font loadBDLButtonFont = this.$$$getFont$$$(null, -1, 14, loadBDLButton.getFont());
        if (loadBDLButtonFont != null) loadBDLButton.setFont(loadBDLButtonFont);
        loadBDLButton.setForeground(new Color(-1));
        loadBDLButton.setSelected(false);
        loadBDLButton.setText("Load BDL");
        buttonsPanel.add(loadBDLButton, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        removeFilesBtnPanel = new JPanel();
        removeFilesBtnPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), 2, -1, true, false));
        buttonsPanel.add(removeFilesBtnPanel, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        removeFeatureBtn = new JButton();
        removeFeatureBtn.setBackground(new Color(-12954519));
        Font removeFeatureBtnFont = this.$$$getFont$$$(null, -1, 14, removeFeatureBtn.getFont());
        if (removeFeatureBtnFont != null) removeFeatureBtn.setFont(removeFeatureBtnFont);
        removeFeatureBtn.setForeground(new Color(-1));
        removeFeatureBtn.setMaximumSize(new Dimension(250, 30));
        removeFeatureBtn.setPreferredSize(new Dimension(250, 30));
        removeFeatureBtn.setText("Remove loaded feature");
        removeFilesBtnPanel.add(removeFeatureBtn, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        removeBdlBtn = new JButton();
        removeBdlBtn.setBackground(new Color(-12954519));
        removeBdlBtn.setEnabled(true);
        Font removeBdlBtnFont = this.$$$getFont$$$(null, -1, 14, removeBdlBtn.getFont());
        if (removeBdlBtnFont != null) removeBdlBtn.setFont(removeBdlBtnFont);
        removeBdlBtn.setForeground(new Color(-1));
        removeBdlBtn.setMaximumSize(new Dimension(250, 30));
        removeBdlBtn.setMinimumSize(new Dimension(143, 30));
        removeBdlBtn.setPreferredSize(new Dimension(250, 30));
        removeBdlBtn.setText("Remove loaded BDL");
        removeFilesBtnPanel.add(removeBdlBtn, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        titleText = new JLabel();
        Font titleTextFont = this.$$$getFont$$$(null, Font.BOLD, 16, titleText.getFont());
        if (titleTextFont != null) titleText.setFont(titleTextFont);
        titleText.setForeground(new Color(-2073783));
        titleText.setText("NaturalAPI Design");
        mainPanel.add(titleText, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        titleText.setLabelFor(log);
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        return new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return mainPanel;
    }
}
