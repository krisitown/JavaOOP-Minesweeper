package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 *  Panel used for drawing the game board
 */
public class DrawingPanel extends JComponent {
    private boolean isInitialized;
    public boolean gameOver;
    private int size;
    public int padding = Window.height-Window.width-50;
    public int blockSize;
    private Minefield minefield;
    BufferedImage flag;
    Font font, gameOverFont;

    public DrawingPanel(){}

    public void InitializePanel(Minefield minefield) {
        this.minefield = minefield;
        try{
            flag = ImageIO.read(getClass().getResource("res/flag.png"));
        } catch (IOException exc){}
        size = this.minefield.getSize();
        blockSize = Window.width/size;
        font = new Font("Comic Sans MS", Font.BOLD, blockSize);
        gameOverFont = new Font("Comic Sans MS", Font.BOLD, Window.height/12);
        gameOver = false;
        isInitialized = true;
    }

    public Minefield getMinefield(){
        return this.minefield;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D graphicSettings = (Graphics2D)g;

        super.paintComponent(g);

        graphicSettings.setFont(font);

        if(isInitialized){
            for (int row = 0; row < size; row++) {
                for (int col = 0; col < size; col++) {
                    graphicSettings.drawRect(row*blockSize, col*blockSize +padding, blockSize, blockSize);
                    if(minefield.isCellShown(row,col)){
                        graphicSettings.setColor(Color.LIGHT_GRAY);
                        graphicSettings.fillRect((row*blockSize) + 2, (col*blockSize)+padding+2, blockSize-2, blockSize-2);
                        graphicSettings.setColor(Color.BLACK);

                        graphicSettings.drawString(minefield.getCellValue(row,col) == 0 ? "" : Integer.toString(minefield.getCellValue(row,col)),
                                (row*blockSize) +  (int)(blockSize*0.20), (col*blockSize) + (int)(blockSize*0.20) + padding + (int)(blockSize*0.75));
                    }
                    if(minefield.isCellFlagged(row,col)){
                        graphicSettings.drawImage(flag, (row*blockSize) +  1, (col*blockSize) + 1 +padding,  blockSize - 2, blockSize - 2, this);
                    }

                    //god mode
                    else if(minefield.doesCellHaveBomb(row,col)){
                        graphicSettings.drawString("x",
                                (row*blockSize) +  (int)(blockSize*0.20), (col*blockSize) + (int)(blockSize*0.20) + padding + (int)(blockSize*0.75));
                    }
                }
            }
            if (gameOver){
                graphicSettings.setFont(gameOverFont);
                graphicSettings.setColor(Color.RED);
                graphicSettings.drawString("Game Over!", (Window.width/2)-(int)(2.8*gameOverFont.getSize()), (Window.height/2)-(gameOverFont.getSize()/2));
                graphicSettings.setColor(Color.BLACK);
                graphicSettings.setFont(font);
            }

            if(minefield.playerWon){
                graphicSettings.setFont(gameOverFont);
                graphicSettings.setColor(Color.GREEN);
                graphicSettings.drawString("You Win!", (Window.width/2)-(2*gameOverFont.getSize()), (Window.height/2)-(gameOverFont.getSize()/2));
                graphicSettings.setColor(Color.BLACK);
                graphicSettings.setFont(font);
            }
        }
    }
}
