package com.mycompany.snake2;

import javax.swing.JFrame;

public class Janela extends JFrame {
    
    Janela(){
        
        add(new Game());
        setTitle("SnakeGame");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        pack();
        setVisible(true);
        setLocationRelativeTo(null);
    }

   
}
