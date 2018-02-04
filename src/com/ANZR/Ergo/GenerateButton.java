package com.ANZR.Ergo;

import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;

public class GenerateButton extends AnAction {
    Project project;
    ModuleRootManager sourceModule;
    ToolWindow toolWindow;
    Folder moduleFolder;

    @Override
    public void actionPerformed(AnActionEvent e) {

        project = e.getData(LangDataKeys.PROJECT);
        toolWindow = ToolWindowManager.getInstance(project).getToolWindow("Ergo");

        if (ModuleRootManager.getInstance(e.getData(LangDataKeys.MODULE)) != null)
            moduleFolder = new Folder(project.getName(),
                    printProjectFiles(ModuleRootManager.getInstance(e.getData(LangDataKeys.MODULE)).getSourceRoots(), 0));

        GenerateToolWindow tool = new GenerateToolWindow();
        tool.populateToolWindow(project,toolWindow, moduleFolder);
    }

    @Override
    public void update(AnActionEvent e) {
    }

    public Folder printProjectFiles(VirtualFile[] dir, int tab) {
        Folder tempFolder = new Folder();
        for (int x = 0; x < dir.length; x++) {
            for (int y = 0; y < tab; y++)
                System.out.print("  ");
            System.out.println(dir[x].getName());

            if(dir[x].getExtension() == "java"){
                tempFolder.addClass(new ClassFolder(dir[x].getName()));
            }else if(dir[x].getExtension() == null && dir[x].getChildren() != null){
                tempFolder.addFolder(printProjectFiles(dir[x].getChildren(), tab + 1));
            }
        }
        return tempFolder;
    }
}
