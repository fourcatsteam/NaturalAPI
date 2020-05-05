package FourCats.GUI;

import FourCats.Frameworks.FileSystemAccess;
import FourCats.InterfaceAdapters.Controller;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;

import FourCats.InterfaceAdapters.DataPresenter;
import FourCats.InterfaceAdapters.DataPresenter_GUI;
import FourCats.Observer.Observer;

public class GUI_Discover extends JPanel implements Observer{
    //private JButton loadFileBtn;
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
    private LinkedList<String> nameTitleList;

    public GUI_Discover(Controller c, DataPresenter d, FileSystemAccess fs) {
        this.areFilesLoaded = false;
        this.controller = c;
        this.datapresenter= d;
        this.fileSystemAccess = fs;
        datapresenter.attach(this);
        nameTitleList = new LinkedList<>();
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

        /*loadFileBtn.addActionListener(actionEvent -> {
            int returnValue = txtChooser.showOpenDialog(GUI_Discover.this);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File[] files = txtChooser.getSelectedFiles();
                for(File f: files){
                    log.append("Selected: " + f.getName() + "." + "\n");
                    nameTitleList.add(f.getName());
                }
                this.areFilesLoaded = true;
                String sourceDir = txtChooser.getCurrentDirectory().getPath().replaceAll("\\\\","/");
                fileSystemAccess.setTxtSourceFolder(sourceDir);
            } else {
                log.append("Open command cancelled by user." + "\n");
            }
            log.setCaretPosition(log.getDocument().getLength());
        });*/

        createBDLbtn.addActionListener(actionEvent -> {
            int returnVal = destinationFolderChooser.showOpenDialog(this);
            if(returnVal == JFileChooser.APPROVE_OPTION) {
                File directory = destinationFolderChooser.getSelectedFile();
                String path = directory.getPath().replaceAll("\\\\", "/");
                fileSystemAccess.setSaveFolder(path);

                int returnValue = txtChooser.showOpenDialog(GUI_Discover.this);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File[] files = txtChooser.getSelectedFiles();
                    for(File f: files){
                        log.append("Selected: " + f.getName() + "." + "\n");
                        nameTitleList.add(f.getName());
                    }
                    this.areFilesLoaded = true;
                    String sourceDir = txtChooser.getCurrentDirectory().getPath().replaceAll("\\\\","/");
                    fileSystemAccess.setTxtSourceFolder(sourceDir);

                    String result = this.choosing(1);
                    if (result != null) {
                        controller.createBdl(result, nameTitleList);
                        nameTitleList.clear();
                        this.areFilesLoaded = false;
                    } else {
                        if (this.areFilesLoaded) {
                            log.append("Your files are still available.\n");
                        }
                    }
                } else {
                    log.append("Open command cancelled by user." + "\n");
                }
            }else{
                log.append("Selection canceled\n");
            }
        });

        addDocumentBtn.addActionListener(actionEvent -> {
            int returnVal = sourceFolderChooser.showOpenDialog(this);
            if(returnVal == JFileChooser.APPROVE_OPTION) {
                File directory = sourceFolderChooser.getSelectedFile();
                String path = directory.getPath().replaceAll("\\\\", "/");
                fileSystemAccess.setBdlSourceFolder(path);
                fileSystemAccess.setSaveFolder(path);

                int returnValue = txtChooser.showOpenDialog(GUI_Discover.this);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File[] files = txtChooser.getSelectedFiles();
                    for (File f : files) {
                        log.append("Selected: " + f.getName() + "." + "\n");
                        nameTitleList.add(f.getName());
                    }
                    this.areFilesLoaded = true;
                    String sourceDir = txtChooser.getCurrentDirectory().getPath().replaceAll("\\\\", "/");
                    fileSystemAccess.setTxtSourceFolder(sourceDir);

                    String result = this.choosing(2);
                    if (result != null) {
                        controller.addDocument(result, nameTitleList);
                        nameTitleList.clear();
                        this.areFilesLoaded = false;
                    } else {
                        if (this.areFilesLoaded) {
                            log.append("Your files are still available.\n");
                        }
                    }
                } else {
                    log.append("Open command cancelled by user." + "\n");
                }
            }else{
                log.append("Selection canceled\n");
            }
        });

        removeDocumentBtn.addActionListener(actionEvent -> {
            int returnVal = sourceFolderChooser.showOpenDialog(this);
            if(returnVal == JFileChooser.APPROVE_OPTION) {
                File directory = sourceFolderChooser.getSelectedFile();
                String path = directory.getPath().replaceAll("\\\\", "/");
                fileSystemAccess.setBdlSourceFolder(path);
                fileSystemAccess.setSaveFolder(path);

                txtChooser.setCurrentDirectory(directory);
                int returnValue = txtChooser.showOpenDialog(GUI_Discover.this);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File[] files = txtChooser.getSelectedFiles();
                    for (File f : files) {
                        log.append("Selected: " + f.getName() + "." + "\n");
                        nameTitleList.add(f.getName());
                    }
                    this.areFilesLoaded = true;
                    String sourceDir = txtChooser.getCurrentDirectory().getPath().replaceAll("\\\\", "/");
                    fileSystemAccess.setTxtSourceFolder(sourceDir);

                    String result = this.choosing(2);
                    if (result != null) {
                        controller.removeDocument(result, nameTitleList);
                        nameTitleList.clear();
                        this.areFilesLoaded = false;
                    } else {
                        if (this.areFilesLoaded) {
                            log.append("Your files are still available.\n");
                        }
                    }
                } else {
                    log.append("Open command cancelled by user." + "\n");
                }
            }else {
                log.append("Selection canceled\n");
            }
        });

        viewBDLBtn.addActionListener(actionEvent -> {
            int returnVal = sourceFolderChooser.showOpenDialog(this);
            if(returnVal == JFileChooser.APPROVE_OPTION) {
                File directory = sourceFolderChooser.getSelectedFile();
                String path = directory.getPath().replaceAll("\\\\","/");
                fileSystemAccess.setBdlSourceFolder(path);
                String result = (String)JOptionPane.showInputDialog(this,
                        "Choose the name of the bdl that you want to view",
                        "Choose name",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        null,
                        null );
                if(result != null) {

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
                        controller.viewBdl(result, visualizationType);
                    }
                } else {
                    log.append("Operation canceled\n");
                }
            }else {
                log.append("Selection canceled\n");
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

    private String choosing(int useCase){
        String result = null;
        String text = useCase == 1 ? "Choose a name for the BDL" : "Choose the name of the BDL that you want update";
        if(!this.areFilesLoaded){
            JOptionPane.showMessageDialog(this,"Please, load at least one .txt file, by pressing \"Load File\"","Load file error",JOptionPane.ERROR_MESSAGE);
        }else {
            result = (String)JOptionPane.showInputDialog(this,
                    text,
                    "Choose name",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    null,
                    null            );

            if(result != null && result.length() > 0){
                log.append("Name choosed: " + result +"\n");
            }else {
                log.append("No name choosed. Please choose one.\n");
                return null;
            }
        }
        return result;
    }

    private void clearing(){
        //Clear the TextArea
        log.selectAll();
        log.replaceSelection("");
    }

    public void showGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("NaturalAPI Discover");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Add content to the window.
        frame.add(panel1);
        frame.setPreferredSize(new Dimension(800,500));

        //Display the window.
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void showResult(){
        DefaultTableModel nounModel = new DefaultTableModel(new Object[]{"Noun","Frequency"},0);
        datapresenter.getBdlNouns().stream()
                .forEach(tableRow -> {
                    nounModel.addRow(new Object[]{tableRow.getWord(),tableRow.getFrequency()});
                });
        tableNouns.setModel(nounModel);

        DefaultTableModel verbsModel = new DefaultTableModel(new Object[]{"Verb","Frequency"},0);
        datapresenter.getBdlVerbs().stream()
                .forEach(tableRow -> {
                    verbsModel.addRow(new Object[]{tableRow.getWord(),tableRow.getFrequency()});
                });
        tableVerbs.setModel(verbsModel);

        DefaultTableModel predModel = new DefaultTableModel(new Object[]{"Predicate","Frequency"},0);
        datapresenter.getBdlPredicates().stream()
                .forEach(tableRow -> {
                    predModel.addRow(new Object[]{tableRow.getWord(),tableRow.getFrequency()});
                });

        tablePredicates.setModel(predModel);
        tablePredicates.getColumn("Frequency").setPreferredWidth(10);

        log.append(datapresenter.getMessage()+"\n");
    }

    @Override
    public void update() {
        showResult();
    }

}
