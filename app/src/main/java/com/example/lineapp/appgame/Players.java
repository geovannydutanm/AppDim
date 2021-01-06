package com.example.lineapp.appgame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

import com.example.lineapp.R;


public class Players {

    private final int judadorPositionX;
    private final int judadorPositionY;
    private final int outerCircleRadius;
    //private final int innerCircleRadius;
    //private final Paint innerCirclePaint;
    private final Paint outerCirclePaint;
    private int innerCircleCenterPositionX;
    private int innerCircleCenterPositionY;
    private boolean isPressed = false;
    private double joystickCenterToTouchDistance;
    private double actuatorX;
    private double actuatorY;
    private int color;

    public Players(int centerPositionX, int centerPositionY, int outerCircleRadius, int color) {

        // Outer and inner circle make up the joystick
        judadorPositionX = centerPositionX;
        judadorPositionY = centerPositionY;
        actuatorX= centerPositionX;
        actuatorY= centerPositionY;

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
        innerCircleCenterPositionX = (int) (actuatorX);
        innerCircleCenterPositionY = (int) (actuatorY);
    }*/

    public void setActuator(double touchPositionX, double touchPositionY) {
        this.actuatorX = touchPositionX;
        this.actuatorY = touchPositionY;
    }



    public void setIsPressed(boolean isPressed) {
        this.isPressed = isPressed;
    }

    public double getActuatorX() {
        return actuatorX;
    }

    public void setActuatorX(double actuatorX) {
        this.actuatorX = actuatorX;
    }

    public double getActuatorY() {
        return actuatorY;
    }

    public void setActuatorY(double actuatorY) {
        this.actuatorY = actuatorY;
    }

    public void resetActuator() {
        actuatorX = 0;
        actuatorY = 0;
    }

    public int judadorPositionX() {
        return judadorPositionX;
    }

    public int judadorPositionY() {
        return judadorPositionY;
    }

    public int getInnerCircleCenterPositionX() {
        return innerCircleCenterPositionX;
    }

    public void setInnerCircleCenterPositionX(int innerCircleCenterPositionX) {
        this.innerCircleCenterPositionX = innerCircleCenterPositionX;
    }

    public int getInnerCircleCenterPositionY() {
        return innerCircleCenterPositionY;
    }

    public void setInnerCircleCenterPositionY(int innerCircleCenterPositionY) {
        this.innerCircleCenterPositionY = innerCircleCenterPositionY;
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

