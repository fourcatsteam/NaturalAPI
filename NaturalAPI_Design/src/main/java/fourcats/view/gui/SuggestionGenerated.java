package fourcats.view.gui;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import fourcats.datastructure.observer.Observer;
import fourcats.interfaceadapters.Controller;
import fourcats.interfaceadapters.DataPresenterGUI;
import fourcats.view.utilities.ViewUtility;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.View;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class SuggestionGenerated implements Observer {
    private JPanel mainPanel;
    private JPanel panelButtons;
    private JPanel panelSuggestions;
    private JPanel panelInScrollPanel;
    private JScrollPane scrollPanel;
    private JButton generateBalButton;
    private JButton addFeatureButton;
    private final Controller contr;
    private final DataPresenterGUI dataPresenter;
    private final GridBagConstraints gridConstraint;
    private final GridBagLayout gridBagLayout;
    private int currentScenarioId;
    private int gridX = 0;
    private int gridY = 0;
    private final List<String> lFeatureNames;
    private final List<String> lFeaturePaths;

    public SuggestionGenerated(List<String> featureNames, List<String> featurePaths, Controller controller, DataPresenterGUI dataPresenter) {
        this.contr = controller;
        this.dataPresenter = dataPresenter;
        this.dataPresenter.attach(this);
        this.currentScenarioId = -1;
        this.lFeatureNames = new ArrayList<>();
        this.lFeaturePaths = new ArrayList<>();
        this.lFeatureNames.addAll(featureNames);
        this.lFeaturePaths.addAll(featurePaths);
        gridBagLayout = new GridBagLayout();
        gridConstraint = new GridBagConstraints();
        panelInScrollPanel.setLayout(gridBagLayout);
        gridConstraint.fill = GridBagConstraints.HORIZONTAL;

        scrollPanel = new JScrollPane(panelInScrollPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        panelButtons.setLayout(new GridLayout(2, 1));
        panelButtons.add(generateBalButton);
        panelButtons.add(addFeatureButton);

        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        mainPanel.add(scrollPanel, BorderLayout.CENTER);
        mainPanel.add(panelButtons, BorderLayout.SOUTH);


        generateBalButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser("..\\NaturalAPI_Design\\BAL");
            int returnVal = fileChooser.showSaveDialog(null);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                String path = file.getAbsolutePath();
                if (!path.equals("")) {
                    contr.generateBAL(path);
                }
            }
        });

        generateBalButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                generateBalButton.setBackground(new Color(224, 91, 73));
            }
        });
        generateBalButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                generateBalButton.setBackground(new Color(58, 84, 105));
            }
        });

        addFeatureButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                addFeatureButton.setBackground(new Color(224, 91, 73));
            }
        });
        addFeatureButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                addFeatureButton.setBackground(new Color(58, 84, 105));
            }
        });

        addFeatureButton.addActionListener(e -> {
            List<String> newPaths = new ArrayList<>(); //contains paths of new files
            JFileChooser fileChooser = new JFileChooser("..\\NaturalAPI_Design\\gherkin_documents");
            fileChooser.setMultiSelectionEnabled(true);
            int returnVal = fileChooser.showOpenDialog(null);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File[] files = fileChooser.getSelectedFiles();
                for (File f : files) {
                    if (!lFeaturePaths.contains(f.getAbsolutePath())) {
                        if (ViewUtility.isFeaturePathValid(f.getAbsolutePath())) {
                            newPaths.add(f.getAbsolutePath());
                            this.lFeatureNames.add(f.getName());
                            this.lFeaturePaths.add(f.getAbsolutePath());
                        } else {
                            JOptionPane.showMessageDialog(null, "The file '" + f.getName() + "' is not a feature file.",
                                    "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "The file '" + f.getName() + "' has already been uploaded!",
                                "Notice", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
            if (!newPaths.isEmpty()) {
                contr.generateSuggestions(newPaths, false); //generate suggestions only for the new files
            }

        });
        contr.generateSuggestions(featurePaths, true);
    }

    public void createAndShowGUI() {
        JFrame frame = new JFrame("NaturalAPI Design - Suggestions");
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                //detach from the subject so that the instance of the class will be destroy
                dataPresenter.detach(SuggestionGenerated.this);
            }
        });
        frame.add(mainPanel);
        frame.setPreferredSize(new Dimension(1400, 1000));
        frame.setIconImage(ViewUtility.getLogo());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void initScenario() {
        JPanel panelScenario = new JPanel();
        panelSuggestions = new JPanel();

        //add a vertical spacer in panelInScrollPanel if it's not the first scenario
        if (currentScenarioId != -1) {
            gridConstraint.gridx = gridX;
            gridConstraint.gridy = gridY;
            Component box = Box.createRigidArea(new Dimension(0, 50));
            gridBagLayout.setConstraints(box, gridConstraint);
            panelInScrollPanel.add(box);
        }
        currentScenarioId++;

        //include panel suggestion in a box with a button which let to manually add a suggestion
        Box suggestionsAndAddButtonBox = Box.createVerticalBox();
        JButton addSuggestionButton = new AddSuggestionButtonWidget(contr, Integer.toString(currentScenarioId)).getButtonWidget();
        suggestionsAndAddButtonBox.add(panelSuggestions);
        suggestionsAndAddButtonBox.add(addSuggestionButton);

        //add the box to the panelInScroll panel after settings constraints x(column) and y(row) values
        this.gridX = 0;
        this.gridY++;
        gridConstraint.gridx = this.gridX;
        gridConstraint.gridy = this.gridY;
        panelSuggestions.setLayout(new BoxLayout(panelSuggestions, BoxLayout.PAGE_AXIS));
        gridBagLayout.setConstraints(suggestionsAndAddButtonBox, gridConstraint);
        panelInScrollPanel.add(suggestionsAndAddButtonBox);


        //add panel scenario in panelInScrollPanel with a new scenarioWidget
        this.gridX++;
        gridConstraint.gridx = this.gridX;
        gridConstraint.gridy = this.gridY;
        gridBagLayout.setConstraints(panelScenario, gridConstraint);
        panelInScrollPanel.add(panelScenario);
        new ScenarioWidget(panelScenario, getFeatureName(), dataPresenter.getActor(),
                dataPresenter.getScenarioContent());

        //get ready for next scenario by adding a "row" to the grid (gridY++)
        this.gridY++;
    }

    @Override
    public void update() {
        //if the suggestions widget needs a complete refresh to be updated with the model, then this needs to be execute
        //to fully recreate all suggestion widgets and scenario widgets in panelInScrollPanel
        if (dataPresenter.isSuggestionsRefreshNeeded()) {
            panelInScrollPanel.removeAll();
            panelInScrollPanel.revalidate();
            panelInScrollPanel.repaint();
            currentScenarioId = -1;
        }
        //init a new scenario if the id of the current scenario is different from the one being read from the dataPresenter
        if (Integer.parseInt(dataPresenter.getScenarioId()) != currentScenarioId) {
            initScenario();
        }
        //the update method can be call for different reasons but that doesn't mean we always need to create a new Suggestion widget
        if (dataPresenter.isSuggestionToAdd()) {
            if (dataPresenter.getActionName() != null)
                new SuggestionWidget(panelSuggestions, contr, dataPresenter);
            else {
                JLabel label = new JLabel("No additional suggestion found in this scenario. You can add actions manually by clicking on the '+' button.");
                Box labelBox = Box.createHorizontalBox();
                labelBox.add(Box.createHorizontalGlue());
                labelBox.add(label);
                labelBox.add(Box.createHorizontalGlue());
                labelBox.add(Box.createVerticalStrut(35));
                panelSuggestions.add(labelBox);
            }
        }
        if (!dataPresenter.getMessage().equals("")) {
            String message = dataPresenter.getMessage();
            boolean isError = dataPresenter.isOkOperation();
            //in this way we can execute operations while the notification is shown
            SwingUtilities.invokeLater(() -> {
                //check if the operation was successful then show information message or error message
                if (isError)
                    JOptionPane.showMessageDialog(null, message);
                else
                    JOptionPane.showMessageDialog(null, message, "Error!", JOptionPane.ERROR_MESSAGE);
            });
        }
    }

    private String getFeatureName() {
        //the "name" of the feature from the dataPresenter is coming as a path so this method it's useful to get the name of the feature file
        for (String fPath : lFeaturePaths) {
            if (fPath.equals(dataPresenter.getFeaturePath())) {
                return lFeatureNames.get(lFeaturePaths.indexOf(dataPresenter.getFeaturePath()));
            }
        }
        return "";
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
        mainPanel.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        scrollPanel = new JScrollPane();
        mainPanel.add(scrollPanel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        panelInScrollPanel = new JPanel();
        panelInScrollPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panelInScrollPanel.setFocusCycleRoot(true);
        scrollPanel.setViewportView(panelInScrollPanel);
        panelButtons = new JPanel();
        panelButtons.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        panelButtons.setAutoscrolls(false);
        panelButtons.setBackground(new Color(-4473925));
        mainPanel.add(panelButtons, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        generateBalButton = new JButton();
        generateBalButton.setBackground(new Color(-12954519));
        Font generateBalButtonFont = this.$$$getFont$$$(null, -1, 14, generateBalButton.getFont());
        if (generateBalButtonFont != null) generateBalButton.setFont(generateBalButtonFont);
        generateBalButton.setForeground(new Color(-1));
        generateBalButton.setText("Generate BAL");
        panelButtons.add(generateBalButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        addFeatureButton = new JButton();
        addFeatureButton.setBackground(new Color(-12954519));
        Font addFeatureButtonFont = this.$$$getFont$$$(null, -1, 14, addFeatureButton.getFont());
        if (addFeatureButtonFont != null) addFeatureButton.setFont(addFeatureButtonFont);
        addFeatureButton.setForeground(new Color(-1));
        addFeatureButton.setText("Add another feature");
        panelButtons.add(addFeatureButton, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
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