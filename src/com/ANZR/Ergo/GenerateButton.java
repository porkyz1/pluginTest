package com.ANZR.Ergo;

import com.intellij.execution.runners.ProgramRunner;
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

    @Override
    public void actionPerformed(AnActionEvent e) {




        project = e.getData(LangDataKeys.PROJECT);
        toolWindow = ToolWindowManager.getInstance(project).getToolWindow("Ergo");

        GenerateToolWindow tool = new GenerateToolWindow();
        tool.createToolWindowContent(project, toolWindow);

        //Gets source folder but will crash if the focus is on
        //any but the code editor window
//        sourceModule = ModuleRootManager.getInstance(e.getData(LangDataKeys.MODULE));
//        if (sourceModule != null)
//            printProjectFiles(sourceModule.getSourceRoots(), 0);

    }

    @Override
    public void update(AnActionEvent e) {
    }

    public void printProjectFiles(VirtualFile[] dir, int tab) {
        for (int y = 0; y < tab; y++)
            System.out.print("  ");
        for (int x = 0; x < dir.length; x++) {
            System.out.println(dir[x].getName());

            if (dir[x].getChildren().length >= 0) {
                printProjectFiles(dir[x].getChildren(), tab + 1);
            }
        }
    }
}
