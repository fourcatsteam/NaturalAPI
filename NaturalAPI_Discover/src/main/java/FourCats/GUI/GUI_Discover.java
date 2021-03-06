package FourCats.GUI;

import FourCats.DataStructure.TableRow;
import FourCats.DataStructure.WordCounter;
import FourCats.Frameworks.FileSystemAccess;
import FourCats.InterfaceAdapters.Controller;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.LinkedList;

import FourCats.InterfaceAdapters.DataPresenter;
import FourCats.Observer.Observer;

public class GUI_Discover extends JPanel implements Observer{
    private JPanel panel1;
    private JFileChooser txtChooser;
    private JFileChooser destinationFolderChooser;
    private JFileChooser sourceFolderChooser;
    private JTextArea log;
    private JButton createBDLbtn;
    private JButton addDocumentBtn;
    private JButton removeDocumentBtn;
    private JButton viewBDLBtn;
    private JScrollPane scrollNouns;
    private JScrollPane scrollVerbs;
    private JScrollPane scrollPredicates;
    private JScrollPane logScroll;
    private JTable tableNouns;
    private JTable tableVerbs;
    private JTable tablePredicates;
    private Boolean areFilesLoaded;
    private Controller controller;
    private DataPresenter datapresenter;
    private FileSystemAccess fileSystemAccess;
    private LinkedList<String> isKeyString;


    public GUI_Discover(Controller c, DataPresenter d, FileSystemAccess fs) {
        this.isKeyString = new LinkedList<>();
        this.areFilesLoaded = false;
        this.controller = c;
        this.datapresenter= d;
        this.fileSystemAccess = fs;
        datapresenter.attach(this);
        txtChooser = new JFileChooser("..\\NaturalAPI_Discover\\txt_documents");
        txtChooser.setMultiSelectionEnabled(true);
        txtChooser.setDialogTitle("Select txt documents");
        destinationFolderChooser = new JFileChooser(".\\BDL");
        destinationFolderChooser.setDialogTitle("Select destination folder");
        destinationFolderChooser.setMultiSelectionEnabled(false);
        destinationFolderChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        sourceFolderChooser = new JFileChooser(".\\BDL");
        sourceFolderChooser.setDialogTitle("Select source folder");
        sourceFolderChooser.setMultiSelectionEnabled(false);
        sourceFolderChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        createBDLbtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                createBDLbtn.setBackground(new Color( 224,91,73));
               // createBDLbtn.setBorderPainted(false);
            }
        });
        createBDLbtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                createBDLbtn.setBackground(new Color(58,84,105));
               // createBDLbtn.setBorderPainted(true);
            }
        });
        createBDLbtn.addActionListener(actionEvent -> {
            int returnVal = destinationFolderChooser.showOpenDialog(this);
            if(returnVal == JFileChooser.APPROVE_OPTION) {
                File directory = destinationFolderChooser.getSelectedFile();
                String path = directory.getPath().replaceAll("\\\\", "/");
                fileSystemAccess.setSaveFolder(path);

                String name = this.chooseName("Choose the name of the BDL you want to create");
                if (name != null) {
                    log.append("Chosen name: " + name + "\n");

                    int returnValue = txtChooser.showOpenDialog(GUI_Discover.this);
                    if (returnValue == JFileChooser.APPROVE_OPTION) {
                        File[] files = txtChooser.getSelectedFiles();
                        LinkedList<String> nameTitleList = new LinkedList<>();
                        for (File f : files) {
                            log.append("Selected: " + f.getName() + "." + "\n");
                            nameTitleList.add(f.getName());
                        }
                        String sourceDir = txtChooser.getCurrentDirectory().getPath().replaceAll("\\\\", "/");
                        fileSystemAccess.setTxtSourceFolder(sourceDir);

                        controller.createBdl(name, nameTitleList);
                    } else {
                        log.append("File selection cancelled.\n");
                    }
                } else {
                    log.append("Operation cancelled.\n");
                }
            }else{
                log.append("Destination folder selection cancelled.\n");
            }
        });

        addDocumentBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                addDocumentBtn.setBackground(new Color( 224,91,73));
                //addDocumentBtn.setBorderPainted(false);
            }
        });
        addDocumentBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                addDocumentBtn.setBackground(new Color(58,84,105));
               // addDocumentBtn.setBorderPainted(true);
            }
        });
        addDocumentBtn.addActionListener(actionEvent -> {
            int returnVal = sourceFolderChooser.showOpenDialog(this);
            if(returnVal == JFileChooser.APPROVE_OPTION) {
                File directory = sourceFolderChooser.getSelectedFile();
                String path = directory.getPath().replaceAll("\\\\", "/");
                fileSystemAccess.setBdlSourceFolder(path);
                fileSystemAccess.setSaveFolder(path);

                String name = this.chooseName("Choose the name of the BDL you want to update");
                if (name != null) {
                    log.append("Chosen name: " + name + "\n");

                    int returnValue = txtChooser.showOpenDialog(GUI_Discover.this);
                    if (returnValue == JFileChooser.APPROVE_OPTION) {
                        File[] files = txtChooser.getSelectedFiles();
                        LinkedList<String> nameTitleList = new LinkedList<>();
                        for (File f : files) {
                            log.append("Selected: " + f.getName() + "." + "\n");
                            nameTitleList.add(f.getName());
                        }
                        String sourceDir = txtChooser.getCurrentDirectory().getPath().replaceAll("\\\\", "/");
                        fileSystemAccess.setTxtSourceFolder(sourceDir);

                        controller.addDocument(name, nameTitleList);
                    } else {
                        log.append("File selection cancelled.\n");
                    }
                } else {
                    log.append("Operation canceled\n");
                }
            }else{
                log.append("Source folder selection cancelled.\n");
            }
        });

        removeDocumentBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                removeDocumentBtn.setBackground(new Color( 224,91,73));
                //removeDocumentBtn.setBorderPainted(false);
            }
        });
        removeDocumentBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                removeDocumentBtn.setBackground(new Color(58,84,105));
                //removeDocumentBtn.setBorderPainted(true);
            }
        });

        removeDocumentBtn.addActionListener(actionEvent -> {
            int returnVal = sourceFolderChooser.showOpenDialog(this);
            if(returnVal == JFileChooser.APPROVE_OPTION) {
                File directory = sourceFolderChooser.getSelectedFile();
                String path = directory.getPath().replaceAll("\\\\", "/");
                fileSystemAccess.setBdlSourceFolder(path);
                fileSystemAccess.setSaveFolder(path);

                String name = this.chooseName("Choose the name of the BDL you want to update");
                if (name != null) {
                    log.append("Chosen name: " + name + "\n");

                    txtChooser.setCurrentDirectory(directory);
                    int returnValue = txtChooser.showOpenDialog(GUI_Discover.this);
                    if (returnValue == JFileChooser.APPROVE_OPTION) {
                        File[] files = txtChooser.getSelectedFiles();
                        LinkedList<String> nameTitleList = new LinkedList<>();
                        for (File f : files) {
                            log.append("Selected: " + f.getName() + "." + "\n");
                            nameTitleList.add(f.getName());
                        }
                        String sourceDir = txtChooser.getCurrentDirectory().getPath().replaceAll("\\\\", "/");
                        fileSystemAccess.setTxtSourceFolder(sourceDir);

                        controller.removeDocument(name, nameTitleList);
                    } else {
                        log.append("File selection cancelled\n");
                    }
                } else {
                log.append("Operation cancelled\n");
                }
            }else {
                log.append("Source folder selection cancelled\n");
            }
        });

        viewBDLBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                viewBDLBtn.setBackground(new Color( 224,91,73));
                //viewBDLBtn.setBorderPainted(false);
            }
        });
        viewBDLBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                viewBDLBtn.setBackground(new Color(58,84,105));
                //viewBDLBtn.setBorderPainted(true);
            }
        });

        viewBDLBtn.addActionListener(actionEvent -> {
            int returnVal = sourceFolderChooser.showOpenDialog(this);
            if(returnVal == JFileChooser.APPROVE_OPTION) {
                File directory = sourceFolderChooser.getSelectedFile();
                String path = directory.getPath().replaceAll("\\\\","/");
                fileSystemAccess.setBdlSourceFolder(path);
                String name = chooseName("Choose the name of the BDL you want to view");
                if(name != null) {
                    log.append("Chosen name: " + name + "\n");
                    String[] choices = {"Show all","More probable words","First 15 words"};
                    String choice = (String) JOptionPane.showInputDialog(this,
                            "Select a type of visualization",
                            "Select type",
                            JOptionPane.PLAIN_MESSAGE,
                            null,
                            choices,
                            choices[0]
                    );

                    if (choice != null) {
                        Integer visualizationType = Arrays.asList(choices).indexOf(choice);
                        controller.viewBdl(name, visualizationType);
                    } else {
                        log.append("Operation cancelled\n");
                    }
                } else {
                    log.append("Operation cancelled\n");
                }
            }else {
                log.append("Source folder selection cancelled\n");
            }
        });

        //Editing behaviour Ctrl+C keyboard copy
        KeyStroke stroke = KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK, false);
        tableNouns.registerKeyboardAction(actionEvent -> doCopy(tableNouns),"Copy",stroke,JComponent.WHEN_FOCUSED);
        tableVerbs.registerKeyboardAction(actionEvent -> doCopy(tableVerbs),"Copy",stroke,JComponent.WHEN_FOCUSED);
        tablePredicates.registerKeyboardAction(actionEvent -> doCopy(tablePredicates),"Copy",stroke,JComponent.WHEN_FOCUSED);

        //Setting lose focus when clicking outside a table
        Toolkit.getDefaultToolkit().addAWTEventListener(awtEvent -> {
            if(awtEvent.getID() == MouseEvent.MOUSE_CLICKED) {
                MouseEvent mevent = (MouseEvent) awtEvent;
                if(!mevent.getSource().equals(tableNouns)) {
                    tableNouns.clearSelection();
                }
                if(!mevent.getSource().equals(tableVerbs)) {
                    tableVerbs.clearSelection();
                }
                if(!mevent.getSource().equals(tablePredicates)) {
                    tablePredicates.clearSelection();
                }
            }
        }, AWTEvent.MOUSE_EVENT_MASK);
    }

    private void doCopy(JTable t) {
        int col = t.getSelectedColumn();
        int row = t.getSelectedRow();
        if (col != -1 && row != -1) {
            Object value = t.getValueAt(row, col);
            String data;
            if (value == null) {
                data = "";
            } else {
                data = value.toString();
            }

            final StringSelection selection = new StringSelection(data);

            final Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(selection, selection);
        }
    }

    private String chooseName(String message) {
        String name = "";
        while(name!=null && name.length()<=0) {
            name = (String)JOptionPane.showInputDialog(this,
                    message,
                    "Choose name",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    null,
                    null
            );
        }
        return name;
    }

    private void clearTable(){
        //Clear the TextArea
        tableNouns.setModel(new DefaultTableModel());
        tableVerbs.setModel(new DefaultTableModel());
        tablePredicates.setModel(new DefaultTableModel());
    }

    public void showGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("NaturalAPI Discover");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Add content to the window.
        frame.add(panel1);
        frame.setPreferredSize(new Dimension(800,500));

        //set logo
        final URL url = Thread.currentThread().getContextClassLoader().getResource("images/logo.png");
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage(url));

        //Display the window.
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void showResult(){
        clearTable();
        if(!datapresenter.getError()) {
            DefaultTableModel nounModel = new DefaultTableModel(new Object[]{"Noun", "Frequency"}, 0);
            datapresenter.getBdlNouns().stream()
                    .forEach(tableRow -> {
                        nounModel.addRow(new Object[]{tableRow.getWord(), tableRow.getFrequency()});
                    });
            tableNouns.setModel(nounModel);

            DefaultTableModel verbsModel = new DefaultTableModel(new Object[]{"Verb", "Frequency"}, 0);
            datapresenter.getBdlVerbs().stream()
                    .forEach(tableRow -> {
                        verbsModel.addRow(new Object[]{tableRow.getWord(), tableRow.getFrequency()});
                    });
            tableVerbs.setModel(verbsModel);

            DefaultTableModel predModel = new DefaultTableModel(new Object[]{"Predicate", "Frequency"}, 0);
            datapresenter.getBdlPredicates().stream()
                    .forEach(tableRow -> {
                        predModel.addRow(new Object[]{tableRow.getWord(), tableRow.getFrequency()});
                        if(tableRow.isKeyValue()){
                            isKeyString.add(tableRow.getWord());
                        }
                    });
            
            tablePredicates.setModel(predModel);
            tablePredicates.getColumn("Predicate").setCellRenderer(
                    new DefaultTableCellRenderer() {
                        @Override
                        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                            DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                            for (String s : isKeyString) {
                                if (s.equalsIgnoreCase(value.toString())) {
                                    renderer.setForeground(Color.GREEN);
                                    return renderer;
                                }else{
                                    renderer.setForeground(Color.BLACK);
                                }
                            }
                            return renderer;
                        }

                    }
            );

            tablePredicates.getColumn("Frequency").setPreferredWidth(10);
        }

        log.append(datapresenter.getMessage()+"\n");
    }

    @Override
    public void update() {
        showResult();
    }

}
