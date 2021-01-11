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
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class MyViewGame extends SurfaceView implements SurfaceHolder.Callback {

    private static final String TAG = "Activiyapp_game";
    private final List<Joystick> joystickList = new ArrayList<>(2);
    private final List<Integer> joystickPressedList = new ArrayList<>(2);
    //private final Joystick1 joystick1;

    private final int posicionJ1_X;
    private final int posicionJ1_Y;
    private final int posicionJ2_X;
    private final int posicionJ2_Y;
    private final HashMap<Integer, Players> playersList = new HashMap<Integer, Players>();
    private final Paint paint = new Paint();
    private MyViewGameLoop viewLoop;
    //private final HashMap<Integer, Ataque> ataqueList = new HashMap<Integer, Ataque>();
    private List<Attack> ataqueList = new ArrayList<Attack>();
    private List<ObjectiveEnemy> objectiveEnemyList = new ArrayList<ObjectiveEnemy>();
    Random random = new Random();

    public MyViewGame(Context context) {
        super(context);
        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

        posicionJ1_X = getScreenWidth() / 2;
        posicionJ2_X = getScreenWidth() / 2;
        posicionJ1_Y = 150;
        posicionJ2_Y = getScreenHeight() - getScreenHeight() / 5;
        joystickList.add(new Joystick(1, posicionJ1_X, posicionJ1_Y, 70, 40, Color.GREEN));
        joystickList.add(new Joystick(2, posicionJ2_X, posicionJ2_Y, 70, 40, Color.RED));

        Players jugador1 = new Players(posicionJ1_X, posicionJ1_Y+100, 30, Color.GREEN);
        Players jugador2 = new Players(posicionJ2_X, posicionJ2_Y-100, 30, Color.RED);
        playersList.put(1, jugador1);
        playersList.put(2, jugador2);

        viewLoop = new MyViewGameLoop(this, surfaceHolder);
        DisplayMetrics displayMetrics = new DisplayMetrics();
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
        int index = event.getActionIndex();
        int puntoId = event.getPointerId(index);
        float indexX = event.getX(index);
        float indexY = event.getY(index);
        float pressNegX1= posicionJ1_X-100;
        float pressPstX1= posicionJ1_X+100;
        float pressNegY1= posicionJ1_Y-100;
        float pressPstY1= posicionJ1_Y+100;

        float pressNegX2= posicionJ2_X-100;
        float pressPstX2= posicionJ2_X+100;
        float pressNegY2= posicionJ2_Y-100;
        float pressPstY2= posicionJ2_Y+100;
        try {
            switch (event.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_POINTER_DOWN:
                    indexX = event.getX(index);
                    indexY = event.getY(index);
                    indexX = event.getX(index);
                    indexY = event.getY(index);
                    if (indexX >= pressNegX1 & indexX <= pressPstX1 &
                            indexY >= pressNegY1 & indexY <= pressPstY1)
                    {
                        joystickList.get(0).setIsPressed(true);
                        joystickList.get(0).setCurrentIDPoint(puntoId);
                    }
                    if (indexX >= pressNegX2 & indexX <= pressPstX2 &
                            indexY >= pressNegY2 & indexY <= pressPstY2)
                    {
                        joystickList.get(1).setIsPressed(true);
                        joystickList.get(1).setCurrentIDPoint(puntoId);

                    }
                    break;
                case MotionEvent.ACTION_MOVE:
                    for (int i = 0; i < event.getPointerCount(); i++) {
                        indexX = event.getX(i);
                        indexY = event.getY(i);
                        if (indexX >= pressNegX1 & indexX <= pressPstX1 &
                                indexY >= pressNegY1 & indexY <= pressPstY1)
                        {
                            int getcurrentIDPoint = joystickList.get(0).getCurrentIDPoint();
                            double lX = event.getX(event.findPointerIndex(getcurrentIDPoint));
                            double lY = event.getY(event.findPointerIndex(getcurrentIDPoint));
                            joystickList.get(0).setActuator(lX, lY);
                            playersList.get(1).setX(lX + 100);
                            int id=ataqueList.size()+1;
                            int idJugador= 1;
                            int posAtaqueX=(int) playersList.get(idJugador).getX();
                            int posAtaquey=(int) playersList.get(idJugador).getY();
                            ataqueList.add(new Attack(id, idJugador,posAtaqueX, posAtaquey, joystickList.get(0).getColor()));
                        }
                        if (indexX >= pressNegX2 & indexX <= pressPstX2 &
                                indexY >= pressNegY2 & indexY <= pressPstY2)
                        {
                            int getcurrentIDPoint = joystickList.get(1).getCurrentIDPoint();
                            double lX = event.getX(event.findPointerIndex(getcurrentIDPoint));
                            double lY = event.getY(event.findPointerIndex(getcurrentIDPoint));
                            joystickList.get(1).setActuator(lX, lY);
                            playersList.get(2).setX(lX + 100);
                            int id=ataqueList.size()+1;
                            int idJugador= 2;
                            int posAtaqueX=(int) playersList.get(idJugador).getX();
                            int posAtaquey=(int) playersList.get(idJugador).getY();
                            ataqueList.add(new Attack(id, idJugador,posAtaqueX, posAtaquey, joystickList.get(1).getColor()));
                        }
                    }
                    return true;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_POINTER_UP:
                    joystickPressedList.clear();
                    for (int i = 0; i < joystickList.size(); i++) {
                        joystickList.get(i).setColor(i == 0 ? Color.GREEN : Color.RED);
                        joystickList.get(i).setPressed(false);
                        joystickList.get(i).resetActuator();
                    }
                    int idEnemy=objectiveEnemyList.size()+1;
                    int posEnemyy = getScreenHeight()/ random.nextInt(6);
                    int puntoId1 = event.getPointerId(index);
                    objectiveEnemyList.add(new ObjectiveEnemy(idEnemy,(int) event.getX(puntoId1), (int) event.getY(puntoId1), Color.YELLOW));
                    return true;
            }
        } catch (Exception ex) {
            Log.i(TAG, " Error" + ex);
            return false;
        }
        return true;
    }

    public static boolean isColliding(Attack obj1, ObjectiveEnemy obj2) {
        double distance = getDistanceBetweenObjects(obj1, obj2);
        double distanceToCollision = obj1.getOuterCircleRadius() + obj2.getOuterCircleRadius();
        if (distance < distanceToCollision)
            return true;
        else
            return false;
    }

    public static double getDistanceBetweenObjects(Attack obj1, ObjectiveEnemy obj2) {
        return Math.sqrt(
                Math.pow(obj2.getX() - obj1.getX(), 2) +
                        Math.pow(obj2.getX() - obj1.getX(), 2)
        );
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (viewLoop.getState().equals(Thread.State.TERMINATED)) {
            SurfaceHolder surfaceHolder = getHolder();
            surfaceHolder.addCallback(this);
            viewLoop = new MyViewGameLoop(this, surfaceHolder);
        }
        viewLoop.startLoop();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        try {
            for (Joystick j : joystickList) {
                j.draw(canvas);
                DisplayMetrics displayMetrics = new DisplayMetrics();
                ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                paint.setColor(j.getColor());
            }
            playersList.get(1).draw(canvas);
            playersList.get(2).draw(canvas);

            for (Attack atq : ataqueList) {
                atq.draw(canvas);
            }
            for (ObjectiveEnemy obj : objectiveEnemyList) {
                obj.draw(canvas);
            }
        }catch(Exception ex){

        }
    }

    public void update() {
        try {
            for (Joystick j : joystickList) {
                j.update();
            }
            for (ObjectiveEnemy obj : objectiveEnemyList) {
                obj.update();
            }
            for (Attack atq : ataqueList) {
                if(atq.getY()<100)
                {
                    ataqueList.remove(atq);
                }
                else{
                    atq.update();
                }
            }
            //Capturar Ataques
            Iterator<ObjectiveEnemy> iteratorEnemy = objectiveEnemyList.iterator();
            while (iteratorEnemy.hasNext()) {
                ObjectiveEnemy enemy = iteratorEnemy.next();
                Iterator<Attack> iteratorSpell = ataqueList.iterator();
                while (iteratorSpell.hasNext()) {
                    Attack spell = iteratorSpell.next();
                    if (isColliding(spell, enemy)) {
                        iteratorSpell.remove();
                        iteratorEnemy.remove();
                        break;
                    }
                }
            }
        }catch(Exception ex){
        }
    }

    public void pause() {
        viewLoop.stopLoop();
    }
}