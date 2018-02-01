package com.ANZR.Ergo;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class Folder {
    String name;
    ArrayList<Folder> folders;
    ArrayList<ClassFolder> classes;
    DefaultTableModel model;

    public Folder(String name, DefaultTableModel model) {
        this.name = name;
        this.model = model;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Folder> getFolders() {
        return folders;
    }

    public ArrayList<ClassFolder> getClasses() {
        return classes;
    }

    public DefaultTableModel getModel() {
        return model;
    }

    public void makeModel(){
        ArrayList<Object> temp = new ArrayList<>();
        model.addColumn("File Name");
        model.addColumn("Percent/Number of Anti-Patterns");
        for (int x = 0; x <= folders.toArray().length; x++) {
            temp.clear();
            temp.add(folders.get(x).getName());

            temp.add(folders.get(x).folders.toArray().length+
                    folders.get(x).classes.toArray().length);
            model.addRow(temp.toArray());
        }
        for (int x = 0; x <= folders.toArray().length; x++) {
            temp.clear();
            temp.add(folders.get(x).getName());

            temp.add(folders.get(x).classes.toArray().length);
            model.addRow(temp.toArray());
        }
    }
}
