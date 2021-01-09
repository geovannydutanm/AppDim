package com.example.lineapp.appgame;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Joystick1 {

    private final int outerCircleCenterPositionX;
    private final int outerCircleCenterPositionY;
    private final int outerCircleRadius;
    private final int innerCircleRadius;
    private final Paint innerCirclePaint;
    private final Paint outerCirclePaint;
    private int innerCircleCenterPositionX;
    private int innerCircleCenterPositionY;
    private boolean isPressed = false;
    private double joystickCenterToTouchDistance;
    private double actuatorX;
    private double actuatorY;
    private int color;
    private int id;

    public Joystick1(int id, int centerPositionX, int centerPositionY, int outerCircleRadius, int innerCircleRadius, int color) {

        // Outer and inner circle make up the joystick
        outerCircleCenterPositionX = centerPositionX;
        outerCircleCenterPositionY = centerPositionY;
        innerCircleCenterPositionX = centerPositionX;
        innerCircleCenterPositionY = centerPositionY;
        this.color = color;

        // Radii of circles
        this.outerCircleRadius = outerCircleRadius;
        this.innerCircleRadius = innerCircleRadius;

        // paint of circles
        outerCirclePaint = new Paint();
        outerCirclePaint.setColor(Color.WHITE);
        outerCirclePaint.setStyle(Paint.Style.FILL_AND_STROKE);

        innerCirclePaint = new Paint();
        innerCirclePaint.setColor(color);
        innerCirclePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void draw(Canvas canvas) {
        // Draw outer circle
        canvas.drawCircle(
                outerCircleCenterPositionX,
                outerCircleCenterPositionY,
                outerCircleRadius,
                outerCirclePaint
        );

        // Draw inner circle
        canvas.drawCircle(
                innerCircleCenterPositionX,
                innerCircleCenterPositionY,
                innerCircleRadius,
                innerCirclePaint
        );
    }

    public void update() {
        updateInnerCirclePosition();
    }

    private void updateInnerCirclePosition() {
        innerCircleCenterPositionX = (int) (outerCircleCenterPositionX + actuatorX * outerCircleRadius);
        innerCircleCenterPositionY = (int) (outerCircleCenterPositionY + actuatorY * outerCircleRadius);
    }

    public void setActuator(double touchPositionX, double touchPositionY) {
        double deltaX = touchPositionX - outerCircleCenterPositionX;
        double deltaY = touchPositionY - outerCircleCenterPositionY;
        double deltaDistance = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));

        if (deltaDistance < outerCircleRadius) {
            actuatorX = deltaX / outerCircleRadius;
            actuatorY = deltaY / outerCircleRadius;
        } else {
            actuatorX = deltaX / deltaDistance;
            actuatorY = deltaY / deltaDistance;
        }
    }

    public boolean isPressed(double touchPositionX, double touchPositionY) {
        joystickCenterToTouchDistance = Math.sqrt(
                Math.pow(outerCircleCenterPositionX - touchPositionX, 2) +
                        Math.pow(outerCircleCenterPositionY - touchPositionY, 2)
        );
        return joystickCenterToTouchDistance < outerCircleRadius;
    }

    public boolean getIsPressed() {
        return isPressed;
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

    public int getOuterCircleCenterPositionX() {
        return outerCircleCenterPositionX;
    }

    public int getOuterCircleCenterPositionY() {
        return outerCircleCenterPositionY;
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

    public int getInnerCircleRadius() {
        return innerCircleRadius;
    }

    public Paint getInnerCirclePaint() {
        return innerCirclePaint;
    }

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
        this.innerCirclePaint.setColor(this.color);
    }
}
