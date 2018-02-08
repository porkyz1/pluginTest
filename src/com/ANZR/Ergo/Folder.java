package com.ANZR.Ergo;

import org.jetbrains.annotations.Nullable;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.Random;

public class Folder {
    String name;
    ArrayList<Folder> folders = new ArrayList<>();
    ArrayList<ClassFolder> classes= new ArrayList<>();
    DefaultTableModel model = new DefaultTableModel();
    String[] tableHeader = {"Element", "Number of AP"};

    public Folder() {
    }
    public Folder(String name) {
        this.name = name;
    }
    public Folder(String name, Folder folder) {
        this.name = name;
        this.folders = folder.getFolders();
        this.classes = folder.getClasses();
//        if(folder.getModel() != null)
//            this.model = folder.getModel();
    }
    public void addClass(ClassFolder classe){
        this.classes.add(classe);
    }
    public void addFolder(Folder folder){
        this.folders.add(folder);
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
        return makeModel();
    }
    public int findFolderIndex(String name){
        for (int x = 0; x < folders.toArray().length; x++) {
            if(folders.get(x).getName() == name)
                return x;
        }
        return -1;
    }
    public int findClassIndex(String name){
        for (int x = 0; x < classes.toArray().length; x++) {
            if(classes.get(x).getName() == name)
                return x;
        }
        return -1;
    }

    private DefaultTableModel makeModel(){
        Object[][] temp = new Object[folders.toArray().length+
                classes.toArray().length][tableHeader.length];
        for (int x = 0; x < folders.toArray().length; x++) {
            if(folders.get(x).getName() != null)
                temp[x][0] = folders.get(x).getName();
            else temp[x][0] = "null";
                temp[x][1] = folders.get(x).getFolders().toArray().length+
                        folders.get(x).getClasses().toArray().length;
        }
        for (int x = 0; x < classes.toArray().length; x++) {
            if(classes.get(x).getName() != null)
                temp[x+folders.toArray().length][0] = classes.get(x).getName();
            else temp[x+folders.toArray().length][0] = "null";
                temp[x+folders.toArray().length][1] = classes.toArray().length;
        }
        return (new DefaultTableModel(temp, tableHeader));
    }
}
