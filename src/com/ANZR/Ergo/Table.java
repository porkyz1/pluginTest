package com.ANZR.Ergo;

import com.intellij.ui.table.JBTable;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Table extends JBTable {

    GenerateToolWindow parent;
    private Timer timer;
    private boolean wasDoubleClick;
    private int row;


    public Table(GenerateToolWindow parent, DefaultTableModel model){
        super();
        this.parent = parent;

        setModel(model);
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

    @Override
    public boolean isCellEditable(int rows, int columns) {
        row = rows;
        return false;
    }

    private void click(MouseEvent e){
        if (parent.getPreviousFolder().isEmpty())
            parent.getSideBar().getPreviousButton().setEnabled(false);
        else parent.getSideBar().getPreviousButton().setEnabled(true);

        if (e.getClickCount() == 2) {
            wasDoubleClick = true;
        } else {
            Integer timerinterval = (Integer) Toolkit.getDefaultToolkit().getDesktopProperty(
                    "awt.multiClickInterval");
            timer = new Timer(timerinterval.intValue(), new ActionListener() {

                public void actionPerformed(ActionEvent evt) {
                    if ((isRowSelected(row) && wasDoubleClick) && !parent.getIsClass()) {
                        parent.createModel(row);
                        wasDoubleClick = false;
                    }
                }
            });
            timer.setRepeats(false);

            timer.start();
        }
    }

    public Component prepareRenderer(TableCellRenderer cellRenderer, int rows, int columns) {
        Component c = super.prepareRenderer(cellRenderer, rows, columns);
        if (!getValueAt(rows, 1).equals(0)) {
            c.setBackground(Color.RED);
            c.setForeground(Color.BLACK);
        } else {
            c.setBackground(Color.DARK_GRAY);
            c.setForeground(Color.LIGHT_GRAY);
        }
        return c;
    }

    public int getRow() {
        return row;
    }
}
