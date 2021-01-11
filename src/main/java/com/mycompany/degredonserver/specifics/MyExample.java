/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.degredonserver.specifics;

import java.awt.*;
import javax.swing.*;


public class MyExample extends JFrame {
  MyCanvas canvas = new MyCanvas();
  JLabel heading = new JLabel("My drawing");
  
  public MyExample() {
    Container container = getContentPane();
    container.add(heading,BorderLayout.NORTH);
    container.add(canvas,BorderLayout.CENTER);
  }
  
  public static void main(String[] args) {
    MyExample myDrawing = new MyExample();
    myDrawing.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    myDrawing.setSize(300,300);
    myDrawing.setVisible(true);
  }
}