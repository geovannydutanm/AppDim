package com.example.lineapp.appgameball;

import android.graphics.Canvas;


public class PlayerLine {
    private final LineObj lineObj;
    protected double velocityX, velocityY = 0.0;
    private int innerCirclex;
    private int innerCircley;
    private boolean isPressed = false;
    private double joystickCenterToTouchDistance;
    private double x;
    private double y;
    private int color;

    public PlayerLine(int x, int y, int color) {
        this.x = x;
        this.y = y;
        this.lineObj = new LineObj(x, y, color);
    }

    public void draw(Canvas canvas) {
        lineObj.draw(canvas);
    }

    public void draw(int startX, int startY, int stopX, int stopY, Canvas canvas) {
        lineObj.draw(startX, startY, stopX, stopY, canvas);
    }

    public void setActuator(double touchPositionX, double touchPositionY) {
        this.x = touchPositionX;
        this.y = touchPositionY;
    }

    public void setIsPressed(boolean isPressed) {
        this.isPressed = isPressed;
    }

    public double getX() {
        return x;
    }

    public void setX(double x, boolean left, int size) {
        velocityX = (float) x * 0.020;
        if (left) {
            if (((int) (this.x + velocityX)) < size) {
                this.x += velocityX;
            }
        } else {
            if (((int) (this.x - velocityX)) > 0) {
                this.x -= velocityX;
            } else {
                this.x = 0;
            }
        }
        this.x = (int) this.x;
        this.lineObj.setX((int) this.x);
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void resetActuator() {
        this.x = 0;
        this.y = 0;
    }

    public int getInnerCirclex() {
        return innerCirclex;
    }

    public void setInnerCirclex(int innerCirclex) {
        this.innerCirclex = innerCirclex;
    }

    public int getInnerCircley() {
        return innerCircley;
    }

    public void setInnerCircley(int innerCircley) {
        this.innerCircley = innerCircley;
    }

    public boolean isPressed() {
        return isPressed;
    }

    public void setPressed(boolean pressed) {
        isPressed = pressed;
    }

    public double getJoystickCenterToTouchDistance() {
        return joystickCenterToTouchDistance;
    }

    public void setJoystickCenterToTouchDistance(double joystickCenterToTouchDistance) {
        this.joystickCenterToTouchDistance = joystickCenterToTouchDistance;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
        this.lineObj.setColor(color);
    }

    public double getStartX() {
        return this.lineObj.getRect().left;
    }

    public double getStartY() {
        return this.lineObj.getRect().top;
    }

    public double getStopX() {
        return this.lineObj.getRect().right;
    }

    public double getStopY() {
        return this.lineObj.getRect().bottom;
    }
}

