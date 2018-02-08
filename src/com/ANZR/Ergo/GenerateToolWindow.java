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

    public void populateToolWindow(Project project, ToolWindow toolWindow, Folder rootFolder, int errorCode) {
        this.toolWindow = toolWindow;
        this.project = project;
        this.rootFolder = rootFolder;
        this.currentFolder = rootFolder;
        tableModel = currentFolder.getModel();

        if (errorCode == 0) {
            addButtons();
            setupTable();

        } else {
            if(errorCode == 1) {
                showErrorLabel("Please Build Project Before Running Ergo");
            }
            if (errorCode == 2) {
                showErrorLabel("Please Highlight Proper Source Folder in the Project Tool Window Or Have Focus on a JAVA file inside that Source Folder");
            }
        }

        content = contentFactory.createContent(contentWindow, rootFolder.getName(), false);
        toolWindow.getContentManager().addContent(content);
        toolWindow.show(null);
    }

    private JButton createButton(int x, int y, Icon icon) {
        JButton button = new JButton();
        button.setIcon(icon);
        button.setSize(30, 30);
        button.setLocation(x, y);
        return button;
    }

    private void addButtons() {
        //fix button layout
        rootButton = createButton(0, 0, IconLoader.getIcon("/icons/button_image.png"));
        rootButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableModel = rootFolder.getModel();
                currentFolder = rootFolder;
                table.setModel(tableModel);
                previousFolder.clear();
                isClass = false;
                if (previousFolder.isEmpty())
                    previousButton.setEnabled(false);
                else previousButton.setEnabled(true);
            }
        });
        rootButton.setToolTipText("Go To Root Folder");
        contentWindow.add(rootButton, BorderLayout.LINE_START);

        previousButton = createButton(0, 30, IconLoader.getIcon("/icons/button_image.png"));
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
                if (previousFolder.isEmpty())
                    previousButton.setEnabled(false);
                else previousButton.setEnabled(true);
            }
        });
        previousButton.setToolTipText("Go Back A Folder");
        contentWindow.add(previousButton, BorderLayout.LINE_START);

        nextButton = createButton(0, 60, IconLoader.getIcon("/icons/button_image.png"));
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isClass){
//                    previousFolder.push(currentFolder);
                    if (previousFolder.isEmpty())
                        previousButton.setEnabled(false);
                    else previousButton.setEnabled(true);
                    createModel(row);
                }
            }
        });
        nextButton.setToolTipText("Go into Folder");
        contentWindow.add(nextButton, BorderLayout.LINE_START);

        extraButton = createButton(0, 60, IconLoader.getIcon("/icons/button_image.png"));
        extraButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("extra");
            }
        });
        contentWindow.add(extraButton, BorderLayout.LINE_START);
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
        table.setPreferredScrollableViewportSize(table.getPreferredSize());
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
        contentWindow.add(table.getTableHeader(), BorderLayout.NORTH);

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
        if (previousFolder.isEmpty())
            previousButton.setEnabled(false);
        else previousButton.setEnabled(true);
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