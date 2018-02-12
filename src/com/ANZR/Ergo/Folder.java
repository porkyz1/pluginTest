package com.ANZR.Ergo;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class Folder {
    private String name;
    private ArrayList<Folder> folders = new ArrayList<>();
    private ArrayList<AntiPattern> antiPatterns = new ArrayList<>();
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


    public boolean isClass() {
        return isClass;
    }

    public int findFileIndex(String name){
        for (int i = 0; i < folders.size(); i++) {
            if(folders.get(i).getName() == name)
                return i;
        }
        return -1;
    }



    public static int getAntiPatternNumber(ArrayList<Folder> folder){
        int temp = 0;
        for (Folder f : folder) {
            temp += (f.getAntiPatterns().size() + getAntiPatternNumber(f.getFolders()));
        }
        return temp;
    }


}
