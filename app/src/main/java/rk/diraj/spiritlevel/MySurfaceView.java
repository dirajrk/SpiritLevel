package rk.diraj.spiritlevel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MySurfaceView extends SurfaceView implements
        SurfaceHolder.Callback {

    private Paint paint1 = new Paint();
    private Paint paint2 = new Paint();
    private Point location;
    private Paint paint = new Paint();
    private Paint textPaint = new Paint();

    public MySurfaceView(Context context) {
        super(context);
        initialize();
    }

    public MySurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public MySurfaceView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize();
    }

    private void initialize() {
        getHolder().addCallback(this);
        setFocusable(true);
        paint1.setColor(Color.parseColor("#907FFFD4"));
        paint1.setStrokeWidth(1);
        paint1.setAntiAlias(true);
        paint1.setStrokeCap(Paint.Cap.SQUARE);
        paint1.setStyle(Paint.Style.FILL);

        paint2.setColor(Color.parseColor("#90F9A7B0"));
        paint2.setStrokeWidth(1);
        paint2.setAntiAlias(true);
        paint2.setStrokeCap(Paint.Cap.SQUARE);
        paint2.setStyle(Paint.Style.FILL);

        paint.setColor(Color.parseColor("#ffffff"));
        paint.setStrokeWidth(1);
        paint.setAntiAlias(true);
        paint.setStrokeCap(Paint.Cap.SQUARE);
        paint.setStyle(Paint.Style.FILL);

        textPaint.setColor(Color.parseColor("#000000"));
        textPaint.setTextSize(100);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setStrokeWidth(10);
        textPaint.setAntiAlias(true);
        textPaint.setStrokeCap(Paint.Cap.SQUARE);
        textPaint.setStyle(Paint.Style.FILL);

        location = new Point(0, 0);
    }

    public void update(int x, int y) {
        location.x = x;
        location.y = y;
    }

    @Override
    public void onDraw(Canvas canvas) {
        if (canvas == null) return;

        if (Math.abs(location.x) <= 2 && Math.abs(location.y) <= 2) {


            canvas.drawColor(Color.parseColor("#5EFB6E"));
            canvas.drawCircle(this.getWidth() / 2, this.getHeight() / 2, 400, paint);
            canvas.drawText("0°", getWidth() / 2, getHeight() / 2, textPaint);

        } else if (Math.abs(location.x) <= 500 && Math.abs(location.y) <= 500) {


            canvas.drawColor(Color.parseColor("#FEFCFF"));


            canvas.drawCircle(getWidth() / 2 + 2 * location.x, getHeight() / 2 + 2 * location.y, 400, paint1);
            canvas.drawCircle(getWidth() / 2 - 2 * location.x, getHeight() / 2 - 2 * location.y, 400, paint2);


            int x = Math.abs(location.x) / 10;
            int y = Math.abs(location.y) / 10;
            int displayAngle = x > y ? x : y;
            if (displayAngle == 0) displayAngle = 1;
            canvas.save();
            double angle = Math.toDegrees(Math.atan(-((double) location.x) / ((double) location.y)));
            if (location.y >= 0) {
                angle += 180;
            }
            canvas.rotate((int) angle, getWidth() / 2, getHeight() / 2);
            canvas.drawText(displayAngle + "°", getWidth() / 2, getHeight() / 2, textPaint);
            canvas.restore();

        } else if (Math.abs(location.y) >= 520 && Math.abs(location.x) <= 380) {

            canvas.drawColor(Color.parseColor("#6698FF"));
            canvas.save();
            String angle = Integer.toString(Math.abs(location.x) / 10) + "°";
            if (location.y < 0) {
                canvas.rotate(location.x / 10, getWidth() / 2, getHeight() / 2);
                if (location.x / 10 == 0) {
                    canvas.drawColor(Color.parseColor("#E55451"));
                }
                canvas.drawRect(-getWidth(), -getHeight(), 2 * getWidth(), getHeight() / 2, paint);
                canvas.drawText(angle, getWidth() / 2, getHeight() / 2, textPaint);
                canvas.restore();
            } else {
                canvas.rotate(-location.x / 10, getWidth() / 2, getHeight() / 2);
                if (location.x / 10 == 0) {
                    canvas.drawColor(Color.parseColor("#E55451"));
                }
                canvas.drawRect(-getWidth(), 2 * getHeight(), 2 * getWidth(), getHeight() / 2, paint);
                canvas.restore();
                canvas.save();
                canvas.rotate(-location.x / 10 + 180, getWidth() / 2, getHeight() / 2);
                canvas.drawText(angle, getWidth() / 2, getHeight() / 2, textPaint);
                canvas.restore();
            }

            canvas.drawLine(0, getHeight() / 2, 200, getHeight() / 2, textPaint);
            canvas.drawLine(getWidth(), getHeight() / 2, getWidth() - 200, getHeight() / 2, textPaint);

        } else if (Math.abs(location.x) >= 380 && Math.abs(location.y) < 520) {

            canvas.drawColor(Color.parseColor("#6698FF"));
            canvas.save();
            String angle = Integer.toString((int) Math.abs(location.y) / 10) + "°";
            if (location.x < 0) {
                canvas.rotate(-location.y / 10, getWidth() / 2, getHeight() / 2);
                if (location.y / 10 == 0) {
                    canvas.drawColor(Color.parseColor("#E55451"));
                }
                canvas.drawRect(-getWidth(), -getHeight(), getWidth() / 2, 2 * getHeight(), paint);
                canvas.restore();
                canvas.save();
                canvas.rotate(-location.y / 10 - 90, getWidth() / 2, getHeight() / 2);
                canvas.drawText(angle, getWidth() / 2, getHeight() / 2, textPaint);
                canvas.restore();
            } else {
                if (location.y / 10 == 0) {
                    canvas.drawColor(Color.parseColor("#E55451"));
                }
                canvas.rotate(location.y / 10, getWidth() / 2, getHeight() / 2);
                canvas.drawRect(2 * getWidth(), -getHeight(), getWidth() / 2, 2 * getHeight(), paint);
                canvas.restore();
                canvas.save();
                canvas.rotate(location.y / 10 + 90, getWidth() / 2, getHeight() / 2);
                canvas.drawText(angle, getWidth() / 2, getHeight() / 2, textPaint);
                canvas.restore();
            }

            canvas.drawLine(getWidth() / 2, getHeight(), getWidth() / 2, getHeight() - 200, textPaint);
            canvas.drawLine(getWidth() / 2, 0, getWidth() / 2, 200, textPaint);
        }
    }

    class DrawThread extends Thread {
        private final SurfaceHolder surfaceHolder;
        MySurfaceView mySurfaceView;
        private boolean run = false;

        public DrawThread(SurfaceHolder surfaceHolder,
                          MySurfaceView mySurfaceView) {
            this.surfaceHolder = surfaceHolder;
            this.mySurfaceView = mySurfaceView;
            run = false;
        }

        public void setRunning(boolean run) {
            this.run = run;
        }

        @SuppressLint("WrongCall")
        @Override
        public void run() {
            while (run) {
                Canvas canvas = surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    mySurfaceView.onDraw(canvas);
                }
                if (canvas != null) {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        DrawThread drawThread = new DrawThread(getHolder(), this);
        drawThread.setRunning(true);
        drawThread.start();
    }
}