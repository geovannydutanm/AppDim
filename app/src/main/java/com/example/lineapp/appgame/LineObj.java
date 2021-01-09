package com.example.lineapp.appgame;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class LineObj {
    private Paint paint;
    private int x;
    private int y;
    private Rect rect;

    public LineObj(int x, int y, int color) {
        this.x = x;
        this.y = y;
        paint = new Paint();
        paint.setColor(color);
    }

    public void draw(Canvas canvas) {
        this.rect = new Rect(this.x, this.y, this.x + 150, this.y + 20);
        canvas.drawRect(this.rect, paint);
    }

    public void draw(int startX, int startY, int stopX, int stopY, Canvas canvas) {
        this.rect = new Rect(startX, startY, stopX, stopY);
        canvas.drawRect(this.rect, paint);
    }

    public Paint getPaint() {
        return paint;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Rect getRect() {
        return rect;
    }

    public void setRect(Rect rect) {
        this.rect = rect;
    }

    public void setColor(int color) {
        this.paint.setColor(color);
    }
}