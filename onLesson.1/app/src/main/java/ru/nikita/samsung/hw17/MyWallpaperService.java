package ru.nikita.samsung.hw17;


import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.service.wallpaper.WallpaperService;
import android.view.SurfaceHolder;
import java.util.LinkedHashSet;
import java.util.Random;


public final class MyWallpaperService extends LiveWallpaper {

    @Override
    public Engine onCreateEngine() {
        return new MyEngine();
    }

    public class MyEngine extends LiveEngine {
        final byte countOfCircle = 10;
        Circle2D[] arrayCircleList = new Circle2D[countOfCircle];

        public int getScreenWidth() {
            return Resources.getSystem().getDisplayMetrics().widthPixels;
        }
        public int getScreenHeight() {
            return Resources.getSystem().getDisplayMetrics().heightPixels;
        }

        public MyEngine(){
            Random _random = new Random();
            for (int i=0; i<arrayCircleList.length; i++){
                arrayCircleList[i] = new Circle2D();
                arrayCircleList[i].x = (float)Math.random()*getScreenWidth();
                arrayCircleList[i].y = (float)Math.random()*getScreenHeight();
                arrayCircleList[i].dX = (float)Math.random();
                arrayCircleList[i].dY = (float)Math.random();
                arrayCircleList[i].SetColor(_random.nextInt(Integer.MAX_VALUE)*-1); //TODO: Надо подумать над цветами.
            }
        }

        @Override
        protected void drawFrame(Canvas _canvas) {
            _canvas.save();
            _canvas.drawColor(Color.BLACK);
            for (Circle2D _circle : arrayCircleList) {
                _circle.Draw(_canvas);
                _circle.Move();
                if (_circle.y<=0 || _circle.y>=getScreenHeight()) _circle.dY*=-1;
                if (_circle.x<=0 || _circle.x>=getScreenWidth()) _circle.dX*=-1;
            }
            _canvas.restore();
        }
    }
}


