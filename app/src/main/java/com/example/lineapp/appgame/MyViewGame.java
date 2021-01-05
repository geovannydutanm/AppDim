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
import android.graphics.Canvas;
import android.graphics.Path;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.HashMap;

//import com.example.lineapp.lineapp5.DesignPatch;


public class MyViewGame extends SurfaceView implements SurfaceHolder.Callback {

    //private Game game;

    private static final String TAG = "MyActivity";
    private final Joystick1 joystick1;
    private final Joystick2 joystick2;
    private final long touchDownTime = -1;
    private final long touchUpTime = -1;
    private final float touchX = -1;
    private final float touchY = -1;
    private final HashMap<Integer, Joystick1> DesignJoysticks = new HashMap<Integer, Joystick1>();
    int posicionJ1_X = 100;
    int posicionJ1_y = 700;
    int posicionJ2_X = 900;
    int posicionJ2_y = 900;
    int index_joystickPointer1;
    int index_joystickPointer2;
    private int joystickPointer1Id = 0;
    private int joystickPointer2Id = 0;
    private MyViewGameLoop viewLoop;
    private int numeroAtaque = 0;
    private double previousX1;
    private double previousY1;
    private double previousX2;
    private double previousY2;
    private SparseArray<Path> paths;

    public MyViewGame(Context context) {
        super(context);

        // Get surface holder and add callback
        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

        viewLoop = new MyViewGameLoop(this, surfaceHolder);

        // Initialize game panels
        joystick1 = new Joystick1(posicionJ1_X, posicionJ1_y, 70, 40);
        joystick2 = new Joystick2(posicionJ2_X, posicionJ2_y, 70, 40);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        setFocusable(true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        // Handle user input touch event actions
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                if (joystick1.isPressed((double) event.getX(), (double) event.getY())) {
                    // Joystick is pressed in this event -> setIsPressed(true) and store pointer id
                    joystickPointer1Id = event.getPointerId(event.getActionIndex());
                    joystick1.setIsPressed(true);
                } else {
                    numeroAtaque++;
                }
                if (joystick2.isPressed((double) event.getX(), (double) event.getY())) {
                    joystickPointer2Id = event.getPointerId(event.getActionIndex());
                    joystick2.setIsPressed(true);
                } else {
                    numeroAtaque++;
                }
                return true;
            case MotionEvent.ACTION_MOVE:
                if (joystickPointer1Id == event.getPointerId(event.getActionIndex())) {
                    joystick1.setActuator(event.getX(joystickPointer1Id), event.getX(joystickPointer1Id));
                }
                if (joystickPointer2Id == event.getPointerId(event.getActionIndex())) {
                    joystick2.setActuator(event.getX(joystickPointer2Id), event.getX(joystickPointer2Id));
                }
                return true;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                if (joystickPointer1Id == event.getPointerId(event.getActionIndex())) {
                    joystick1.setIsPressed(false);
                    joystick1.resetActuator();
                    Log.i(TAG, " 11 POINT UPPP X:: " + previousX1 + " Y:: " + previousY1);
                }
                if (joystickPointer1Id == event.getPointerId(event.getActionIndex())) {
                    joystick2.setIsPressed(false);
                    joystick2.resetActuator();
                    Log.i(TAG, " 2222 POINT UPP  X:: " + previousX2 + " Y:: " + previousY2);
                }
                return true;
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
        viewLoop.startLoop();
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
        joystick1.draw(canvas);
        joystick2.draw(canvas);
    }

    public void update() {
        joystick1.update();
        joystick2.update();
    }

    public void pause() {
        viewLoop.stopLoop();
    }
}