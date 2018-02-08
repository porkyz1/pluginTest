package com.ANZR.Ergo;

import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;

public class GenerateButton extends AnAction {
    Project project;
    ToolWindow toolWindow;
    Folder moduleFolder;
    int errorCode = 0;


    @Override
    public void actionPerformed(AnActionEvent e) {
        project = e.getData(LangDataKeys.PROJECT);
        toolWindow = ToolWindowManager.getInstance(project).getToolWindow("Ergo");
        try {
            moduleFolder = printProjectFiles(ModuleRootManager.getInstance(e.getData(LangDataKeys.MODULE)).getSourceRoots(), 0, new Folder(project.getName()));
//            moduleFolder = printProjectFiles(project.getBaseDir().getChildren(),0,new Folder(project.getProjectFile().getName()));
        }catch(IllegalArgumentException badFocus){
            errorCode = 2;
        }
        GenerateToolWindow tool = new GenerateToolWindow();
        tool.populateToolWindow(project,toolWindow, moduleFolder, errorCode);
    }


    @Override
    public void update(AnActionEvent e) {
    }

    public Folder printProjectFiles(VirtualFile[] dir, int tab, Folder buildFolder) {
        for (int x = 0; x < dir.length; x++) {
            for (int y = 0; y < tab; y++)
                System.out.print("  ");
            System.out.println(dir[x].getName() + " " + dir[x].getExtension() + " " + dir[x].getFileType().getName());

            if(dir[x].getFileType().getName() == "JAVA"){
                buildFolder.addClass(new ClassFolder(dir[x].getName(), true));
            }else {
                buildFolder.addFolder(printProjectFiles(dir[x].getChildren(), tab + 1, new Folder(dir[x].getName())));
            }//make not folder of class array
        }
        return buildFolder;
    }
}

