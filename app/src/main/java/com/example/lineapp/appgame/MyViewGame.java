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
import android.graphics.Color;
import android.graphics.Path;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//import com.example.lineapp.lineapp5.DesignPatch;


public class MyViewGame extends SurfaceView implements SurfaceHolder.Callback {

    //private Game game;

    private static final String TAG = "MyActivity";
    private final List<Joystick1> joystick1List = new ArrayList<>(2);
    private final long touchDownTime = -1;
    private final long touchUpTime = -1;
    private final float touchX = -1;
    private final float touchY = -1;
    private final HashMap<Integer, Joystick1> DesignJoysticks = new HashMap<Integer, Joystick1>();
    private final int numeroAtaque = 0;
    private final List<Integer> joystickPressedList = new ArrayList<>(2);
    private final Joystick1 joystick1;
    private final Joystick1 joystick2;
    int posicionJ1_X = 100;
    int posicionJ1_y = 700;
    int posicionJ2_X = 900;
    int posicionJ2_Y = 900;
    int index_joystickPointer1;
    int index_joystickPointer2;
    private int joystickPointer1Id = 0;
    private int joystickPointer2Id = 0;
    private MyViewGameLoop viewLoop;
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


        joystick1 = new Joystick1(posicionJ1_X, posicionJ1_y, 70, 40, Color.GREEN);
        joystick2 = new Joystick1(posicionJ2_X, posicionJ2_Y, 70, 40, Color.RED);
        joystick1List.add(joystick1);
        joystick1List.add(joystick2);

        viewLoop = new MyViewGameLoop(this, surfaceHolder);

        // Initialize game panels
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        setFocusable(true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Handle user input touch event actions
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                float x = event.getX();
                float y = event.getY();
                joystickPressedList.clear();
                if (joystick1List.get(0).isPressed(x, y)) {
                    joystickPointer1Id = event.getPointerId(event.getActionIndex());
                    joystick1.setIsPressed(true);
                    joystickPressedList.add(0);
                } else if (joystick1List.get(1).isPressed(x, y)) {
                    joystickPointer2Id = event.getPointerId(event.getActionIndex());
                    joystick1List.get(1).setIsPressed(true);
                    joystickPressedList.add(1);
                }
                return true;
            case MotionEvent.ACTION_POINTER_DOWN:
                if (joystickPressedList.size() == 1) {
                    int joy = joystickPressedList.get(0) == 0 ? 1 : 0;
                    joystickPressedList.add(joy);
                }
                Joystick1 joystick = joystickPressedList.get(event.getActionIndex()) == 0 ? joystick1List.get(0) : joystick1List.get(1);
                joystick.setIsPressed(true);
                joystick.setColor(Color.DKGRAY);
                return true;
            case MotionEvent.ACTION_MOVE:
                for (int i = 0; i < joystickPressedList.size(); i++) {
                    int currentIDPoint = event.getPointerId(i);
                    Joystick1 joys = joystick1List.get(i);
                    double lX = event.getX(event.findPointerIndex(currentIDPoint));
                    double lY = event.getY(event.findPointerIndex(currentIDPoint));
                    joys.setActuator(lX, lY);
                }
                return true;
            case MotionEvent.ACTION_POINTER_UP:
                int joyUp = joystickPressedList.get(event.getActionIndex());
                int color = joyUp == 0 ? Color.GREEN : Color.RED;
                joystick1List.get(joyUp).setColor(color);
                joystick1List.get(joyUp).setIsPressed(false);
                joystick1List.get(joyUp).resetActuator();
                return true;

            /*
            case MotionEvent.ACTION_UP:
            */

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