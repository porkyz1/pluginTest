package com.ANZR.Ergo;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class Folder {
    private String name;
    private ArrayList<Folder> folders = new ArrayList<>();
    private ArrayList<AntiPattern> antiPatterns = new ArrayList<>();

    private String[] classTableHeader = {"AP Name", "Y/N"};
    private String[] FolderTableHeader = {"Element", "Number of AP"};
    private boolean isClass = false;

    public Folder(String name) {
        this.name = name;
    }

    public Folder(String name, boolean isClass) {
        this.name = name;
        this.isClass = isClass;
        addAntiPattern(new AntiPattern("god", .5));
    }

    public void addFolder(Folder folder){
        this.folders.add(folder);
    }
    public void addAntiPattern(AntiPattern pattern){
        this.antiPatterns.add(pattern);
    }

    public String getName() {
        return name;
    }

    public ArrayList<Folder> getFolders() {
        return folders;
    }

    public ArrayList<AntiPattern> getAntiPatterns() {
        return antiPatterns;
    }

    public DefaultTableModel getModel() {
        if(isClass)
            return makeClassModel();
        else return makeFolderModel();
    }

    public boolean isClass() {
        return isClass;
    }

    public int findFileIndex(String name){
        for (int x = 0; x < folders.toArray().length; x++) {
            if(folders.get(x).getName() == name)
                return x;
        }
        return -1;
    }

    private DefaultTableModel makeFolderModel(){
        Object[][] temp = new Object[folders.toArray().length][FolderTableHeader.length];
        for (int x = 0; x < folders.toArray().length; x++) {
            if(folders.get(x).getName() != null)
                temp[x][0] = folders.get(x).getName();
            else temp[x][0] = "null";
            temp[x][1] = getAntiPatternNumber(folders);
        }
        return (new DefaultTableModel(temp, FolderTableHeader));
    }

    private int getAntiPatternNumber(ArrayList<Folder> folder){
        int temp = 0;
        for (Folder f : folder) {
            temp += (f.getAntiPatterns().toArray().length + getAntiPatternNumber(f.getFolders()));
        }
        return temp;
    }

    private DefaultTableModel makeClassModel(){
        Object[][] temp = new Object[antiPatterns.toArray().length][classTableHeader.length];
        for (int x = 0; x < antiPatterns.toArray().length; x++) {
            if(antiPatterns.get(x).getName() != null)
                temp[x][0] = antiPatterns.get(x).getName();
            else temp[x][0] = "null";
            temp[x][1] = antiPatterns.get(x).getPercent();
        }
        return (new DefaultTableModel(temp, classTableHeader));
    }
}
