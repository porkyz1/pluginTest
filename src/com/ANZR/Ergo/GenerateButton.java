package com.ANZR.Ergo;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowAnchor;
import com.intellij.openapi.wm.ToolWindowManager;
import org.jetbrains.jps.builders.BuildOutputConsumer;
import java.io.File;

public class GenerateButton extends AnAction{
     Project src;
     ToolWindow toolWindow;
    @Override
    public void actionPerformed(AnActionEvent e) {
        src = e.getData(LangDataKeys.PROJECT);
        e.getData(LangDataKeys.MODULE).getModuleFile().getChildren();

        GenerateToolWindow tool = new GenerateToolWindow();

        System.out.println(src.getBaseDir().getName());
        System.out.println(src.getBasePath());

//        printProjectFiles(src.getBaseDir().getChildren(), 0);
        printProjectFiles(e.getData(LangDataKeys.MODULE).getModuleFile().getChildren(), 0);

        toolWindow = ToolWindowManager.getInstance(src)
                    .registerToolWindow("Ergo", true, ToolWindowAnchor.RIGHT);
        toolWindow.show(null);
        tool.createToolWindowContent(src, toolWindow);
    }
    @Override
    public void update(AnActionEvent e){
    }
    public void printProjectFiles(VirtualFile[] dir, int tab){
        for (int y = 1;y<tab;y++)
            System.out.print("  ");
        for(int x = 0; x< dir.length;x++) {
                System.out.println(dir[x].getName());

            if(dir[x].getChildren().length >= 0) {
                printProjectFiles(dir[x].getChildren(), tab+1);
            }
        }
    }
}
