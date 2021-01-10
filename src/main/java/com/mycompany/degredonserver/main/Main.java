/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.degredonserver.main;

import com.mycompany.degredonserver.panels.ServerPanel;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

/**
 *
 * @author FabioAbreu
 */
public class Main {
    
    JFrame mainFrame;
    
    public static void main(String args[]) {
        JFrame mainFrame = new JFrame();
        mainFrame.add(new ServerPanel());
        
        mainFrame.setResizable(true);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);

        // this.pdfSearchPanel();
        mainFrame.setTitle("Degredon Server");
        mainFrame.setVisible(true);
    }
    
    public JFrame getMainFrame() {
        return mainFrame;
    }
}
