package com.ANZR.Ergo;

import com.intellij.openapi.util.IconLoader;
import javafx.geometry.Side;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SideBar extends JPanel {

    private JButton rootButton = new JButton();
    private JButton previousButton = new JButton();
    private JButton nextButton = new JButton();
//    private JButton extraButton = new JButton();
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
                    constraints.weighty = 20;
                    add(nextButton, constraints);
                    break;
//                case 3:
//                    constraints.weighty = 20;
//                    add(extraButton, constraints);
//                    break;
            }
            row++;
        }

    }

    private void addButtons() {
        rootButton = createButton("Go To Root Folder", IconLoader.getIcon("/icons/root.png"));
        rootButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parent.returnToRootFolder();
            }
        });

        previousButton = createButton("Previous Folder", IconLoader.getIcon("/icons/back.png"));
        previousButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parent.previousFolder();
            }
        });
        previousButton.setEnabled(false);

        nextButton = createButton("Enter Selected Folder", IconLoader.getIcon("/icons/into.png"));
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parent.nextFolder();
            }
        });

        //fund use or remove
//        extraButton = createButton("extra", IconLoader.getIcon("/icons/button_image.png"));
//        extraButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                parent.extraButtonPressed();
//            }
//        });

    }

    private JButton createButton(String popupText, Icon icon) {
        JButton button = new JButton();
        button.setIcon(icon);
        button.setSize(30, 30);
        button.setToolTipText(popupText);
        return button;
    }

    public JButton getRootButton() {
        return rootButton;
    }

    public JButton getPreviousButton() {
        return previousButton;
    }

    public JButton getNextButton() {
        return nextButton;
    }

//    public JButton getExtraButton() {
//        return extraButton;
//    }

}
