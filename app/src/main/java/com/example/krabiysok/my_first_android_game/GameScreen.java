package com.example.krabiysok.my_first_android_game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

/**
 * Created by KrabiySok on 1/6/2015.
 */
public class GameScreen extends SurfaceView {

    private SurfaceView sView;
    private boolean isHeightMax;
    private Point displayParameters;
    private Bitmap backGround;
    private Joystick joystick;

    private class Joystick {
        private Paint paint;
        private float rBig, rSmall, specialR, jumpR; // Joystick circle radius
        private float rBigD, jumpRD; // rBigD = rBig^2; jumpRD = jumpR^2
        private float x, y;
        private Float xDot, yDot; // Small circke I cold dot
        private DashPathEffect dashPath;

        Joystick() {
            paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setColor(Color.BLUE);
            paint.setAlpha(100);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(sView.getLayoutParams().height / 50);
            dashPath = new DashPathEffect(new float[] { 5, 10, 5, 5 }, 1);
            rBig = sView.getLayoutParams().height / 8;
            rSmall = rBig / 3;
            x = 10 + rSmall / 2 + rBig;
            y = sView.getLayoutParams().height - x;
            specialR = (float)(rBig - paint.getStrokeWidth() / 1.5);
            jumpR = rBig + rSmall;
            rBigD = rBig * rBig;
            jumpRD = jumpR * jumpR;
        }

        // Use method dotInCircle before use this method
        public void drawJoystick(Canvas canva, Float xDot, Float yDot) {
            canva.drawCircle(x, y, rBig, paint);
            if (xDot == null || yDot == null)
                canva.drawCircle(x, y, rSmall, paint);
            else {
                if (xDot > x + rBig)
                    xDot = x + rBig;
                if (xDot < x - rBig)
                    xDot = x - rBig;
                if (yDot > y + rBig)
                    yDot = y + rBig;
                if (yDot < y - rBig)
                    yDot = y - rBig;
                canva.drawCircle(xDot, yDot, rSmall, paint);
            }
            this.xDot = xDot;
            this.yDot = yDot;
            paint.setPathEffect(dashPath);
            canva.drawCircle(x, y, specialR, paint);
            paint.setPathEffect(null);
        }

        //-1 dot is not in circle 1 in circle 2 on circle
        public int dotInCircle(float xDot, float yDot) {
            float pos = (x - xDot) * (x - xDot) + (y - yDot) * (y - yDot);
            if (pos < rBigD)
                return 1;
            if (pos < rBigD)
                return 2;
            return -1;
        }

        public double getDirection() {
            double a = Math.pow(Math.pow(x - xDot, 2) + Math.pow(y - yDot, 2), 0.5),
            c = Math.pow(Math.pow(x - xDot, 2) + Math.pow(y - rBig - yDot, 2), 0.5);
            return xDot < x ? Math.PI + Math.acos((a * a + rBig * rBig - c * c) /
                    (2 * a * rBig)) : Math.acos((a * a + rBig * rBig - c * c) /
                    (2 * a * rBig));
        }
    }

    public GameScreen(Context context) {
        super(context);
        sView = this;
        displayParameters = new Point();
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).
                getDefaultDisplay().getSize(displayParameters);
        isHeightMax = displayParameters.y > displayParameters.x;
        sView.getHolder().addCallback(new SurfaceHolder.Callback() {
            public void surfaceDestroyed(SurfaceHolder holder) {
            }

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                sView.getLayoutParams().height = displayParameters.y;
                sView.getLayoutParams().width = displayParameters.x;
                joystick = new Joystick();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width,
                                       int height) {
            }
        });
    }

    public SurfaceView getsView() {
        return sView;
    }

    public Point getDisplayParameters() {
        return displayParameters;
    }

    public Canvas getCanvas() {
        Canvas canva = sView.getHolder().lockCanvas();
        if (canva != null) {
            if (backGround != null)
                canva.drawBitmap(backGround, null, new Rect(0,
                    0, sView.getLayoutParams().width,
                        sView.getLayoutParams().height), null);
           joystick.drawJoystick(canva, null, null);
        }
        return canva;
    }

    public Bitmap getBackGround() {
        return backGround;
    }

    public void setBackGround(Bitmap backGround) {
        //Need to write cicada method for generation unique background
        this.backGround = backGround;
    }

    public void draw(Canvas canva) {
        if (canva != null)
            sView.getHolder().unlockCanvasAndPost(canva);
    }

    public boolean isHeightMax() {
        return isHeightMax;
    }
}
