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
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseArray;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;


import com.example.lineapp.lineapp4.MyView4;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
//import com.example.lineapp.lineapp5.DesignPatch;

import static android.view.MotionEvent.INVALID_POINTER_ID;



public class MyViewGame extends SurfaceView implements SurfaceHolder.Callback{

    //private Game game;

    private int joystickPointer1Id = 0;
    private int joystickPointer2Id = 0;
    private final Joystick1 joystick1;
    private final Joystick2 joystick2;
    int posicionJ1_X =100;
    int posicionJ1_y =700;
    int posicionJ2_X =400;
    int posicionJ2_y =400;


    //private HashMap<Integer, MyView4.DesignPatch> DesignPs = new HashMap<Integer, MyView4.DesignPatch>();
    private HashMap<Integer, Joystick1> DesignJoysticks = new HashMap<Integer, Joystick1>();
    //private final Joystick2 joystick2;

    //private List<Enemy> enemyList = new ArrayList<Enemy>();
    //private List<Spell> spellList = new ArrayList<Spell>();
    private MyViewGameLoop viewLoop;

    private int numeroAtaque = 0;

    /*
    private final Player player;



    private GameOver gameOver;
    private Performance performance;
    private GameDisplay gameDisplay;
    private Tilemap tilemap;*/

    public MyViewGame(Context context) {
        super(context);

        // Get surface holder and add callback
        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

        viewLoop = new MyViewGameLoop(this, surfaceHolder);

        // Initialize game panels
        //performance = new Performance(context, gameLoop);
        //gameOver = new GameOver(context);
        joystick1 = new Joystick1(posicionJ1_X, posicionJ1_y, 70, 40);
        joystick2 = new Joystick2(posicionJ2_X, posicionJ2_y, 70, 40);

        // Initialize game objects
        //player = new Player(context, joystick, 2*500, 500, 30);

        // Initialize display and center it around the player
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        //gameDisplay = new GameDisplay(displayMetrics.widthPixels, displayMetrics.heightPixels, player);

        // Initialize map
        //tilemap = new Tilemap(context, Level.LAYOUT1, gameDisplay);

        setFocusable(true);
    }

    int index_joystickPointer1;
    int index_joystickPointer2;
    private static final String TAG = "MyActivity";

    private SparseArray<Path> paths;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //int pointerIndex = event.getActionIndex();
        //int pointerId = event.getPointerId(pointerIndex);
        int pointer1Id = event.getPointerId(joystickPointer1Id);
        int pointer2Id = event.getPointerId(joystickPointer2Id);

        Log.i(TAG," POINT: " + pointer1Id);
        Log.i(TAG," POINT: " + pointer2Id);

        int index = event.getActionIndex();
        Path pathMatriz;
        int puntoId = event.getPointerId(index);
        // Handle user input touch event actions
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                pathMatriz = new Path();
                pathMatriz.moveTo(event.getX(index), event.getY(index));
                paths.put(puntoId, pathMatriz);
                if (joystick1.getIsPressed()) {
                    // Joystick was pressed before this event -> cast spell
                    numeroAtaque ++;
                    Log.i(TAG," INDEX MOVE 1 PRESSS" + pointer1Id);
                }else if (joystick1.isPressed(event.getX(0), event.getY(0))) {
                    // Joystick is pressed in this event -> setIsPressed(true) and store pointer id
                    joystickPointer1Id = event.getPointerId(pointer1Id);
                    joystick1.setIsPressed(true);
                    Log.i(TAG," INDEX MOVE 1 DDDDD" + pointer1Id);
                }
                else if (joystick2.getIsPressed()) {
                    // Joystick was pressed before this event -> cast spell
                    Log.i(TAG," INDEX MOVE 2  PRESSS" + pointer2Id);
                    numeroAtaque ++;
                } else if (joystick2.isPressed(event.getX(1), event.getY(1))) {
                    // Joystick is pressed in this event -> setIsPressed(true) and store pointer id
                    joystickPointer2Id = event.getPointerId(pointer2Id);
                    joystick2.setIsPressed(true);
                    Log.i(TAG," INDEX MOVE 2 DDDDD" + event.getPointerCount());
                } else {
                    // Joystick was not previously, and is not pressed in this event -> cast spell
                    numeroAtaque ++;
                }
                break;
                //return true;
            case MotionEvent.ACTION_MOVE:
                if (joystick1.getIsPressed()) {
                    // Joystick was pressed previously and is now moved
                    joystick1.setActuator((double) event.getX(index), (double) event.getY(index));
                    Log.i(TAG," INDEX MOVE 1, index "+ pointer1Id  +" DDDDD== "  + event.getPointerCount()+ " X= " + event.getX(pointer1Id) + "  y =" +event.getY(pointer1Id));
                }else if (joystick2.getIsPressed()) {
                    // Joystick was pressed previously and is now moved
                    joystick2.setActuator((double) event.getX(index), (double) event.getY(index));
                    Log.i(TAG," INDEX MOVE 2, index "+ pointer2Id  +" DDDDD== " + event.getPointerCount()+ " X= " + event.getX(pointer2Id) + "  y =" +event.getY(pointer2Id));
                }
                /*for (int i = 0; i < event.getPointerCount(); i++) {
                    if (joystick1.getIsPressed()) {
                        // Joystick was pressed previously and is now moved
                        joystick1.setActuator((double) event.getX(pointer1Id), (double) event.getY(pointer1Id));
                        Log.i(TAG," INDEX MOVE 1, index "+ pointer1Id  +" DDDDD== "  + event.getPointerCount()+ " X= " + event.getX(i) + "  y =" +event.getY(i));
                    }else if (joystick2.getIsPressed()) {
                        // Joystick was pressed previously and is now moved
                        joystick2.setActuator((double) event.getX(pointer2Id), (double) event.getY(pointer2Id));
                        Log.i(TAG," INDEX MOVE 2, index "+ pointer2Id  +" DDDDD== " + event.getPointerCount()+ " X= " + event.getX(i) + "  y =" +event.getY(i));
                    }
                }*/

                return true;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                if (joystickPointer1Id == event.getPointerId(event.getActionIndex())) {
                    // joystick pointer was let go off -> setIsPressed(false) and resetActuator()
                    joystick1.setIsPressed(false);
                    joystick1.resetActuator();
                }
                if (joystickPointer2Id == event.getPointerId(event.getActionIndex())) {
                    // joystick pointer was let go off -> setIsPressed(false) and resetActuator()
                    joystick2.setIsPressed(false);
                    joystick2.resetActuator();
                }
                return true;
        }

        return super.onTouchEvent(event);
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
        /*
        for(int i = 0; i < DesignJoysticks.size(); i++) {
            if (DesignJoysticks.get(i) != null) {
                if (joystick1.getIsPressed()) {
                    joystick1.draw(canvas);

                }
                else if (joystick2.getIsPressed()) {
                    joystick2.draw(canvas);
                    //DesignJoysticks.put(index, joystick2);
                }

            }
        }*/
        // Draw map
        //PENDIENTE
        //tilemap.draw(canvas, gameDisplay);

        // Draw game objects
        //JUGADOR NAVE
        //player.draw(canvas, gameDisplay);

        //OBJETO INCREMENTABLE ANEMIGO
        /*
        for (Enemy enemy : enemyList) {
            enemy.draw(canvas, gameDisplay);
        }

        for (Spell spell : spellList) {
            spell.draw(canvas, gameDisplay);
        }*/

        // Draw game panels

        //performance.draw(canvas);

        // Draw Game over if the player is dead
        /*
        if (player.getHealthPoint() <= 0) {
            gameOver.draw(canvas);
        }*/
    }

    public void update() {
        // Stop updating the game if the player is dead
        /*
        if (player.getHealthPoint() <= 0) {
            return;
        }*/

        // Update game state
        joystick1.update();
        joystick2.update();
        //player.update();

        // Spawn enemy
        /*
        if(Enemy.readyToSpawn()) {
            enemyList.add(new Enemy(getContext(), player));
        }

        // Update states of all enemies
        for (Enemy enemy : enemyList) {
            enemy.update();
        }

        // Update states of all spells
        while (numberOfSpellsToCast > 0) {
            spellList.add(new Spell(getContext(), player));
            numberOfSpellsToCast --;
        }
        for (Spell spell : spellList) {
            spell.update();
        }

        // Iterate through enemyList and Check for collision between each enemy and the player and
        // spells in spellList.
        Iterator<Enemy> iteratorEnemy = enemyList.iterator();
        while (iteratorEnemy.hasNext()) {
            Circle enemy = iteratorEnemy.next();
            if (Circle.isColliding(enemy, player)) {
                // Remove enemy if it collides with the player
                iteratorEnemy.remove();
                player.setHealthPoint(player.getHealthPoint() - 1);
                continue;
            }

            Iterator<Spell> iteratorSpell = spellList.iterator();
            while (iteratorSpell.hasNext()) {
                Circle spell = iteratorSpell.next();
                // Remove enemy if it collides with a spell
                if (Circle.isColliding(spell, enemy)) {
                    iteratorSpell.remove();
                    iteratorEnemy.remove();
                    break;
                }
            }
        }*/

        // Update gameDisplay so that it's center is set to the new center of the player's
        // game coordinates
        //gameDisplay.update();
    }

    public void pause() {
        viewLoop.stopLoop();
    }

    /*
    Paint paint = new Paint();
    float prevX, prevY;
    ArrayList<DesignPatch> designP = new ArrayList<>();
    private GestureDetector gDetector;
    private ScaleGestureDetector sDetector;
    private float DIMENSION = 100;
    private int activePointerId = INVALID_POINTER_ID;
    private boolean verificado;
    private DesignPatch dpathScale = new DesignPatch();
    private float escalaV = 1;
    private int color = Color.RED;
    Random random = new Random();

    public MyViewGame(Context context, AttributeSet attrs) {
        super(context, attrs);
        gDetector = new GestureDetector(context,this);
        gDetector.setOnDoubleTapListener(this);
        sDetector = new ScaleGestureDetector(context, this);
        //VERIFICAR
        paint.setAntiAlias(true);
        paint.setStrokeWidth(6f);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for(int i = 0; i < designP.size(); i++){
            paint.setColor(designP.get(i).getdesignPatchColor());

            canvas.drawPath(designP.get(i),paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean gd = gDetector.onTouchEvent(event);
        boolean sd = sDetector.onTouchEvent(event);
        int pointerIndex = event.getActionIndex();
        int pointerId = event.getPointerId(pointerIndex);

        switch (event.getActionMasked()) {

            case MotionEvent.ACTION_DOWN:
                prevX = event.getX(pointerIndex);
                prevY = event.getY(pointerIndex);
                activePointerId = event.getPointerId(0);
                break;
            case MotionEvent.ACTION_MOVE: {
                try {
                    pointerIndex = event.findPointerIndex(activePointerId);
                    prevX = event.getX(pointerIndex);
                    prevY = event.getY(pointerIndex);
                }
                catch(Exception e){}
                this.invalidate();
                break;
            }
            case MotionEvent.ACTION_POINTER_UP:
                if (pointerId == activePointerId) {
                    prevX = event.getX(pointerIndex);
                    prevY = event.getY(pointerIndex);
                    activePointerId = event.getPointerId(pointerIndex);
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL: {
                activePointerId = INVALID_POINTER_ID;
                break;
            }
        }
        return sd || gd || super.onTouchEvent(event);
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return false;
    }
*/
    /*
    @Override
    public boolean onDoubleTap(MotionEvent e) {
        DesignPatch dpath = new DesignPatch();
        this.color = Color.rgb(random.nextInt(255), random.nextInt(255), random.nextInt(255));
        dpath.setdesignPatchColor(color);
        dpath.moveTo(prevX,prevY);
        dpath.setdesignPatchPuntoX(prevX);
        dpath.setdesignPatchPuntoY(prevY);
        dpath.setdesignPatchDimension(1);
        float left =prevX+DIMENSION;
        float top = prevY+DIMENSION;
        float right =prevX-DIMENSION;
        float bottom=prevY-DIMENSION;

        RectF rect = new RectF(left, top, right, bottom);
        dpath.addRoundRect(rect,20f,20f, Path.Direction.CCW);
        designP.add(dpath);
        this.invalidate();
        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        DesignPatch dpath = new DesignPatch();
        int i;
        verificado = false;
        for(i = 0; i < designP.size(); i++){
            dpath = designP.get(i);
            if(distanceX+prevX>dpath.getdesignPatchPuntoX()-(DIMENSION*dpath.getdesignPatchDimension())
                    && distanceX+prevX<dpath.getdesignPatchPuntoX()+(DIMENSION*dpath.getdesignPatchDimension())
                    && distanceY+prevY<dpath.getDesignPatchPuntoY()+(DIMENSION*dpath.getdesignPatchDimension())
                    && distanceY+prevY>dpath.getDesignPatchPuntoY()-(DIMENSION*dpath.getdesignPatchDimension())){
                verificado = true;
                break;
            }
        }
        if(verificado) {
            int color = dpath.getdesignPatchColor();
            dpath.rewind();
            dpath.moveTo(prevX + distanceX, prevY + distanceY);
            dpath.setdesignPatchPuntoX(prevX + distanceX);
            dpath.setdesignPatchPuntoY(prevY + distanceY);
            dpath.setdesignPatchColor(color);
            float left =prevX + dpath.getdesignPatchDimension()*DIMENSION;
            float top = prevY + dpath.getdesignPatchDimension()*DIMENSION;
            float right =prevX -  dpath.getdesignPatchDimension()*DIMENSION;
            float bottom=prevY -  dpath.getdesignPatchDimension()*DIMENSION;
            RectF rect = new RectF(left, top, right, bottom);
            dpath.addRoundRect(rect,20f,20f,Path.Direction.CCW);
            designP.set(i, dpath);
        }
        this.invalidate();
        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return true;
    }

    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        escalaV *= detector.getScaleFactor();
        escalaV = Math.max(0.1f, Math.min(escalaV, 10.0f));
        if(dpathScale!=null){
            dpathScale.rewind();
            dpathScale.setdesignPatchDimension(escalaV);
            float left =prevX + dpathScale.getdesignPatchDimension()*DIMENSION;
            float top = prevY + dpathScale.getdesignPatchDimension()*DIMENSION;
            float right =prevX -  dpathScale.getdesignPatchDimension()*DIMENSION;
            float bottom=prevY -  dpathScale.getdesignPatchDimension()*DIMENSION;
            RectF rect = new RectF(left, top, right, bottom);
            dpathScale.addRoundRect(rect,20f,20f,Path.Direction.CCW);

        }
        this.invalidate();
        return true;
    }


    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        DesignPatch dpath = new DesignPatch();
        int i = 0;
        verificado = false;
        for(i = 0; i < designP.size(); i++){
            dpath = designP.get(i);
            if(prevX>dpath.getdesignPatchPuntoX()-(DIMENSION*dpath.getdesignPatchDimension())
                    && prevX<dpath.getdesignPatchPuntoX()+(DIMENSION*dpath.getdesignPatchDimension())
                    && prevY<dpath.getDesignPatchPuntoY()+(DIMENSION*dpath.getdesignPatchDimension())
                    && prevY>dpath.getDesignPatchPuntoY()-(DIMENSION*dpath.getdesignPatchDimension())){
                verificado = true;
                break;
            }
        }
        if(verificado) {
            dpathScale = dpath;
            escalaV = dpath.getdesignPatchDimension();
        }
        this.invalidate();
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {
        dpathScale = null;
        this.invalidate();
    }*/
}