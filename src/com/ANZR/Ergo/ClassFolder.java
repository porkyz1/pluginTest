package com.ANZR.Ergo;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class ClassFolder {
    private String name;
    private ArrayList<AntiPattern> antiPatterns = new ArrayList<>();
    private String[] tableHeader = {"AP Name", "Y/N"};

    public ClassFolder(String name, boolean loadDummyData) {
        this.name = name;
        if(loadDummyData){
            addAntiPattern(new AntiPattern("god", .5));
        }
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
    public void addAntiPattern(AntiPattern pattern){
        this.antiPatterns.add(pattern);
    }

    public DefaultTableModel makeModel(){
        Object[][] temp = new Object[antiPatterns.toArray().length][tableHeader.length];
        for (int x = 0; x < antiPatterns.toArray().length; x++) {
            if(antiPatterns.get(x).getName() != null)
                temp[x][0] = antiPatterns.get(x).getName();
            else temp[x][0] = "null";
            temp[x][1] = antiPatterns.get(x).getPercent();
        }
        return (new DefaultTableModel(temp, tableHeader));
    }
}
