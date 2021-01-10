package com.example.lineapp.lineapp3;

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

import com.example.lineapp.appgame.Joystick1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MyView3 extends View {

    private Bitmap canvasBitmap;
    int color = Color.BLACK;
    Random random = new Random();

    private SparseArray<Path> paths;
    //private final JoystickTest joystickTest;

    public MyView3(Context context) {
        super(context);
        setupDrawing();
        //joystickTest = new JoystickTest(0, 0, 70, 40);
    }

    public MyView3(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupDrawing();
        //joystickTest = new JoystickTest(275, 700, 70, 40);
    }

    public MyView3(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setupDrawing();
        //joystickTest = new JoystickTest(275, 700, 70, 40);
    }

    private Paint dibujarPintar;
    private Paint  canvanPintar;
    private void setupDrawing() {
        paths = new SparseArray<>();
        dibujarPintar = new Paint();
        //int color = Color.rgb(random.nextInt(255), random.nextInt(255),random.nextInt(255));
        //dibujarPintar.setColor(this.color);
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
    Map<Integer, Integer> PuntosColor = new HashMap<Integer, Integer>();
    @Override
    protected void onDraw(Canvas canvas) {

        canvas.drawBitmap(canvasBitmap, 0, 0, canvanPintar);
        for (int i=0; i<paths.size(); i++) {
            paths.valueAt(i);
            int colr = PuntosColor.get(i);
            dibujarPintar.setColor(colr);
            canvas.drawPath(paths.valueAt(i), dibujarPintar);
            //joystickTest.draw(canvas);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int index = event.getActionIndex();
        int puntoId = event.getPointerId(index);
        Path pathMatriz;
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                pathMatriz = new Path();
                this.color = Color.rgb(random.nextInt(255), random.nextInt(255),random.nextInt(255));
                //pathMatriz.setColor(color);
                PuntosColor.put(puntoId,color);
                pathMatriz.moveTo(event.getX(index), event.getY(index));
                paths.put(puntoId, pathMatriz);
                break;
            case MotionEvent.ACTION_MOVE:
                for (int i=0; i<event.getPointerCount(); i++) {
                    puntoId = event.getPointerId(i);
                    pathMatriz = paths.get(puntoId);
                    if (pathMatriz != null) pathMatriz.lineTo(event.getX(i), event.getY(i));
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                paths.remove(puntoId);
                /*pathMatriz = paths.get(puntoId);
                if (pathMatriz != null) {
                    dibujarCanvas.drawPath(pathMatriz, dibujarPintar);
                    paths.remove(puntoId);
                }*/
                break;
            default:
                return false;
        }
        invalidate();
        return true;
    }

}