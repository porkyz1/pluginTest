package com.ANZR.Ergo;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class ClassFolder {
    private String name;
    private ArrayList<AntiPattern> antiPatterns = new ArrayList<>();
    private DefaultTableModel model;

    public ClassFolder(String name, DefaultTableModel model) {
        this.name = name;
        this.model = model;
    }

    public String getName() {
        return name;
    }

    public ArrayList<AntiPattern> getAntiPatterns() {
        return antiPatterns;
    }

    public DefaultTableModel getModel() {
        return model;
    }

    public void makeModel(){
        ArrayList<Object> temp = new ArrayList<>();
        model.addColumn("File Name");
        model.addColumn("Anti-Pattern Percent");
        for (int x = 0; x <= antiPatterns.toArray().length; x++) {
            temp.clear();
            temp.add(antiPatterns.get(x).getName());
            temp.add(antiPatterns.get(x).getPercent());
            model.addRow(temp.toArray());
        }
    }
}
