package com.example.lineapp.lineapp2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Random;

public class MyView2 extends View {
    Random random = new Random();
    Paint paint = new Paint();
    float prevX, prevY, newX, newY;
    int color = Color.BLACK;

    ArrayList<Float[]> lineaPinta = new ArrayList<>();
    //float lineaPinta[] = new float[2];
    //float matrizLineas[] = new float[4];
    public void LineaAlmacena(float x, float y, float newX, float newY, float pintura) {
        Float matrizLineas[] = new Float[5];
        //matrizLineas[] = new float[4];
        matrizLineas[0]= x;
        matrizLineas[1] = y;
        matrizLineas[2] = newX;
        matrizLineas[3] = newY;
        matrizLineas[4] = pintura;
        lineaPinta.add(0,matrizLineas);
    }

    public MyView2(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(6f);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(this.color);
        for(int i = 0; i < lineaPinta.size(); i++){
            Float  matrizLineas[] = new Float[5];
            matrizLineas=lineaPinta.get(i);
            int ss = matrizLineas[4].intValue();;
            paint.setColor((int) matrizLineas[4].intValue());
            canvas.drawLine(matrizLineas[0], matrizLineas[1],matrizLineas[2],matrizLineas[3], paint);
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                prevX = event.getX();
                prevY = event.getY();
                this.color = Color.rgb(random.nextInt(255), random.nextInt(255),random.nextInt(255));
                break;
            case MotionEvent.ACTION_MOVE:
                newX = event.getX();
                newY = event.getY();
                this.invalidate();
                break;
            case MotionEvent.ACTION_UP:
                LineaAlmacena(this.prevX, this.prevY, this.newX, this.newY, this.color);
                prevY = -1;
                newX = -1;
                prevX = -1;
                newY = -1;
                //this.invalidate();
                break;
        }
        return  true;
    }
}
