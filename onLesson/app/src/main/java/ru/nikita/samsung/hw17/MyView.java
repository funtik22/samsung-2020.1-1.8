package ru.nikita.samsung.hw17;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class MyView extends View {

    Paint paint = new Paint();
    Circle2D[] arrayCircleList;

    float _remeber[] = new float[2];
    float _delta[] = new float[2];

    public MyView(Context context, int countOfCircle){
        super(context);
        arrayCircleList = new Circle2D[countOfCircle];

        for (int i=0; i<arrayCircleList.length; i++){
            arrayCircleList[i] = new Circle2D();
            Log.d("Debug", Integer.toString(getWidth()));
            arrayCircleList[i].x = (float)Math.random()*500;/* getWidth() */;
            arrayCircleList[i].dX = (float)Math.random();/* getWidth() */;
            arrayCircleList[i].dY = (float)Math.random();/* getWidth() */;
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i=0; i<arrayCircleList.length; i++){
            Circle2D _circle = arrayCircleList[i];
            paint.setColor(_circle.color);
            canvas.drawCircle(_circle.x, _circle.y, _circle.radius, paint);
            _circle.y += _circle.dY * _delta[1];
            _circle.x += _circle.dX * _delta[0];
        }
/*
        x = canvas.getWidth()/2;
        canvas.drawCircle(x, y, 20, paint);
        //x += 0.5f;
        y += 0.5f;
 */
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                _remeber[0] = event.getX();
                _remeber[1] = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                _delta[0] = event.getX()-_remeber[0];
                _delta[1] = event.getY()-_remeber[1];
                break;
        }
        return true;
    }
}
