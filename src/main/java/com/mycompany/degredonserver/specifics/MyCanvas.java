/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.degredonserver.specifics;

   import javax.swing.*;
   import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;

    public class MyCanvas extends JPanel {
       public void paintComponent(Graphics g) {
         super.paintComponent(g);
         g.fillRect(10,10,100,50);
         g.drawRect(10,80,100,50);
           try {
               Thread.sleep(1000);
           } catch (InterruptedException ex) {
               Logger.getLogger(MyCanvas.class.getName()).log(Level.SEVERE, null, ex);
           }
           g.fillRect(10,10,80,50);
         g.drawRect(10,80,90,50);
           try {
               Thread.sleep(1000);
           } catch (InterruptedException ex) {
               Logger.getLogger(MyCanvas.class.getName()).log(Level.SEVERE, null, ex);
           }
           g.fillRect(10,10,50,50);
         g.drawRect(10,80,30,50);
           try {
               Thread.sleep(1000);
           } catch (InterruptedException ex) {
               Logger.getLogger(MyCanvas.class.getName()).log(Level.SEVERE, null, ex);
           }
           g.fillRect(10,10,10,50);
         g.drawRect(10,80,5,50);
      }
   }