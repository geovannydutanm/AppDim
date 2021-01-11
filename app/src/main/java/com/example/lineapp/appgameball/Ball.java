package com.example.lineapp.appgameball;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Ball {
    private Paint paint;
    private float radius;
    private int x;
    private int y;
    private int color;
    private Canvas canvas;
    private boolean up;

    public Ball(int x, int y, float radius, int color) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        paint = new Paint();
        paint.setColor(color);
        up = false;
    }

    public void draw(Canvas canvas) {
        this.canvas = canvas;
        canvas.drawCircle(this.x, this.y, this.radius, this.paint);
    }

    public Paint getPaint() {
        return paint;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
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

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public void updateDown(double x) {
        while (this.y < x) {
            try {
                this.up = false;
                int velocity = (int) (x * 0.002);
                this.y += velocity;
                Thread.sleep(5);
            } catch (Exception e) {
                this.y = (int) x;
            }
        }
    }

    public void updateUp(double x) {
        while (this.y < x) {
            try {
                this.up = true;
                int velocity = (int) (x * 0.002);
                this.y += velocity;
                Thread.sleep(5);
            } catch (Exception e) {
                this.y = (int) x;
            }
        }
    }

    public void setActuator(double touchPositionX, double touchPositionY) {
        setX((int) touchPositionX);
        setY((int) touchPositionY);
    }

    public void resetActuator() {
        setX(0);
        setY(0);
    }
}