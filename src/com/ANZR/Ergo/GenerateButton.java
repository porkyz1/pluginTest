package com.ANZR.Ergo;

import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;

public class GenerateButton extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        Project project = e.getData(LangDataKeys.PROJECT);
        ToolWindow toolWindow = ToolWindowManager.getInstance(project).getToolWindow("Ergo");
        VirtualFile[] files = ProjectRootManager.getInstance(project).getContentSourceRoots();
        Folder rootFolder = getRootFolder(project.getName(), files);
        GenerateToolWindow tool = new GenerateToolWindow();
        tool.populateToolWindow(toolWindow, rootFolder, project);
    }

    @Override
    public void update(AnActionEvent e) {
    }

    private Folder getRootFolder(String folderName, VirtualFile[] sourceFolders){
        Folder buildFolder = new Folder(folderName);

        for (VirtualFile file : sourceFolders) {

            if(file.getFileType().getName().equals("JAVA")){
                buildFolder.addFolder(new Folder(file.getName(), true, file));
            }else if (file.isDirectory()){
                Folder childFolder = getRootFolder(file.getName(), file.getChildren());
                buildFolder.addFolder(childFolder);
            }else{
                ///File was not a directory or Java file.
            }
        }

        return buildFolder;
    }



}

