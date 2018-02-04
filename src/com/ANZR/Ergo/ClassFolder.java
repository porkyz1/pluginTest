package com.ANZR.Ergo;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class ClassFolder {
    private String name;
    private ArrayList<AntiPattern> antiPatterns = new ArrayList<>();
    private DefaultTableModel model = new DefaultTableModel();
    String[] tableHeader = {"AP Name", "Y/N"};

    public ClassFolder(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public ArrayList<AntiPattern> getAntiPatterns() {
        return antiPatterns;
    }

    public DefaultTableModel getModel() {
        return makeModel();
    }

    public DefaultTableModel makeModel(){
        Object[][] temp = new Object[antiPatterns.toArray().length][tableHeader.length];
        for (int x = 0; x < antiPatterns.toArray().length; x++) {
            temp[x][0] = antiPatterns.get(x).getName();
            temp[x][1] = antiPatterns.get(x).getPercent();
        }
        return (new DefaultTableModel(temp, tableHeader));
    }
}
