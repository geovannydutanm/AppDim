package com.example.lineapp.appgame;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

public class MyViewGameLoop extends Thread {
    public static final double MAX_UPS = 30.0;
    private static final double UPS_PERIOD = 1E+3 / MAX_UPS;
    private final MyViewGame _view;
    private final SurfaceHolder surfaceHolder;
    private boolean isRunning = false;

    public MyViewGameLoop(MyViewGame view, SurfaceHolder surfaceHolder) {
        _view = view;
        this.surfaceHolder = surfaceHolder;
    }

    public void startLoop() {
        isRunning = true;
        start();
    }

    @Override
    public void run() {
        super.run();

        int updateCount = 0;
        int frameCount = 0;
        long startTime;
        long elapsedTime;
        long sleepTime;

        Canvas canvas = null;
        startTime = System.currentTimeMillis();
        while (isRunning) {
            try {
                canvas = surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    _view.update();
                    updateCount++;

                    _view.draw(canvas);
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } finally {
                if (canvas != null) {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                        frameCount++;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            elapsedTime = System.currentTimeMillis() - startTime;
            sleepTime = (long) (updateCount * UPS_PERIOD - elapsedTime);
            if (sleepTime > 0) {
                try {
                    sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            while (sleepTime < 0 && updateCount < MAX_UPS - 1) {
                _view.update();
                updateCount++;
                elapsedTime = System.currentTimeMillis() - startTime;
                sleepTime = (long) (updateCount * UPS_PERIOD - elapsedTime);
            }
            elapsedTime = System.currentTimeMillis() - startTime;
            if (elapsedTime >= 1000) {
                updateCount = 0;
                frameCount = 0;
                startTime = System.currentTimeMillis();
            }
        }
    }

    public void stopLoop() {
        isRunning = false;
        try {
            join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
