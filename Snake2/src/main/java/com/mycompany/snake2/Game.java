package com.mycompany.snake2;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

public class Game extends JPanel implements ActionListener {

    static final int LARGURA = 1300;
    static final int ALTURA = 750;
    static final int TAM_UNIDADES = 50;
    static final int GAME_UNITS = (LARGURA * ALTURA) / (TAM_UNIDADES * TAM_UNIDADES);
    static final int DELAY = 175;
    final int x[] = new int[GAME_UNITS];
    final int y[] = new int[GAME_UNITS];
    int bodyParts = 6;
    int applesEaten;
    int appleX;
    int appleY;
    char direcao = 'R';
    boolean running = false;
    Timer timer;
    Random random;

    Game() {
        random = new Random();
        this.setPreferredSize(new Dimension(LARGURA, ALTURA));
        this.setBackground(Color.green);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }

    public void startGame() {
        newApple();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }

    //Pinta os componentes na tela
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {

        if (running) {
            g.setColor(Color.red);
            g.fillOval(appleX, appleY, TAM_UNIDADES, TAM_UNIDADES);

            for (int i = 0; i < bodyParts; i++) {
                if (i == 0) {
                    g.setColor(Color.blue);
                    g.fillRect(x[i], y[i], TAM_UNIDADES, TAM_UNIDADES);
                } else {
                    g.setColor(new Color(51, 148, 222));
                    g.fillRect(x[i], y[i], TAM_UNIDADES, TAM_UNIDADES);
                }
            }
            g.setColor(Color.black);
            g.setFont(new Font("Ink Free", Font.BOLD, 40));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Pontos: " + applesEaten, (LARGURA - metrics.stringWidth("Pontos: " + applesEaten)) / 2, g.getFont().getSize());

        } else {
            gameOver(g);
        }

    }

    //Cria uma maçã aleatoriamente
    public void newApple() {
        appleX = random.nextInt((int) (LARGURA / TAM_UNIDADES)) * TAM_UNIDADES;
        appleY = random.nextInt((int) (ALTURA / TAM_UNIDADES)) * TAM_UNIDADES;
    }

    //Faz a animação da cobra, mudando as coordenadas de cada parte do corpo
    public void move() {
        for (int i = bodyParts; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }

        switch (direcao) {
            case 'U':
                y[0] = y[0] - TAM_UNIDADES;
                break;
            case 'D':
                y[0] = y[0] + TAM_UNIDADES;
                break;
            case 'L':
                x[0] = x[0] - TAM_UNIDADES;
                break;
            case 'R':
                x[0] = x[0] + TAM_UNIDADES;
                break;
        }

    }

    //Verifica colisão com maças
    public void checkApples() {
        if ((x[0] == appleX) && (y[0] == appleY)) {
            bodyParts++;
            applesEaten++;
            newApple();
        }
    }

    //Verifica colisões
    public void checkCollisions() {

        //Verifica colisoes com o corpo
        for (int i = bodyParts; i > 0; i--) {
            if ((x[0] == x[i]) && (y[0] == y[i])) {
                running = false;
            }
        }
        //Verifica colisoes com a borda esquerda
        if (x[0] < 0) {
            running = false;
        }
        //Verifica colisoes com a borda direita
        if (x[0] > LARGURA) {
            running = false;
        }
        //Verifica colisoes com o topo
        if (y[0] < 0) {
            running = false;
        }
        //Veirifica colisoes com a borda de baixo
        if (y[0] > ALTURA) {
            running = false;
        }

        if (!running) {
            timer.stop();
        }
    }

    //Tela de game over
    public void gameOver(Graphics g) {
        g.setColor(Color.black);
        g.setFont(new Font("Ink Free", Font.BOLD, 75));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("Game Over", (LARGURA - metrics2.stringWidth("Game Over")) / 2, ALTURA / 2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (running) {
            move();
            checkApples();
            checkCollisions();
        }
        repaint();
    }

    public class MyKeyAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (direcao != 'R') {
                        direcao = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (direcao != 'L') {
                        direcao = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (direcao != 'D') {
                        direcao = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (direcao != 'U') {
                        direcao = 'D';
                    }
                    break;
            }
        }
    }
}
