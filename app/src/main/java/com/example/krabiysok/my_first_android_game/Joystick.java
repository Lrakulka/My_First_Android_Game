package com.example.krabiysok.my_first_android_game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

/**
 * Created by KrabiySok on 1/8/2015.
 */
public class Joystick  {
    private Aim aim;
    private Stick stick;
    private SurfaceView stickSurfaceV, aimSurfaceV;

    private class Aim implements View.OnTouchListener {
        private volatile Float xAim, yAim;

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event != null && event.getAction() != MotionEvent.ACTION_UP &&
                    event.getAction() != MotionEvent.ACTION_CANCEL) {
                xAim = event.getX();
                yAim = event.getY();
            } else xAim = yAim = null;
            return true;
        }

        public Float getxAim() {
            return xAim;
        }

        public Float getyAim() {
            return yAim;
        }
    }

    private class Stick implements View.OnTouchListener {
        private Paint paint;
        private Float xDotStick, yDotStick; // Small circke I cold dot
        // Joystick circle radius
        private float rBig, rSmall, specialR, jumpR, maxDotDistance;
        private float rBigD, jumpRD; // rBigD = rBig^2; jumpRD = jumpR^2
        private float x, y;
        private static final double PI2 = Math.PI * 2;
        private DashPathEffect dashPath;

        Stick() {
            // Make surfaceView transparent
            stickSurfaceV.setZOrderOnTop(true);    // necessary
            stickSurfaceV.getHolder().setFormat(PixelFormat.TRANSPARENT);

            stickSurfaceV.getHolder().addCallback(new SurfaceHolder.Callback() {

                @Override
                public void surfaceCreated(SurfaceHolder holder) {
                    if (aimSurfaceV.getHeight() > 0)
                        stickSurfaceV.getLayoutParams().height =
                            stickSurfaceV.getLayoutParams().width = (int)
                                   (aimSurfaceV.getHeight() / 3.5);
                    else stickSurfaceV.getLayoutParams().height =
                            stickSurfaceV.getLayoutParams().width = 200;
                    paint = new Paint(Paint.ANTI_ALIAS_FLAG);
                    paint.setColor(Color.BLUE);
                    paint.setAlpha(150);
                    paint.setStyle(Paint.Style.STROKE);
                    paint.setStrokeWidth(stickSurfaceV.getLayoutParams().height / 20);
                    dashPath = new DashPathEffect(new float[] { 5, 10, 5, 5 }, 1);
                    x = y = jumpR = stickSurfaceV.getLayoutParams().height / 2;
                    jumpR -= paint.getStrokeWidth() / 2;
                    rBig = (float) (jumpR / 1.7);
                    rSmall = rBig / 2;
                    specialR = (float)(rBig - paint.getStrokeWidth() / 1.5);
                    maxDotDistance = (float) (rBig * 0.8);
                    rBigD = rBig * rBig;
                    jumpRD = jumpR * jumpR;
                    onTouch(null, null);
                }

                @Override
                public void surfaceChanged(SurfaceHolder holder, int format,
                                           int width, int height) {

                }

                @Override
                public void surfaceDestroyed(SurfaceHolder holder) {

                }
            });

        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event != null && event.getAction() != MotionEvent.ACTION_UP &&
                    event.getAction() != MotionEvent.ACTION_CANCEL) {
                xDotStick = event.getX();
                yDotStick = event.getY();
            } else xDotStick = yDotStick = null;
            Integer stickInField = stickInField();
            Canvas canva = stickSurfaceV.getHolder().lockCanvas();
            // Clean canvas
            canva.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            canva.drawCircle(x, y, rBig, paint);
            canva.drawCircle(x, y, jumpR, paint);
            if (stickInField == null || stickInField == -1) {
                canva.drawCircle(x, y, rSmall, paint);
                xDotStick = yDotStick = null;
            }
            else {
                Float xDot = xDotStick, yDot = yDotStick;
                if (stickInField == 1) {
                    float dist = (float) (Math.pow(Math.pow(xDotStick - x, 2) +
                            Math.pow(yDotStick - y, 2), 0.5));
                    xDot = x + maxDotDistance * (xDotStick - x ) / dist;
                    yDot = y + maxDotDistance * (yDotStick - y ) / dist;
                }
                canva.drawCircle(xDot, yDot, rSmall, paint);

            }
            paint.setPathEffect(dashPath);
            canva.drawCircle(x, y, specialR, paint);
            paint.setPathEffect(null);
            stickSurfaceV.getHolder().unlockCanvasAndPost(canva);
            return true;
        }

        //-1 dot is not in circle 0 in circle 1 on circle
        public synchronized Integer stickInField() {
            if (xDotStick == null || yDotStick == null)
                return null;
            float pos = (x - xDotStick) * (x - xDotStick) + (y - yDotStick) *
                    (y - yDotStick);
            if (pos < rBigD)
                return 0;
            if (pos < jumpRD)
                return 1;
            return -1;
        }

        public Double getDirectionStick() {
            if (xDotStick == null || yDotStick == null)
                return null;
            double a = Math.pow(Math.pow(x - xDotStick, 2) + Math.pow(y - yDotStick, 2), 0.5),
                    c = Math.pow(Math.pow(x - xDotStick, 2) + Math.pow(y - rBig - yDotStick, 2), 0.5);
            Double result = xDotStick < x ? PI2 - Math.acos((a * a + rBig * rBig -
                    c * c) / (2 * a * rBig)) : Math.acos((a * a + rBig * rBig - c * c) /
                    (2 * a * rBig));
            return result;
        }

        public void cleanStickPosition() {
            xDotStick = yDotStick = null;
        }

    }

    Joystick(SurfaceView stickSurfaceV, SurfaceView aimSurfaceV) {
        this.aimSurfaceV = aimSurfaceV;
        this.stickSurfaceV = stickSurfaceV;
        aim = new Aim();
        stick = new Stick();
        aimSurfaceV.setOnTouchListener(aim);
        stickSurfaceV.setOnTouchListener(stick);
    }

    public Stick getStick() {
        return stick;
    }

    public Aim getAim() {
        return aim;
    }
}