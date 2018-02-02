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
    private JPanel contentWindow = new JPanel();
    private JBTable table;
    private DefaultTableModel tableModel;
    private ToolWindow toolWindow;
    private Project project;

    Object[][] data = {{0, "File","Anti-Pattern(s)"},
            {1, "File","Anti-Pattern(s)"},
            {2, "File","Anti-Pattern(s)"}};


    public void createToolWindowContent(Project project, ToolWindow toolWindow) {
        this.toolWindow = toolWindow;
        this.project = project;

        setupTable();

        projectName.setText(this.project.getName());
        projectURL.setText(this.project.getBasePath());

        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        Content content = contentFactory.createContent(contentWindow, "", false);
        toolWindow.getContentManager().addContent(content);
        toolWindow.show(null);
    }

    String[][] tableHeader = {{"Element", "Number of AP", "Num of C"}, {"C Name", "# AP"}, {"AP Name", "Y/N"}};

    private void setupTable() {
        tableModel = new DefaultTableModel(data, tableHeader[0]);

        table = createTable();
        table.setModel(tableModel);
        table.setPreferredScrollableViewportSize(table.getPreferredSize());
        table.setFillsViewportHeight(true);
        table.setAutoCreateRowSorter(true);

        contentWindow.add(new JScrollPane(table));
        contentWindow.add(table.getTableHeader(), BorderLayout.NORTH);
        contentWindow.add(table, BorderLayout.CENTER);

    }

    private JBTable createTable(){
        return new JBTable(){
            public boolean isCellEditable(int rows, int columns){
                if(table.isCellSelected(rows,columns) && table.isRowSelected(rows)){
                    tableModel.addRow(data[0]);
                }
                return false;
            }
        };
    }

    private void addToTable(Object[] data){
        tableModel.addRow(data);
    }
}
