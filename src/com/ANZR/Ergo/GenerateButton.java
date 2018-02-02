package com.ANZR.Ergo;

import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.wm.FocusWatcher;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;

public class GenerateButton extends AnAction{
     Project src;
     ModuleRootManager srcModule;
     ToolWindow toolWindow;
     GenerateToolWindow tool = new GenerateToolWindow();
    @Override
    public void actionPerformed(AnActionEvent e) {
        src = e.getData(LangDataKeys.PROJECT);
        toolWindow = ToolWindowManager.getInstance(src).getToolWindow("Ergo");
        tool.createToolWindowContent(src, toolWindow);
        //////////////////////////////////////////////////////
        //Gets source folder but will crash if the focus is on
        //any but the code editor window
        srcModule = ModuleRootManager.getInstance(e.getData(LangDataKeys.MODULE));
        if(srcModule != null)
            printProjectFiles(srcModule.getSourceRoots(), 0);
        ///////////////////////////////////////////////////////
        }
    @Override
    public void update(AnActionEvent e){
    }
    public void printProjectFiles(VirtualFile[] dir, int tab){
        for (int y = 0;y<tab;y++)
            System.out.print("  ");
        for(int x = 0; x< dir.length;x++) {
                System.out.println(dir[x].getName());

            if(dir[x].getChildren().length >= 0) {
                printProjectFiles(dir[x].getChildren(), tab+1);
            }
        }
    }
}
