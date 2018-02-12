package com.ANZR.Ergo;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.*;
import com.intellij.ui.content.*;
import com.intellij.util.containers.Stack;
import javax.swing.*;
import java.awt.*;

public class GenerateToolWindow implements ToolWindowFactory {

    private ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
    private JPanel contentWindow = new JPanel(new BorderLayout());
    private SideBar sideBar = new SideBar(this);
    private Table table;
    private JLabel errorLabel = new JLabel();
    private Folder rootFolder;
    private Folder currentFolder;
    private Stack<Folder> previousFolder = new Stack<>();

    
    public void populateToolWindow(ToolWindow toolWindow, Folder rootFolder) {
        this.rootFolder = rootFolder;
        this.currentFolder = rootFolder;

        //Setup Side Bar
        contentWindow.add(sideBar, BorderLayout.LINE_START);

        //Setup Table
        table = new Table(this);
        table.setTableModel(currentFolder);
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
        int nextFileIndex = currentFolder.findFileIndex(table.getModel().getValueAt(rows, 0).toString());
        if (previousFolder.isEmpty() || !currentFolder.isClass()){
            previousFolder.push(currentFolder);
            currentFolder = currentFolder.getFolders().get(nextFileIndex);
            table.setTableModel(currentFolder);
        }
        sideBar.checkIfButtonEnable();
    }

    public Table getTable() {
        return table;
    }

    public Folder getCurrentFolder() {
        return currentFolder;
    }

    public Stack<Folder> getPreviousFolder() {
        return previousFolder;
    }


    //Getters and Setters

    public Folder getRootFolder() {
        return rootFolder;
    }

    public void setCurrentFolder(Folder currentFolder) {
        this.currentFolder = currentFolder;
    }


}