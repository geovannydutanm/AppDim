package com.example.lineapp.appgame;

//import android.annotation.NonNull;

import android.graphics.Path;

import androidx.annotation.Nullable;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.SOURCE;

//import androidx.annotation.RecentlyNonNull;

public class DesignPatch extends Path {
    int designPatch_grosor;
    int designPatch_color;
    float designPatch_puntoX;
    float designPatch_puntoY;
    float designPatch_dimension;

    public DesignPatch() {
    }

    public DesignPatch(@Nullable Path src) {
        super(src);
    }

    public int get_designPatchGrosor() {
        return designPatch_grosor;
    }

    public void setdesignPatchGrosor(int grosor) {
        this.designPatch_grosor = grosor;
    }

    public int getdesignPatchColor() {
        return designPatch_color;
    }

    public void setdesignPatchColor(int color) {
        this.designPatch_color = color;
    }

    public float getdesignPatchPuntoX() {
        return designPatch_puntoX;
    }

    public void setdesignPatchPuntoX(float posActualX) {
        this.designPatch_puntoX = posActualX;
    }

    public float getDesignPatchPuntoY() {
        return designPatch_puntoY;
    }

    public void setdesignPatchPuntoY(float posActualY) {
        this.designPatch_puntoY = posActualY;
    }

    public float getdesignPatchDimension() {
        return designPatch_dimension;
    }
    public void setdesignPatchDimension(float dimension) {
        this.designPatch_dimension = dimension;
    }
    @Retention(SOURCE)
    @Target({METHOD, PARAMETER, FIELD})
    public @interface NonNull {
    }
    /*public final long mNativePath;

    private static native void nAddRoundRect(long nPath, float left, float top,
                                             float right, float bottom, float rx, float ry, int dir);
    @Override
    public void addRoundRect(float left, float top, float right, float bottom, float rx, float ry,
                             @android.annotation.NonNull Direction dir) {
        isSimplePath = false;
        nAddRoundRect(mNativePath, left, top, right, bottom, rx, ry, dir.nativeInt);
    }*/

}