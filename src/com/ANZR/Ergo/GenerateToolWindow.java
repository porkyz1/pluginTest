package com.ANZR.Ergo;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.*;
import com.intellij.ui.content.*;
import com.intellij.ui.table.JBTable;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;

public class GenerateToolWindow implements ToolWindowFactory {

    private JLabel projectName = new JLabel();
    private JLabel projectURL = new JLabel();
    private JPanel contentWindow = new JPanel(new BorderLayout());
    private JBTable table = new JBTable();

    int row;
    int column;

    private Folder rootFolder;
    private Folder currentFolder;
    private ClassFolder currentClass;

    private ToolWindow toolWindow;
    private DefaultTableModel tableModel = null;
    private Project project;
    private JButton button = new JButton();
    String[][] tableHeader34 = {{"Element", "Number of AP"}, {"AP Name", "Y/N"}};
    String[][] dummyData = {{"greg", "3"}, {"god", "maybe"}, {"jeff", "sure"}};

    public void populateToolWindow(Project project, ToolWindow toolWindow, Folder rootFolder) {
        this.toolWindow = toolWindow;
        this.project = project;
        this.rootFolder = rootFolder;
        this.currentFolder = rootFolder;
//        button.setIcon(IconLoader.getIcon("/icons/button_image.png"));
//        button.setSize(20,20);
//        button.setDefaultCapable(false);
//        button.setAction();
//        contentWindow.add(button);

        if(tableModel == null)
            tableModel = (currentFolder.getModel());
        setupTable();



        projectName.setText(this.project.getName());
        projectURL.setText(this.project.getBasePath());

        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        Content content = contentFactory.createContent(contentWindow, "", false);
        toolWindow.getContentManager().addContent(content);
        toolWindow.show(null);
    }

    public void createToolWindowContent(Project project, ToolWindow toolWindow) {
//        toolWindow.show(null);
    }



    private void setupTable() {
        table = createTable();
        createModel();
        table.setModel(tableModel);
        table.setPreferredScrollableViewportSize(table.getPreferredSize());
        table.setFillsViewportHeight(true);
        table.setAutoCreateRowSorter(true);


        contentWindow.add(new JScrollPane(table));
        contentWindow.add(table.getTableHeader(), BorderLayout.NORTH);

    }

    private void setupNextTable(DefaultTableModel nextTableModel){
        if(nextTableModel != null) {

//            Object[][] patterns = {{"God Object", 1}, {"Long Method", 2}, {"Singleton Overuse", 3}};
//            tableModel = new DefaultTableModel(patterns, tableHeader[1]);
            table.setModel(nextTableModel);
        }
//        }else{
//            tableModel = new DefaultTableModel(dummyData, tableHeader[0]);
//            table.setModel(tableModel);
//
//        }


    }

private void createModel(){
    if(table.isCellSelected(row,column) && table.isRowSelected(row)){
        if(tableModel.getValueAt(row,0).toString().contains(".java"))
            tableModel = (currentFolder.getClasses().get(row).getModel());
        else{
            tableModel = (currentFolder.getFolders().get(row).getModel());
            currentFolder = currentFolder.getFolders().get(row);
        }
    }
}
    private JBTable createTable(){
        return new JBTable(){
            public boolean isCellEditable(int rows, int columns){
                row = rows;
                column = columns;
                return false;
            }
        };
    }

    public void setRootFolder(Folder folder){
        rootFolder = folder;
    }

    private void addToTable(Object[] data){
        tableModel.addRow(data);
    }
}
