package ru.nikita.samsung.hw17;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Circle2D {

    private final Paint paint = new Paint();

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

    public void Draw(Canvas canvas){
        canvas.drawCircle(x,y, radius, paint);
    }

    public void SetColor(int _color){
        color = _color;
        paint.setColor(_color);
    }

    public void Move(){
        x+=dX;
        y+=dY;
    }
}
