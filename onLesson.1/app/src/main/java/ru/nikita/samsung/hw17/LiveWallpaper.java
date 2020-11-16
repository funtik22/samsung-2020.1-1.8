package ru.nikita.samsung.hw17;

import android.graphics.Canvas;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.service.wallpaper.WallpaperService;
import android.view.SurfaceHolder;

import java.util.LinkedHashSet;

// WallpaperService  - Он обслуживает обои
// абстрактный, так как является лишь заготовкой в которой есть какая-то неполноценная логика
// в нашем случае - не реализованный метод drawFrame
public abstract class LiveWallpaper extends WallpaperService {
    private final LinkedHashSet<Engine> engineStorage;
    public final LinkedHashSet<Engine> getEngineStorage() {
        return this.engineStorage;
    }
    public LiveWallpaper() {
        this.engineStorage = new LinkedHashSet<>();
    }

    protected abstract class LiveEngine extends Engine {
        private final RedrawHandler redrawHandler = new RedrawHandler();

        //вызывается когда создаются обои
        public void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);
            getEngineStorage().add(this);
        }

        //вызывается когда создаются обоид
        public void onSurfaceCreated(SurfaceHolder holder) {
            super.onSurfaceCreated(holder);
            this.redrawHandler.planRedraw();
        }

        //при изменения , например, размеров
        public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            super.onSurfaceChanged(holder, format, width, height);
            this.redrawHandler.planRedraw();
        }

        //Вызывается когда меняется режим выдимости обоев
        @Override
        public void onVisibilityChanged(boolean visible) {
            super.onVisibilityChanged(visible);
            this.redrawHandler.planRedraw();
        }

        //Вызывается при уничтожении обоев
        @Override
        public void onSurfaceDestroyed(SurfaceHolder holder) {
            super.onSurfaceDestroyed(holder);
            this.redrawHandler.omitRedraw();
        }

        //Вызывается при уничтожении обоев
        @Override
        public void onDestroy() {
            super.onDestroy();
            this.redrawHandler.omitRedraw();
            getEngineStorage().remove(this);
        }

        //перерисовка
        @Override
        public void onSurfaceRedrawNeeded(SurfaceHolder holder) {
            super.onSurfaceRedrawNeeded(holder);
            this.redrawHandler.omitRedraw();
            this.drawSynchronously(holder);
        }

        //при поворотах, размерах и т.п.
        @Override
        public void onOffsetsChanged(float xOffset, float yOffset,
                                     float xOffsetStep, float yOffsetStep, int xPixelOffset,
                                     int yPixelOffset) {
            this.redrawHandler.planRedraw();
        }

        //Метод который будет рисовать экран
        protected abstract void drawFrame(Canvas _canvas);

        //Выполнение заказа на отрисовку
        public final void drawSynchronously() {
            this.drawSynchronously(getSurfaceHolder());
        }

        //Выполнение заказа на отрисовку
        public final void drawSynchronously(SurfaceHolder holder) {
            if (isVisible()) { //а наше приложение видят?
                Canvas _canvas = null;
                try { //мы пытаемся выполнить код внутри try
                    _canvas = holder.lockCanvas(); // пытаемся взять холс
                    drawFrame(_canvas); //передаем холст для отрисовки экрана
                } finally { //какой бы результат выполнения кода в try мы не получили, всегда завершает так как описано в finally
                    if (_canvas != null) {
                        holder.unlockCanvasAndPost(_canvas);
                    }
                }
            }
        }

        //этот класс помогает нам сделать картинку живой.
        private final class RedrawHandler extends Handler {
            private final int redraw = 1;

            private Runnable mIteration = new Runnable() {
                public void run() {
                    planRedraw();
                }
            };

            public final void omitRedraw() {
                this.removeMessages(redraw);
            }

            public final void planRedraw() {
                this.omitRedraw();
                this.sendEmptyMessage(redraw);
            }
            public void handleMessage(Message msg) {
                if (msg.what == redraw) {
                    drawSynchronously();
                    postDelayed(mIteration, 1); //время ожидания перед запуском отрисовки экрана
                } else {
                    super.handleMessage(msg);
                }
            }
            public RedrawHandler() {
                super(Looper.getMainLooper());
            }
        }
    }
}

