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
    public int padding = Window.height-Window.width-50;
    public int blockSize;
    private Minefield minefield;
    BufferedImage flag;

    public DrawingPanel(){}

    public void InitializePanel(Minefield minefield) {
        this.minefield = minefield;
        try{
            flag = ImageIO.read(getClass().getResource("res/flag.png"));
        } catch (IOException exc){}
        isInitialized = true;
    }

    public Minefield getMinefield(){
        return this.minefield;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D graphicSettings = (Graphics2D)g;

        super.paintComponent(g);
        if(isInitialized){
            int size = this.minefield.getSize();
            blockSize = Window.width/size;

            for (int row = 0; row < size; row++) {
                for (int col = 0; col < size; col++) {

                    if(minefield.isCellShown(row,col)){
                        graphicSettings.fillRect(row*blockSize, col*blockSize+padding, blockSize, blockSize);
                        graphicSettings.drawString(minefield.getCellValue(row,col) == 0 ? "" : Integer.toString(minefield.getCellValue(row,col)), (row*blockSize) +  1, (col*blockSize) +  1 + padding);
                    }
                    else {
                        graphicSettings.drawRect(row*blockSize, col*blockSize +padding, blockSize, blockSize);
                    }
                    if(minefield.isCellFlagged(row,col)){
                        graphicSettings.drawImage(flag, (row*blockSize) +  1, (col*blockSize) + 1 +padding,  blockSize - 2, blockSize - 2, this);
                    }
                }
            }
        }
    }
}
