package com.example.lineapp.lineapp5;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;



import java.util.ArrayList;
import java.util.Random;

import static android.view.MotionEvent.INVALID_POINTER_ID;


public class MyView5 extends View implements  GestureDetector.OnGestureListener,
        GestureDetector.OnDoubleTapListener, ScaleGestureDetector.OnScaleGestureListener {

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

    public MyView5(Context context, AttributeSet attrs) {
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
        dpath.addRoundRect(rect,20f,20f,Path.Direction.CCW);
        /*
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)
            dpath.addRoundRect(new RectF(left,top,right,bottom), rect, Path.Direction.CW);
        else
            dpath.addRoundRect(left,top,right,bottom, matrizLineas,Path.Direction.CCW);
        //mPath.addRoundRect(0, 0, width, height, mRadii, Path.Direction.CW);
        */
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
            //dpath.addRoundRect(prevX + dpath.getdesignPatchDimension()*DIMENSION, prevY + dpath.getdesignPatchDimension()*DIMENSION, prevX -  dpath.getdesignPatchDimension()*DIMENSION, prevY -  dpath.getdesignPatchDimension()*DIMENSION, 20, 20, Path.Direction.CW);
            //dpath.addRoundRect(
            // prevX + dpath.getdesignPatchDimension()*DIMENSION,
            // prevY + dpath.getdesignPatchDimension()*DIMENSION,
            // prevX -  dpath.getdesignPatchDimension()*DIMENSION,
            // prevY -  dpath.getdesignPatchDimension()*DIMENSION,
            // 20, 20, Path.Direction.CW);
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
            //dpathScale.addRoundRect(prevX + dpathScale.getdesignPatchDimension()*DIMENSION, prevY + dpathScale.getdesignPatchDimension()*DIMENSION, prevX -  dpathScale.getdesignPatchDimension()*DIMENSION, prevY -  dpathScale.getdesignPatchDimension()*DIMENSION, 20, 20, Path.Direction.CW);
            //dpathScale.addRoundRect(
            // prevX + dpathScale.getdesignPatchDimension()*DIMENSION,
            // prevY + dpathScale.getdesignPatchDimension()*DIMENSION,
            // prevX -  dpathScale.getdesignPatchDimension()*DIMENSION,
            // prevY -  dpathScale.getdesignPatchDimension()*DIMENSION,
            // 20, 20, Path.Direction.CW);

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
    }
}