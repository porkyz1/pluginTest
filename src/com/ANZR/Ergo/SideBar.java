package com.ANZR.Ergo;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class SideBar{

    private ArrayList<JButton> buttonList = new ArrayList<>();
    private GridBagConstraints constraints = new GridBagConstraints();
    private JPanel panel = new JPanel(new GridBagLayout());
    private int row = 0;

    SideBar(){
    }
    public void addButton(JButton button){
        buttonList.add(button);
    }
    public JPanel getSideBarView(){

        constraints.anchor = GridBagConstraints.FIRST_LINE_START;
        constraints.gridx = 0;
        for(int x = 0; x < buttonList.toArray().length; x++){
            constraints.gridy = row;
            if(x == buttonList.toArray().length-1)
                constraints.weighty = 20;
            panel.add(buttonList.get(x), constraints);
            row++;
        }
        return panel;
    }
}
