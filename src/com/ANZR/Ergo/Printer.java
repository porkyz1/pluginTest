package com.ANZR.Ergo;

import java.util.ArrayList;

public class Printer {

    public static void printProjectFiles(Folder root) {
        printFolder(root, 0);
    }

    private static void printFolder(Folder folder, int numTab){

        ArrayList<ClassFolder> classes = folder.getClasses();
        ArrayList<Folder> folders = folder.getFolders();

        System.out.println(getTabString(numTab) + folder.getName());
        printClasses(classes, numTab + 1);

        for(Folder f : folders)
            printFolder(f, numTab + 1);

    }

    private static void printClasses(ArrayList<ClassFolder> classes, int numTab){
        for (ClassFolder c : classes)
            System.out.println(getTabString(numTab) + "Class: " + c.getName());
    }

    private static String getTabString(int numberOfTabs){
        String tab = "";
        for (int i =0; i < numberOfTabs; i++)
            tab = tab.concat("\t");
        return tab;
    }


}
