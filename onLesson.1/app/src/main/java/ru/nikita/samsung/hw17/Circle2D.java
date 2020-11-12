package ru.nikita.samsung.hw17;

import android.graphics.Color;

public class Circle2D {
    float x;
    float y;

    float dX;
    float dY;

    float radius;
    int color;

    public Circle2D() {
        x=0;
        y=0;
        dX=0;
        dY=0;
        radius=10;
        color = Color.BLUE;
    }

    public Circle2D(float x, float y, float dx, float dy, float radius, int color){
        this.x=x;
        this.y=y;

        this.dX=x;
        this.dY=y;

        this.radius=10;
        this.color = Color.BLUE;
    }

    public void Draw(){
        //TODO: реализовать отрисовку круга
    }
}
