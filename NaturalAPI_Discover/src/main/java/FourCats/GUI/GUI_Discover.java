package FourCats.GUI;

import FourCats.InterfaceAdapters.Controller;
import FourCats.InterfaceAdapters.DataPresenter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;

import FourCats.InterfaceAdapters.DataPresenter_GUI;
import FourCats.Observer.Observer;

public class GUI_Discover extends JPanel implements Observer{
    private JButton loadFileBtn;
    private JPanel panel1;
    private JFileChooser fc;
    private JTextArea log;
    private JButton createBDLbtn;
    private JButton addDocumentBtn;
    private JButton removeDococumentBtn;
    private JButton viewBDLBtn;
    private JScrollPane scrollNouns;
    private JScrollPane scrollVerbs;
    private JScrollPane scrollPredicates;
    private JTextPane panelVerbs;
    private JTextPane panelPredicates;
    private JTextPane panelNouns;
    private JScrollPane logScroll;
    private JTable tableNouns;
    private JTable tableVerbs;
    private JTable tablePredicates;
    private Boolean areFilesLoaded;

    private Controller controller;
    private DataPresenter_GUI datapresenter;
    private LinkedList<String> nameTitleList;

    public GUI_Discover(Controller c, DataPresenter_GUI d) {
        this.areFilesLoaded = false;
        this.controller = c;
        this.datapresenter= d;
        datapresenter.attach(this);
        nameTitleList = new LinkedList<>();
        fc = new JFileChooser("..\\NaturalAPI_Discover\\txt_documents");
        fc.setMultiSelectionEnabled(true);

        loadFileBtn.addActionListener(actionEvent -> {
            int returnVal = fc.showOpenDialog(GUI_Discover.this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File[] files = fc.getSelectedFiles();
                for(File f: files){
                    log.append("Opening: " + f.getName() + "." + "\n");
                    nameTitleList.add(f.getName());
                }
                this.areFilesLoaded = true;
            } else {
                log.append("Open command cancelled by user." + "\n");
            }
            log.setCaretPosition(log.getDocument().getLength());
        });

        createBDLbtn.addActionListener(actionEvent -> {
            String result = this.choosing(1);
            if(result!=null) {
                controller.createBdl(result, nameTitleList);
                nameTitleList.clear();
                this.areFilesLoaded = false;
            }else{
                if(this.areFilesLoaded) {
                    log.append("Your files are still available.");
                }
            }
        });

        addDocumentBtn.addActionListener(actionEvent -> {
            String result = this.choosing(2);
            if(result!=null) {
                controller.addDocument(result, nameTitleList);
                nameTitleList.clear();
                this.areFilesLoaded = false;
            } else {
                if(this.areFilesLoaded) {
                    log.append("Your files are still available.");
                }
            }
        });

        removeDococumentBtn.addActionListener(actionEvent -> {
            String result = this.choosing(2);
            if(result!=null) {
                controller.removeDocument(result, nameTitleList);
                nameTitleList.clear();
                this.areFilesLoaded = false;
            } else {
                if(this.areFilesLoaded) {
                    log.append("Your files are still available.");
                }
            }
        });

        viewBDLBtn.addActionListener(actionEvent -> {
            String result = (String)JOptionPane.showInputDialog(this,
                    "Choose the name of the bdl that you want to view",
                    "Choose name",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    null,
                    null );
            String[] choices = {"Show all","More probable words","First 15 words"};
            String choice = (String) JOptionPane.showInputDialog(this,
                    "Select a type of visualization",
                    "Select type",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    choices,
                    choices[0]
                    );
            Integer visualizationType = Arrays.asList(choices).indexOf(choice);

            controller.viewBdl(result, visualizationType);
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

    public void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("NaturalAPI Discover");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Add content to the window.
        frame.add(new GUI_Discover(controller,datapresenter).panel1);
        frame.setPreferredSize(new Dimension(600,500));
        //Display the window.
        frame.pack();
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
