package com.ANZR.Ergo;

import com.intellij.openapi.fileEditor.OpenFileDescriptor;
import com.intellij.openapi.util.IconLoader;
import com.intellij.openapi.vfs.VirtualFile;

import javax.swing.*;
import java.awt.*;
import java.util.stream.Collectors;

public class SideBar extends JPanel {

    private JButton rootButton = new JButton();
    private JButton previousButton = new JButton();
    private JButton nextButton = new JButton();
    private JButton sourceButton = new JButton();
    private GenerateToolWindow parent;

    SideBar(GenerateToolWindow parent) {
        super(new GridBagLayout());
        this.parent = parent;

        addButtons();

        int row = 0;
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.FIRST_LINE_START;
        constraints.gridx = 0;
        for (int x = 0; x < 4; x++) {
            constraints.gridy = row;
            switch (x){
                case 0:
                    add(rootButton, constraints);
                    break;
                case 1:
                    add(previousButton, constraints);
                    break;
                case 2:
                    add(nextButton, constraints);
                    break;
                case 3:
                    constraints.weighty = 20;
                    add(sourceButton, constraints);
                    break;
            }
            row++;
        }

    }

    public void setIfButtonIsEnabled(){
        if (parent.getPreviousFolder().isEmpty())
            previousButton.setEnabled(false);
        else
            previousButton.setEnabled(true);

        if(parent.getCurrentFolder().isClass())
            nextButton.setEnabled(false);
        else
            nextButton.setEnabled(true);

        for (Folder f :parent.getCurrentFolder().getFolders()){
            sourceButton.setEnabled(false);
            if(f.isClass()){
                sourceButton.setEnabled(true);
                break;
            }
        }

    }

    private void addButtons() {
        rootButton = createButton("Go To Root Folder", IconLoader.getIcon("/icons/root.png"));
        rootButton.addActionListener(e -> returnToRootFolder());

        previousButton = createButton("Previous Folder", IconLoader.getIcon("/icons/back.png"));
        previousButton.addActionListener(e -> previousFolder());
        previousButton.setEnabled(false);

        nextButton = createButton("Enter Selected Folder", IconLoader.getIcon("/icons/into.png"));
        nextButton.addActionListener(e -> nextFolder());

        sourceButton = createButton("Open Code In Project", IconLoader.getIcon("/icons/source.png"));
        sourceButton.addActionListener(e -> sourceButtonPressed());
        sourceButton.setEnabled(false);
    }

    private JButton createButton(String popupText, Icon icon) {
        JButton button = new JButton();
        button.setIcon(icon);
        button.setSize(30, 30);
        button.setToolTipText(popupText);
        return button;
    }

    private void returnToRootFolder(){
        parent.getTable().setTableModel(parent.getRootFolder());
        parent.setCurrentFolder(parent.getRootFolder());
        parent.getPreviousFolder().clear();
        setIfButtonIsEnabled();
    }

    private void previousFolder(){
        if (!parent.getPreviousFolder().empty()){
            previousButton.setEnabled(true);
            parent.setCurrentFolder(parent.getPreviousFolder().pop());
            parent.getTable().setTableModel(parent.getCurrentFolder());
        }
        setIfButtonIsEnabled();
    }

    private void nextFolder(){
        if (!parent.getCurrentFolder().isClass())
            parent.createModel(parent.getTable().getRow());
        setIfButtonIsEnabled();
    }

    private void sourceButtonPressed(){
        int index = parent.getTable().getRow();
        String className = (String) parent.getTable().getModel().getValueAt(index, 0);

        String headerID = Table.getClassTableHeader()[0];
        String currentHeader = parent.getTable().getModel().getColumnName(0);

        if (currentHeader.equals(headerID)){
            // We are inside the class.
            if (parent.getCurrentFolder().isClass()){
                VirtualFile virtualFile  = parent.getCurrentFolder().getVirtualFile();
                new OpenFileDescriptor(parent.getProject(), virtualFile).navigate(true);
            }
        }else{
            Folder file =  parent.getCurrentFolder().getFolders().stream().filter(x->x.getName().equals(className)).collect(Collectors.toList()).get(0);

            if (file.isClass()){
                VirtualFile virtualFile  = file.getVirtualFile();
                new OpenFileDescriptor(parent.getProject(), virtualFile).navigate(true);
            }
        }

        setIfButtonIsEnabled();
    }


}
