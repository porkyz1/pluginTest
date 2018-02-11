package com.ANZR.Ergo;

import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;

public class GenerateButton extends AnAction {
    private Project project;
    private ToolWindow toolWindow;
    private Folder moduleFolder;
    private VirtualFile[] files;


    @Override
    public void actionPerformed(AnActionEvent e) {
        project = e.getData(LangDataKeys.PROJECT);
        toolWindow = ToolWindowManager.getInstance(project).getToolWindow("Ergo");
        files = ProjectRootManager.getInstance(project).getContentSourceRoots();
        moduleFolder = getModuleFolder(project.getName(), files);

//        Printer.printProjectFiles(moduleFolder);
        GenerateToolWindow tool = new GenerateToolWindow();
        tool.populateToolWindow(project, toolWindow, moduleFolder);
    }


    @Override
    public void update(AnActionEvent e) {
    }

    private Folder getModuleFolder(String folderName, VirtualFile[] sourceFolders){
        Folder buildFolder = new Folder(folderName);

        for (VirtualFile file : sourceFolders) {

            if(file.getFileType().getName() == "JAVA"){
                buildFolder.addClass(new ClassFolder(file.getName(), true));
            }else if (file.isDirectory()){
                Folder childFolder = getModuleFolder(file.getName(), file.getChildren());
                buildFolder.addFolder(childFolder);
            }else{
                System.out.println("ERROR: We got something that was not a Java file or folder...");
            }
        }

        return buildFolder;
    }



}

