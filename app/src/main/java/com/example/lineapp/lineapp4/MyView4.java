
package com.example.lineapp.lineapp4;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Pair;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class MyView4 extends View {



    private Bitmap canvasBitmap;
    int color = Color.BLACK;
    Random random = new Random();

    private SparseArray<Path> paths;

    public MyView4(Context context) {
        super(context);
        setupDrawing();
    }

    public MyView4(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupDrawing();
    }

    public MyView4(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setupDrawing();
    }

    private Paint dibujarPintar;
    private Paint  canvanPintar;
    private void setupDrawing() {
        paths = new SparseArray<>();

        dibujarPintar = new Paint();
        int color = Color.rgb(random.nextInt(255), random.nextInt(255),random.nextInt(255));
        dibujarPintar.setColor(color);
        dibujarPintar.setAntiAlias(true);
        dibujarPintar.setStrokeWidth(10);
        dibujarPintar.setStyle(Paint.Style.STROKE);
        dibujarPintar.setStrokeJoin(Paint.Join.ROUND);
        dibujarPintar.setStrokeCap(Paint.Cap.ROUND);

        canvanPintar = new Paint(Paint.DITHER_FLAG);
    }

    private Canvas dibujarCanvas;

    @Override
    protected void onSizeChanged(int ancho, int alto, int prevancho, int prevalto) {
        super.onSizeChanged(ancho, alto, prevancho, prevalto);
        canvasBitmap = Bitmap.createBitmap(ancho, alto, Bitmap.Config.ARGB_8888);
        dibujarCanvas = new Canvas(canvasBitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for(int i = 0; i < DesignPs.size(); i++) {
            if (DesignPs.get(i) != null) {
                dibujarPintar.setColor(DesignPs.get(i).getColor());
                canvas.drawPath(vDireccion, dibujarPintar);
                canvas.drawPath(DesignPs.get(i), dibujarPintar);
            }
        }
    }
    float newX, newY;
    private HashMap<Integer, DesignPatch> DesignPs = new HashMap<Integer, DesignPatch>();
    private DesignPatch vDireccion = new DesignPatch();

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int index = event.getActionIndex();
        int puntoId = event.getPointerId(index);
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                this.color = Color.rgb(random.nextInt(255), random.nextInt(255), random.nextInt(255));
                DesignPatch designP = new DesignPatch();
                //designP.moveTo(event.getX(index), event.getY(index));
                designP.setColor(color);
                DesignPs.put(index, designP);
                break;
            case MotionEvent.ACTION_MOVE:
                for (int i = 0; i < event.getPointerCount(); i++) {
                    if ( DesignPs.get(event.getPointerId(i)) != null) {
                        DesignPs.get(event.getPointerId(i)).reset();
                        DesignPs.get(event.getPointerId(i)).addCircle(event.getX(i),event.getY(i),100,Path.Direction.CW);
                        if(i == 2) {
                            vDireccion.reset();
                            vDireccion.moveTo((((event.getX(1))+(event.getX(0)))/2), ((event.getY(1)) + (event.getY(0)))/2);
                            newX = event.getX(2) - (((event.getX(1))+(event.getX(0)))/2);
                            newY = event.getY(2) - (((event.getY(1))+(event.getY(0)))/2);
                            vDireccion.lineTo((event.getX(2) + newX),(event.getY(2) + newY));
                        }
                    }
                }
                //this.invalidate();
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_CANCEL:
                DesignPs.remove(puntoId);
                vDireccion.reset();
                break;
        }
        this.invalidate();
        return true;
    }

    public class DesignPatch extends Path {
        int color;
        float posActualX;
        float posActualY;

        public DesignPatch() {
        }

        public DesignPatch(@Nullable Path src) {
            super(src);
        }

        public int getColor() {
            return color;
        }


        public void setColor(int color) {
            this.color = color;
        }

        public float getPosActualX() {
            return posActualX;
        }

        public float getPosActualY() {
            return posActualY;
        }

        public void setPosActualX(float posActualX) {
            this.posActualX = posActualX;
        }

        public void setPosActualY(float posActualY) {
            this.posActualY = posActualY;
        }



    }



}


