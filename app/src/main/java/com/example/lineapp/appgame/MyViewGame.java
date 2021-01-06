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

    private final int posicionJugador1_X = 250;
    private final int posicionJugador1_Y = 250;
    private final int posicionJugador2_X = 800;
    private final int posicionJugador2_Y = 800;
    private final int posicionJ1_X;
    private final int posicionJ1_Y;
    private final int posicionJ2_X;
    private final int posicionJ2_Y;
    private final HashMap<Integer, Players> PlayersList = new HashMap<Integer, Players>();
    private final Paint paint = new Paint();
    private MyViewGameLoop viewLoop;
    //private final Players player;
    private MVGameDisplay gameDisplay;
    private MVGameDisplay gameDisplay2;
    private Canvas Jugador1;
    private Canvas Jugador2;

    public MyViewGame(Context context) {
        super(context);

        // Get surface holder and add callback
        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);


        Players jugador1 = new Players(posicionJugador1_X, posicionJugador1_Y, 30, Color.GREEN);
        Players jugador2 = new Players(posicionJugador2_X, posicionJugador2_Y, 30, Color.RED);
        jugador1.setActuatorX(posicionJugador1_X);
        jugador1.setActuatorY(posicionJugador1_Y);
        PlayersList.put(1, jugador1);
        PlayersList.put(2, jugador2);
        posicionJ1_X = getScreenWidth() / 2;
        posicionJ2_X = getScreenWidth() / 2;

        posicionJ1_Y = 150;
        posicionJ2_Y = getScreenHeight() - getScreenHeight() / 5;

        joystick1List.add(new Joystick1(posicionJ1_X, posicionJ1_Y, 70, 40, Color.GREEN));
        joystick1List.add(new Joystick1(posicionJ2_X, posicionJ2_Y, 70, 40, Color.RED));


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


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Handle user input touch event actions
        int index = event.getActionIndex();
        int puntoId = event.getPointerId(index);
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
                        joys.setActuator(lX, lY);

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
        for (Joystick1 j : joystick1List) {
            j.draw(canvas);
            DisplayMetrics displayMetrics = new DisplayMetrics();
            ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            paint.setColor(j.getColor());
            PlayersList.get(1).setActuatorX(j.getActuatorX());
        }
        PlayersList.get(1).draw(canvas);
        PlayersList.get(2).draw(canvas);

    }

    public void update() {
        for (Joystick1 j : joystick1List) {
            j.update();
        }
        //player.update();
    }

    public void pause() {
        viewLoop.stopLoop();
    }
}