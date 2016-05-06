package com.company.res;

import com.company.Window;

/**
 * Created by krisitown on 06.05.16.
 */
public class RepaintTheBoard implements Runnable {
    Window window;

    public RepaintTheBoard(Window window) {
        this.window = window;
    }

    @Override
    public void run() {
        this.window.repaint();
    }
}
