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

    private JPanel contentWindow = new JPanel(new BorderLayout());
    private JBTable table = new JBTable();
    private JLabel errorLabel = new JLabel();
    private SideBar sideBar = new SideBar();

    private JButton rootButton = new JButton();
    private JButton previousButton = new JButton();
    private JButton nextButton = new JButton();
    private JButton extraButton = new JButton();

    private int row;
    private Timer timer;
    private boolean wasDoubleClick;
    private boolean isClass = false;

    private Folder rootFolder;
    private Folder currentFolder;
    private Stack previousFolder = new Stack();

    private ToolWindow toolWindow;
    private DefaultTableModel tableModel = new DefaultTableModel();
    private Project project;

    private ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
    private Content content;

    public void populateToolWindow(Project project, ToolWindow toolWindow, Folder rootFolder) {
        this.toolWindow = toolWindow;
        this.project = project;
        this.rootFolder = rootFolder;
        this.currentFolder = rootFolder;
        this.tableModel = currentFolder.getModel();

        addButtons();
        setupTable();

        content = contentFactory.createContent(contentWindow, rootFolder.getName(), false);
        toolWindow.getContentManager().removeAllContents(true);
        toolWindow.getContentManager().addContent(content);
        toolWindow.show(null);
    }

    private JButton createButton(String popupText, Icon icon) {
        JButton button = new JButton();
        button.setIcon(icon);
        button.setSize(30, 30);
        button.setToolTipText(popupText);
        return button;
    }

    private void addButtons() {
        rootButton = createButton("Go To Root Folder", IconLoader.getIcon("/icons/root.png"));
        rootButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableModel = rootFolder.getModel();
                currentFolder = rootFolder;
                table.setModel(tableModel);
                previousFolder.clear();
                isClass = false;
                if (previousFolder.isEmpty())previousButton.setEnabled(false);
                else previousButton.setEnabled(true);

                if(isClass)nextButton.setEnabled(false);
                else nextButton.setEnabled(true);
            }
        });
        sideBar.addButton(rootButton);

        previousButton = createButton("Previous Folder", IconLoader.getIcon("/icons/back.png"));
        previousButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (previousFolder.empty())
                    previousButton.setEnabled(false);
                else {
                    previousButton.setEnabled(true);
                    currentFolder = (Folder) previousFolder.pop();
                    tableModel = currentFolder.getModel();
                    table.setModel(tableModel);
                    isClass = false;
                }
                if (previousFolder.isEmpty()) previousButton.setEnabled(false);
                else previousButton.setEnabled(true);

                if(isClass)nextButton.setEnabled(false);
                else nextButton.setEnabled(true);
            }
        });
        previousButton.setEnabled(false);
        sideBar.addButton(previousButton);

        nextButton = createButton("Enter Selected Folder", IconLoader.getIcon("/icons/into.png"));
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isClass){
//                    previousFolder.push(currentFolder);
                    if (previousFolder.isEmpty())previousButton.setEnabled(false);
                    else previousButton.setEnabled(true);

                    if(isClass)nextButton.setEnabled(false);
                    else nextButton.setEnabled(true);
                    createModel(row);
                }
            }
        });
        sideBar.addButton(nextButton);

        extraButton = createButton("extra", IconLoader.getIcon("/icons/button_image.png"));
        extraButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("extra");
            }
        });
        sideBar.addButton(extraButton);

        contentWindow.add(sideBar.getSideBarView(), BorderLayout.LINE_START);
    }

    public void createToolWindowContent(Project project, ToolWindow toolWindow) {
    }

    public void showErrorLabel(String error) {
        //change color make more noticable
        errorLabel.setText(error);
        errorLabel.setVisible(true);
        contentWindow.add(errorLabel, BorderLayout.CENTER);
    }

    private void setupTable() {
        table = createTable();
        tableModel = rootFolder.getModel();
        table.setModel(tableModel);
        table.setFillsViewportHeight(true);
        table.setAutoCreateRowSorter(true);
        table.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (previousFolder.isEmpty())
                    previousButton.setEnabled(false);
                else previousButton.setEnabled(true);

                if (e.getClickCount() == 2) {
                    wasDoubleClick = true;
                } else {
                    Integer timerinterval = (Integer) Toolkit.getDefaultToolkit().getDesktopProperty(
                            "awt.multiClickInterval");
                    timer = new Timer(timerinterval.intValue(), new ActionListener() {

                        public void actionPerformed(ActionEvent evt) {
                            if ((table.isRowSelected(row) && wasDoubleClick) && !isClass) {
                                createModel(row);
                                wasDoubleClick = false;
                            }
                        }
                    });
                    timer.setRepeats(false);

                    timer.start();
                }
            }
            @Override
            public void mousePressed(MouseEvent e) {

            }
            @Override
            public void mouseReleased(MouseEvent e) {

            }
            @Override
            public void mouseEntered(MouseEvent e) {

            }
            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        contentWindow.add(new JScrollPane(table));
        contentWindow.add(table.getTableHeader(), BorderLayout.EAST);

    }

    private void createModel(int rows) {
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
        if (previousFolder.isEmpty())previousButton.setEnabled(false);
        else previousButton.setEnabled(true);

        if(isClass)nextButton.setEnabled(false);
        else nextButton.setEnabled(true);
    }

    private JBTable createTable() {
        return new JBTable() {
            public boolean isCellEditable(int rows, int columns) {
                row = rows;
                return false;
            }
            public Component prepareRenderer(TableCellRenderer cellRenderer, int rows, int columns) {
                Component c = super.prepareRenderer(cellRenderer, rows, columns);
                if (!table.getValueAt(rows, 1).equals(0)) {
                    c.setBackground(Color.RED);
                    c.setForeground(Color.BLACK);
                } else {
                    c.setBackground(Color.DARK_GRAY);
                    c.setForeground(Color.LIGHT_GRAY);
                }
                return c;
            }
        };
    }
}