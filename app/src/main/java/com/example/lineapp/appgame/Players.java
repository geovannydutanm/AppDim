package com.example.lineapp.appgame;

import android.graphics.Canvas;
import android.graphics.Paint;


public class Players {

    private final int judadorPositionX;
    private final int judadorPositionY;
    private final int outerCircleRadius;
    //private final int innerCircleRadius;
    //private final Paint innerCirclePaint;
    private final Paint outerCirclePaint;
    private int innerCirclex;
    private int innerCircley;
    private boolean isPressed = false;
    private double joystickCenterToTouchDistance;
    private double x;
    private double y;
    private int color;

    public Players(int x, int y, int outerCircleRadius, int color) {

        // Outer and inner circle make up the joystick
        judadorPositionX = x;
        judadorPositionY = y;
        this.x = x;
        this.y = y;

        // Radii of circles
        this.outerCircleRadius = outerCircleRadius;
        //this.innerCircleRadius = innerCircleRadius;

        // paint of circles
        outerCirclePaint = new Paint();
        outerCirclePaint.setColor(color);
        outerCirclePaint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    public void draw(Canvas canvas) {
        // Draw outer circle
        canvas.drawCircle(
                judadorPositionX,
                judadorPositionY,
                outerCircleRadius,
                outerCirclePaint
        );

    }

    /*public void update() {
        updateInnerCirclePosition();
    }

    private void updateInnerCirclePosition() {
        innerCirclex = (int) (x);
        innerCircley = (int) (y);
    }*/

    public void setActuator(double touchPositionX, double touchPositionY) {
        this.x = touchPositionX;
        this.y = touchPositionY;
    }


    public void setIsPressed(boolean isPressed) {
        this.isPressed = isPressed;
    }

    public int getJudadorPositionX() {
        return judadorPositionX;
    }

    public int getJudadorPositionY() {
        return judadorPositionY;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
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

    public int judadorPositionX() {
        return judadorPositionX;
    }

    public int judadorPositionY() {
        return judadorPositionY;
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

    public int getOuterCircleRadius() {
        return outerCircleRadius;
    }

    /*
    public int getInnerCircleRadius() {
        return innerCircleRadius;
    }

    public Paint getInnerCirclePaint() {
        return innerCirclePaint;
    }*/

    public Paint getOuterCirclePaint() {
        return outerCirclePaint;
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
        //this.innerCirclePaint.setColor(this.color);
    }
}

