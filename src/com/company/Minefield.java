package com.company;

import java.util.Random;

/**
 * Created by krisitown on 06.05.16.
 */
public class Minefield {
    private Random random;
    private Cell[][] field;
    private byte boardSize;
    private byte mineCount;

    public Minefield(byte boardSize) {
        this.boardSize = boardSize;
        this.field = new Cell[boardSize][boardSize];
        this.mineCount = (byte)Math.round((boardSize*boardSize)*0.15);

        random = new Random();
        this.initializeBoard();
        this.initializeMines();
        this.setCellValues();
    }
    
    private void initializeBoard(){
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                field[row][col] = new Cell();
            }
        }
    }

    private void initializeMines() {
        for (byte i = 0; i < mineCount; i++) {
            byte row = (byte)random.nextInt(boardSize);
            byte col = (byte)random.nextInt(boardSize);

            //if there is already a bomb on that cell, it finds a new place for a bomb
            if(field[row][col].hasBomb){
                i--;
                continue;
            }

            field[row][col].hasBomb = true;
        }
    }

    private void setCellValues() {
        for (byte row = 0; row < field.length; row++) {
            for (byte col = 0; col < field.length; col++) {
                //if the current cell doesn't have a bomb, counts how many bombs are near it
                if(!field[row][col].hasBomb){
                    byte nearbyMines = 0;
                    if(row - 1 >= 0){
                        if(field[row - 1][col].hasBomb){
                            nearbyMines++;
                        }
                    }

                    if(col - 1 >= 0){
                        if(field[row][col - 1].hasBomb){
                            nearbyMines++;
                        }
                    }

                    if(row + 1 < field.length){
                        if(field[row + 1][col].hasBomb){
                            nearbyMines++;
                        }
                    }

                    if(col + 1 < field[row].length){
                        if(field[row][col + 1].hasBomb){
                            nearbyMines++;
                        }
                    }

                    if(row - 1 >= 0 && col - 1 >= 0){
                        if(field[row - 1][col - 1].hasBomb){
                            nearbyMines++;
                        }
                    }

                    if(row + 1 > field.length && col + 1 > field[row].length){
                        if(field[row + 1][col + 1].hasBomb){
                            nearbyMines++;
                        }
                    }

                    if (row - 1 >= 0 && col + 1 < field[row].length) {
                        if(field[row - 1][col + 1].hasBomb){
                            nearbyMines++;
                        }
                    }

                    if(row + 1 < field.length && col - 1 >= 0){
                        if(field[row + 1][col - 1].hasBomb){
                            nearbyMines++;
                        }
                    }

                    //set the cell value to the number of mines near it
                    field[row][col].setValue(nearbyMines);
                }
            }
        }
    }

    //toggles the flag of a cell
    public void toggleFlagOfCell(int row, int col){
        if(!field[row][col].isShown){
            if(field[row][col].isFlagged){
                field[row][col].isFlagged = false;
            }
            else {
                field[row][col].isFlagged = true;
            }
        }
    }

    //clicks on a cell, if the cell contains a bomb it returns true
    public boolean clickCell(int row, int col){
        if(!field[row][col].isShown){
            if(field[row][col].hasBomb){
                return true;
            }
            field[row][col].isShown = true;
        }
        return false;
    }

    public int getSize(){
        return field.length;
    }

    public byte getCellValue(int row, int col){
        return field[row][col].getValue();
    }

    public boolean isCellShown(int row, int col){
        return field[row][col].isShown;
    }

    public boolean isCellFlagged(int row, int col){
        return field[row][col].isFlagged;
    }
}
