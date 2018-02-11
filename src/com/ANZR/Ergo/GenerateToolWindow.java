package com.ANZR.Ergo;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.IconLoader;
import com.intellij.openapi.wm.*;
import com.intellij.ui.content.*;
import com.intellij.ui.table.JBTable;
import com.intellij.util.containers.Stack;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;

public class GenerateToolWindow implements ToolWindowFactory {

    private DefaultTableModel tableModel = new DefaultTableModel();
    private ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
    private JPanel contentWindow = new JPanel(new BorderLayout());
    private SideBar sideBar = new SideBar(this);
    private Table table;
    private JLabel errorLabel = new JLabel();
    private boolean isClass = false;
    private Folder rootFolder;
    private Folder currentFolder;
    private Stack previousFolder = new Stack();


    public void populateToolWindow(ToolWindow toolWindow, Folder rootFolder) {
        this.rootFolder = rootFolder;
        this.currentFolder = rootFolder;
        this.tableModel = currentFolder.getModel();

        //Setup Side Bar
        contentWindow.add(sideBar, BorderLayout.LINE_START);

        //Setup Table
        tableModel = rootFolder.getModel();
        table = new Table(this, tableModel);
        contentWindow.add(new JScrollPane(table));
        contentWindow.add(table.getTableHeader(), BorderLayout.EAST);

        Content content = contentFactory.createContent(contentWindow, rootFolder.getName(), false);
        toolWindow.getContentManager().removeAllContents(true);
        toolWindow.getContentManager().addContent(content);
        toolWindow.show(null);
    }

    public void createToolWindowContent(Project project, ToolWindow toolWindow) {
    }

    public void showErrorLabel(String error) {
        //change color make more noticable
        errorLabel.setText(error);
        errorLabel.setVisible(true);
        contentWindow.add(errorLabel, BorderLayout.CENTER);
    }

    public void createModel(int rows) {
        int nextClassIndex = currentFolder.findClassIndex(
                tableModel.getValueAt(rows, 0).toString());
        int nextFolderIndex = currentFolder.findFolderIndex(
                tableModel.getValueAt(rows, 0).toString());

        previousFolder.push(currentFolder);
        if (tableModel.getValueAt(rows, 0).toString().contains(".java")) {
            tableModel = currentFolder.getClasses().get(nextClassIndex).getModel();
            table.setModel(tableModel);
            isClass = true;
        } else {
            tableModel = currentFolder.getFolders().get(nextFolderIndex).getModel();
            currentFolder = currentFolder.getFolders().get(nextFolderIndex);
            table.setModel(tableModel);
        }
        if (previousFolder.isEmpty())sideBar.getPreviousButton().setEnabled(false);
        else sideBar.getPreviousButton().setEnabled(true);

        if(isClass)sideBar.getNextButton().setEnabled(false);
        else sideBar.getNextButton().setEnabled(true);
    }


    //SIDE BAR FUNCTIONS
    public void returnToRootFolder(){
        tableModel = rootFolder.getModel();
        currentFolder = rootFolder;
        table.setModel(tableModel);
        previousFolder.clear();
        isClass = false;
        if (previousFolder.isEmpty()) sideBar.getPreviousButton().setEnabled(false);
        else sideBar.getPreviousButton().setEnabled(true);

        if (isClass) sideBar.getNextButton().setEnabled(false);
        else sideBar.getNextButton().setEnabled(true);
    }

    public void previousFolder(){
        if (previousFolder.empty())
            sideBar.getPreviousButton().setEnabled(false);
        else {
            sideBar.getPreviousButton().setEnabled(true);
            currentFolder = (Folder) previousFolder.pop();
            tableModel = currentFolder.getModel();
            table.setModel(tableModel);
            isClass = false;
        }
        if (previousFolder.isEmpty()) sideBar.getPreviousButton().setEnabled(false);
        else sideBar.getPreviousButton().setEnabled(true);

        if (isClass) sideBar.getNextButton().setEnabled(false);
        else sideBar.getNextButton().setEnabled(true);
    }

    public void nextFolder(){
        if (!isClass) {
            if (previousFolder.isEmpty())
                sideBar.getPreviousButton().setEnabled(false);
            else
                sideBar.getPreviousButton().setEnabled(true);

            if (isClass)
                sideBar.getNextButton().setEnabled(false);
            else
                sideBar.getNextButton().setEnabled(true);

            createModel(table.getRow());
        }
    }

    public void extraButtonPressed(){
        System.out.println("EXTRA");
    }


    //Setters + Getters
    public SideBar getSideBar() {
        return sideBar;
    }

    public boolean getIsClass() {
        return isClass;
    }

    public Stack getPreviousFolder() {
        return previousFolder;
    }
}