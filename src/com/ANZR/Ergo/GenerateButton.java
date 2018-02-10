package com.ANZR.Ergo;

import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;

import java.util.ArrayList;

public class GenerateButton extends AnAction {
    private Project project;
    private ToolWindow toolWindow;
    private Folder moduleFolder;


    @Override
    public void actionPerformed(AnActionEvent e) {
        project = e.getData(LangDataKeys.PROJECT);
        toolWindow = ToolWindowManager.getInstance(project).getToolWindow("Ergo");
        VirtualFile[] files = ProjectRootManager.getInstance(project).getContentSourceRoots();
        moduleFolder = getModuleFolder("root", files);

        Printer.printProjectFiles(moduleFolder);

        GenerateToolWindow tool = new GenerateToolWindow();
        tool.populateToolWindow(project, toolWindow, moduleFolder);
    }


    @Override
    public void update(AnActionEvent e) {
    }

    private Folder getModuleFolder(String folderName, VirtualFile[] sourceFolders){
        Folder buildFolder = new Folder(folderName);

        for (int i = 0; i < sourceFolders.length; i++) {

            if(sourceFolders[i].getFileType().getName() == "JAVA"){
                buildFolder.addClass(new ClassFolder(sourceFolders[i].getName(), true));
            }else if (sourceFolders[i].isDirectory()){
                Folder childFolder = getModuleFolder(sourceFolders[i].getName(), sourceFolders[i].getChildren());
                buildFolder.addFolder(childFolder);
            }else{
                System.out.println("ERROR: We got something that was not a Java file or folder...");
            }
        }

        return buildFolder;
    }



}

