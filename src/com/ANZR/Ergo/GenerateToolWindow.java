package com.ANZR.Ergo;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.IconLoader;
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
    private JBTable table;
    private ToolWindow toolWindow;
    private DefaultTableModel tableModel;
    private Project project;
    private JButton button = new JButton();
    String[][] tableHeader = {{"Element", "Number of AP", "Num of C"}, {"AP Name", "Y/N"}};

    Object[][] dummyData = {{0, "File","Anti-Pattern(s)"},
            {1, "File","Anti-Pattern(s)"},
            {2, "File","Anti-Pattern(s)"}};


    public void createToolWindowContent(Project project, ToolWindow toolWindow) {
        this.toolWindow = toolWindow;
        this.project = project;

//        button.setIcon(IconLoader.getIcon("/icons/button_image.png"));
//        button.setPreferredSize(new Dimension(20, 20));
//        contentWindow.add(button, BorderLayout.BEFORE_LINE_BEGINS);

        setupTable();




        projectName.setText(this.project.getName());
        projectURL.setText(this.project.getBasePath());

        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        Content content = contentFactory.createContent(contentWindow, "", false);
        toolWindow.getContentManager().addContent(content);
        toolWindow.show(null);
    }



    private void setupTable() {
        table = createTable();
        tableModel = new DefaultTableModel(dummyData, tableHeader[0]);
        table.setModel(tableModel);
        table.setPreferredScrollableViewportSize(table.getPreferredSize());
        table.setFillsViewportHeight(true);
        table.setAutoCreateRowSorter(true);


        contentWindow.add(new JScrollPane(table));
        contentWindow.add(table.getTableHeader(), BorderLayout.NORTH);

    }

    private void setupNextTable(boolean isClass){
        if(isClass){

            Object[][] patterns = {{"God Object", 1}, {"Long Method", 2}, {"Singleton Overuse", 3}};
            tableModel = new DefaultTableModel(patterns, tableHeader[1]);
            table.setModel(tableModel);

        }else{
            tableModel = new DefaultTableModel(dummyData, tableHeader[0]);
            table.setModel(tableModel);

        }


    }


    private JBTable createTable(){
        return new JBTable(){
            public boolean isCellEditable(int rows, int columns){
                if(table.isCellSelected(rows,columns) && table.isRowSelected(rows)){
                    setupNextTable(true);
                }
                return false;
            }
        };
    }

    private void addToTable(Object[] data){
        tableModel.addRow(data);
    }
}
