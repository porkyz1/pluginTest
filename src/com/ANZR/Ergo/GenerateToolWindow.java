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

    JBTable table;
    DefaultTableModel model;

    Object[][] data = {{".0.","File","Anti-Pattern(s)"},
            {".1.","File","Anti-Pattern(s)"},
            {".2.","File","Anti-Pattern(s)"}};

    private ToolWindow myToolWindow;
    private Project src;

    // Create the tool window content.
    public void createToolWindowContent(Project project, ToolWindow toolWindow) {
        myToolWindow = toolWindow;
        src = project;

        Runner();

        projectName.setText(src.getName());
        projectURL.setText(src.getBasePath());
//        contentWindow.add(projectName);
//        contentWindow.add(projectURL);

        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        Content content = contentFactory.createContent(contentWindow, "", false);
        toolWindow.getContentManager().addContent(content);
        toolWindow.show(null);
    }

    public void Runner() {
        model = new DefaultTableModel();
        model.addColumn("IconCol");
        model.addColumn("FileCol");
        model.addColumn("#/%Col");
//        model.addColumn("DescCol");
        model.addRow(data[1]);
        model.addRow(data[2]);

        table = new JBTable(){
            public boolean isCellEditable(int rows, int columns){
                if(table.isCellSelected(rows,columns) && table.isRowSelected(rows)){
//                    model.removeRow(rows);
                    model.addRow(data[0]);
                }
                return false;
          }
        };
        table.setModel(model);

        table.setPreferredScrollableViewportSize(new Dimension());
        table.setFillsViewportHeight(true);

//        JScrollPane jsp = new JScrollPane(table);
        contentWindow.add(table);
    }
}
