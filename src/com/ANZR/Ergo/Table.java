package com.ANZR.Ergo;

import com.intellij.ui.table.JBTable;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class Table extends JBTable {

    private GenerateToolWindow parent;
    private boolean wasDoubleClick;
    private int row;
    private static final String[] classTableHeader = {"Anti-Pattern", "Found"};
    private static final String[] folderTableHeader = {"Element", "Anti-Patterns Found"};


    Table(GenerateToolWindow parent){
        super();
        this.parent = parent;

        setFillsViewportHeight(true);
        setAutoCreateRowSorter(true);
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                click(e);
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


    }

    public void setTableModel(Folder folder) {
        if(folder.isClass())
            setClassModel(folder);
        else
            setFolderModel(folder);
    }


    private void setFolderModel(Folder folder){
        ArrayList<Folder >folders = folder.getFolders();
        Object[][] temp = new Object[folders.size()][folderTableHeader.length];
        for (int i = 0; i < folders.size(); i++) {
            if(folders.get(i).getName() != null)
                temp[i][0] = folders.get(i).getName();
            else temp[i][0] = "null";
            temp[i][1] = Folder.getAntiPatternNumber(folders);
        }
        setModel(new DefaultTableModel(temp, folderTableHeader));
    }

    private void setClassModel(Folder folder){
        ArrayList<AntiPattern > antiPatterns = folder.getAntiPatterns();
        Object[][] temp = new Object[antiPatterns.size()][classTableHeader.length];
        for (int i = 0; i < antiPatterns.size(); i++) {
            if(antiPatterns.get(i).getName() != null)
                temp[i][0] = antiPatterns.get(i).getName();
            else temp[i][0] = "null";
            temp[i][1] = antiPatterns.get(i).getPercent();
        }
        setModel(new DefaultTableModel(temp, classTableHeader));
    }

    @Override
    public boolean isCellEditable(int rows, int columns) {
        row = rows;
        return false;
    }

    private void click(MouseEvent e){
        if (e.getClickCount() == 2) {
            wasDoubleClick = true;
        } else {
            Integer timerInterval = (Integer) Toolkit.getDefaultToolkit().getDesktopProperty(
                    "awt.multiClickInterval");
            Timer timer = new Timer(timerInterval, evt -> {
                if (isRowSelected(row) && wasDoubleClick) {
                    parent.createModel(row);
                    wasDoubleClick = false;
                }
            });
            timer.setRepeats(false);

            timer.start();
        }
    }

    public Component prepareRenderer(TableCellRenderer cellRenderer, int rows, int columns) {
        Component component = super.prepareRenderer(cellRenderer, rows, columns);
        if (!getValueAt(rows, 1).equals(0)) {
            component.setBackground(Color.RED);
            component.setForeground(Color.BLACK);
        } else {
            component.setBackground(Color.DARK_GRAY);
            component.setForeground(Color.LIGHT_GRAY);
        }
        return component;
    }

    public int getRow() {
        return row;
    }
}
