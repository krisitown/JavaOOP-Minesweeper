package com.company;

import com.company.res.RepaintTheBoard;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.event.MouseEvent;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * The main window for the game
 */
public class Window extends JFrame {
    public static int width = 640;
    public static int height = 740;

    public Window(){
        this.setSize(width, height);
        this.setTitle("Minesweeper");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //initialize all of the components of the main window
        JTextField boardSize = new JTextField(3);
        JButton startGame = new JButton("Start Game!");
        DrawingPanel drawingPanel = new DrawingPanel();
        JPanel menuPanel = new JPanel();

        startGame.addActionListener(e -> {
            byte size = Byte.parseByte(boardSize.getText());
            if(size >= 10 && size < 121){
                Minefield minefield = new Minefield(size);
                drawingPanel.InitializePanel(minefield);
                drawingPanel.setVisible(true);
                drawingPanel.addMouseListener(new MouseInputListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        int x = e.getX();
                        int y = e.getY();
                        if(e.getButton() == MouseEvent.BUTTON1){
                            int row = (int)Math.floor(x/drawingPanel.blockSize);
                            int col = (int)Math.floor((y-drawingPanel.padding)/drawingPanel.blockSize);
                            if(drawingPanel.getMinefield().clickCell(row, col)){
                                drawingPanel.gameOver = true;
                            }
                        }
                        else if(e.getButton() == MouseEvent.BUTTON3){
                            int row = (int)Math.floor(x/drawingPanel.blockSize);
                            int col = (int)Math.floor((y-drawingPanel.padding)/drawingPanel.blockSize);
                            drawingPanel.getMinefield().toggleFlagOfCell(row, col);
                        }
                    }

                    @Override
                    public void mousePressed(MouseEvent e) {

                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {

                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {

                    }

                    @Override
                    public void mouseExited(MouseEvent e) {

                    }

                    @Override
                    public void mouseDragged(MouseEvent e) {

                    }

                    @Override
                    public void mouseMoved(MouseEvent e) {

                    }
                });
            }
        });


        menuPanel.setSize(width, height-width-50);
        menuPanel.add(startGame);
        menuPanel.add(new JLabel("Board size:"));
        menuPanel.add(boardSize);
        menuPanel.setVisible(true);

        this.add(menuPanel);
        this.add(drawingPanel);

        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(5);
        executor.scheduleAtFixedRate(new RepaintTheBoard(this), 0L, 16L, TimeUnit.MILLISECONDS);

        this.setVisible(true);
    }
}
