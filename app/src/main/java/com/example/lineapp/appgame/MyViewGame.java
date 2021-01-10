/* package com.example.lineapp.appgame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import com.example.lineapp.lineapp5.DesignPatch;

import java.util.ArrayList;
import java.util.Random;

import static android.view.MotionEvent.INVALID_POINTER_ID;

public class MyViewGame {
}
*/


package com.example.lineapp.appgame;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyViewGame extends SurfaceView implements SurfaceHolder.Callback {

    private static final String TAG = "MyActivity";
    private final List<Joystick1> joystick1List = new ArrayList<>(2);
    private final List<Integer> joystickPressedList = new ArrayList<>(2);
    private final int posicionJ1_X;
    private final int posicionJ1_Y;
    private final int posicionJ2_X;
    private final int posicionJ2_Y;
    private final HashMap<Integer, PlayerLine> playersList = new HashMap<>();
    private final HashMap<Integer, Ball> ballHashMap = new HashMap<>();
    private final Paint paint = new Paint();
    private MyViewGameLoop viewLoop;
    private boolean startGame = false;

    public MyViewGame(Context context) {
        super(context);

        // Get surface holder and add callback
        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

        posicionJ1_X = (getScreenWidth() / 2);
        posicionJ2_X = getScreenWidth() / 2;

        posicionJ1_Y = 150;
        posicionJ2_Y = getScreenHeight() - getScreenHeight() / 5;

        joystick1List.add(new Joystick1(1, posicionJ1_X, posicionJ1_Y, 70, 40, Color.GREEN));
        joystick1List.add(new Joystick1(2, posicionJ2_X, posicionJ2_Y, 70, 40, Color.RED));

        PlayerLine jugador1 = new PlayerLine(posicionJ1_X - 75, posicionJ1_Y + 200, Color.GREEN);
        PlayerLine jugador2 = new PlayerLine(posicionJ2_X - 75, posicionJ2_Y - 200, Color.RED);
        Ball ball = new Ball(getScreenWidth() / 2, getScreenHeight() / 2 - 100, 35, Color.WHITE);
        playersList.put(1, jugador1);
        playersList.put(2, jugador2);
        ballHashMap.put(1, ball);

        viewLoop = new MyViewGameLoop(this, surfaceHolder);
        // Initialize game panels
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        //gameDisplay = new MVGameDisplay(displayMetrics.widthPixels, displayMetrics.heightPixels, player);
        setFocusable(true);
    }

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    public static void setTimeout(Runnable runnable, int delay) {
        new Thread(() -> {
            try {
                Thread.sleep(delay);
                runnable.run();
            } catch (Exception e) {
                System.err.println(e);
            }
        }).start();
    }

    private boolean checkTouchPlayer2Ball() {
        try {
            Ball ball = this.ballHashMap.get(1);
            PlayerLine pl = this.playersList.get(2);
            int posXBall = ball.getX();
            int posYBall = ball.getY();
            if (!ball.isUp()) { // Down ball
                if (pl.getStartX() <= posXBall && pl.getStopX() >= posXBall
                        && pl.getStartY() - 40 <= posYBall && pl.getStopY() >= posYBall) {
                    ball.setUp(true);
                    return true;
                } else {
                    pl = this.playersList.get(1);
                    if (pl.getStartX() <= posXBall && pl.getStopX() >= posXBall
                            && pl.getStartY() <= posYBall && posYBall <= pl.getStopY() + 40) { // Ball is setting to down
                        ball.setUp(false);
                        setTimeout(() -> {
                            ball.setY(posYBall + 40);
                            updateDown(getScreenHeight() - 250);
                        }, 5);
                    }
                }
            } else { // Up ball
                if (pl.getStartX() <= posXBall && pl.getStopX() >= posXBall
                        && pl.getStartY() - 40 <= posYBall && pl.getStopY() >= posYBall) { // Ball is setting to up
                    ball.setUp(true);
                    setTimeout(() -> {
                        ball.setY(posYBall - 41);
                        updateUp(150);
                    }, 5);
                } else {
                    pl = this.playersList.get(1);
                    if (pl.getStartX() <= posXBall && pl.getStopX() >= posXBall
                            && pl.getStartY() <= posYBall && posYBall <= pl.getStopY() + 40) {
                        ball.setUp(false);
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Handle user input touch event actions
        int index = event.getActionIndex();
        try {
            switch (event.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                    float x = event.getX();
                    float y = event.getY();
                    joystickPressedList.clear();
                    if (joystick1List.get(0).isPressed(x, y)) {
                        joystick1List.get(0).setIsPressed(true);
                        joystickPressedList.add(0);
                    } else if (joystick1List.get(1).isPressed(x, y)) {
                        joystick1List.get(1).setIsPressed(true);
                        joystickPressedList.add(1);
                    }
                    break;
                case MotionEvent.ACTION_POINTER_DOWN:
                    if (joystickPressedList.size() == 1) {
                        int joy = joystickPressedList.get(0) == 0 ? 1 : 0;
                        joystickPressedList.add(joy);
                    }
                    Joystick1 joystick = joystickPressedList.get(event.getActionIndex()) == 0 ? joystick1List.get(0) : joystick1List.get(1);
                    joystick.setIsPressed(true);
                    break;
                case MotionEvent.ACTION_MOVE:
                    for (int i = 0; i < joystickPressedList.size(); i++) {
                        int currentIDPoint = event.getPointerId(i);
                        Joystick1 joys = joystick1List.get(joystickPressedList.get(i));
                        double lX = event.getX(event.findPointerIndex(currentIDPoint));
                        double lY = event.getY(event.findPointerIndex(currentIDPoint));
                        double left;
                        boolean leftBool = false;
                        int size = getScreenWidth() - 150;
                        if (lX < posicionJ1_X) {
                            leftBool = true;
                            left = ((lX + 100) < (getScreenWidth() - 200)) ? (lX + 100) : (getScreenWidth() - 200);
                        } else {
                            left = lX - 200 <= 0 ? 0 : lX - 200;
                            size = 0;
                        }
                        left = (int) left;
                        joys.setActuator(lX, lY);
                        playersList.get(joys.getId()).setX(left, leftBool, size);
                    }
                    break;
                case MotionEvent.ACTION_POINTER_UP:
                    int joyUp = joystickPressedList.get(event.getActionIndex());
                    int intJoyDown = joystickPressedList.get(joyUp == 0 ? 1 : 0);
                    joystickPressedList.clear();
                    joystickPressedList.add(intJoyDown);
                    joystick1List.get(joyUp).setColor(joyUp == 0 ? Color.GREEN : Color.RED);
                    joystick1List.get(joyUp).setIsPressed(false);
                    joystick1List.get(joyUp).resetActuator();
                    break;
                case MotionEvent.ACTION_UP:
                    joystickPressedList.clear();
                    for (int i = 0; i < joystick1List.size(); i++) {
                        joystick1List.get(i).setColor(i == 0 ? Color.GREEN : Color.RED);
                        joystick1List.get(i).setPressed(false);
                        joystick1List.get(i).resetActuator();
                    }
                    break;
            }
        } catch (Exception ex) {
            Log.i(TAG, " EEE" + ex);
            return false;
        }
        return true;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.d("Game.java", "surfaceCreated()");
        if (viewLoop.getState().equals(Thread.State.TERMINATED)) {
            SurfaceHolder surfaceHolder = getHolder();
            surfaceHolder.addCallback(this);
            viewLoop = new MyViewGameLoop(this, surfaceHolder);
        }
        if (!startGame) {
            startGame = !startGame;
            setTimeout(() -> {
                updateDown(getScreenHeight() - 250);
            }, 1000);
        }
        viewLoop.startLoop();
    }

    public void updateDown(double x) {
        int y = ballHashMap.get(1).getY();
        boolean stop = false;
        while (y < x && !stop) {
            try {
                ballHashMap.get(1).setUp(false);
                int velocity = (int) (x * 0.002);
                y += velocity;
                ballHashMap.get(1).setY(y);
                double velocityX = ballHashMap.get(1).getX() + (ballHashMap.get(1).getX() * 0.0002) + 1;
                ballHashMap.get(1).setX((int) velocityX);
                stop = checkTouchPlayer2Ball();
                Thread.sleep(5);
            } catch (Exception e) {
                ballHashMap.get(1).setY((int) x);
            }
            y = stop ? (int) x : ballHashMap.get(1).getY();
        }
        if (x == y) {
            checkTouchPlayer2Ball();
        }
    }

    public void updateUp(double x) {
        int y = ballHashMap.get(1).getY();
        boolean stop = false;
        while (x < y && !stop) {
            try {
                ballHashMap.get(1).setUp(true);
                double velocity = (x * 0.02);
                y -= velocity;
                ballHashMap.get(1).setY(y);
                double velocityX = ballHashMap.get(1).getX() - (ballHashMap.get(1).getX() * 0.0002) - 1;
                ballHashMap.get(1).setX((int) velocityX);
                stop = checkTouchPlayer2Ball();
                Thread.sleep(5);
            } catch (Exception e) {
                ballHashMap.get(1).setY((int) x);
            }
            y = stop ? (int) x : ballHashMap.get(1).getY();
        }
        if (x == y) {
            checkTouchPlayer2Ball();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.d("Game.java", "surfaceChanged()");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.d("Game.java", "surfaceDestroyed()");
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        for (Joystick1 j : joystick1List) {
            j.draw(canvas);
            DisplayMetrics displayMetrics = new DisplayMetrics();
            ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            paint.setColor(j.getColor());
        }
        playersList.get(1).draw(canvas);
        playersList.get(2).draw(canvas);
        ballHashMap.get(1).draw(canvas);
    }

    public void update() {
        for (Joystick1 j : joystick1List) {
            j.update();
        }
    }

    public void pause() {
        viewLoop.stopLoop();
    }
}