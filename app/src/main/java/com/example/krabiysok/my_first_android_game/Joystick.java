package com.example.krabiysok.my_first_android_game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.example.krabiysok.my_first_android_game.com.example.krabiysok.sprites.GeneralAnimation;
import com.example.krabiysok.my_first_android_game.com.example.krabiysok.sprites.Player;

/**
 * Created by KrabiySok on 1/8/2015.
 */
public class Joystick  {
    private static final double PI2 = Math.PI * 2;
    private LeftStick leftStick;
    private Stick rightStick;
    private SurfaceView rightStickSurfaceV, leftStickSurfaceV, aimFieldSrfaceV;

    private class LeftStick extends Stick {
        private SurfaceView aimField;
        private Paint paintAim;
        private Player player;
        private Double newAimAngle;
        private double aimAngle;
        private float aimX, aimY, rAim, rAimDistance;
        private Canvas aimFieldCanva;

        LeftStick(SurfaceView stickSurfaceView, SurfaceView aimField) {
            super(stickSurfaceView);
            this.aimField = aimField;
            aimField.setZOrderOnTop(true);    // necessary
            aimField.getHolder().setFormat(PixelFormat.TRANSPARENT);
            paintAim = new Paint(Paint.ANTI_ALIAS_FLAG);
            paintAim.setColor(Color.RED);
            paintAim.setAlpha(150);
            paintAim.setStyle(Paint.Style.STROKE);
            paintAim.setStrokeWidth(GameScreen.getWindowSize().y / 100);
            rAimDistance = (int) (GameScreen.getWindowSize().y / 3);
            rAim = (int) (GameScreen.getWindowSize().y / 70);
            drawAim(GameScreen.getWindowSize().x / 2,
                    GameScreen.getWindowSize().y / 2 + rAimDistance);
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event != null && event.getAction() != MotionEvent.ACTION_UP &&
                    event.getAction() != MotionEvent.ACTION_CANCEL) {
                xDotStick = event.getX();
                yDotStick = event.getY();
            } else xDotStick = yDotStick = null;
            Integer stickInField = stickInField();
            Canvas canva = stickSurfaceView.getHolder().lockCanvas();
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
            stickSurfaceView.getHolder().unlockCanvasAndPost(canva);
            moveAim();
            return true;
        }

        private void moveAim() {
            if (player != null) {
                if((newAimAngle = this.getDirectionStick()) != null)
                    aimAngle = newAimAngle;
                aimX = (int) (player.getPosition().x + (Math.cos(aimAngle -
                        GeneralAnimation.PI90) * rAimDistance) +
                        player.getSpriteResolution().x / 2);
                aimY = (int) (player.getPosition().y + (Math.sin(aimAngle -
                        GeneralAnimation.PI90) *  rAimDistance) +
                        player.getSpriteResolution().y / 2);
                drawAim(aimX, aimY);
            }
        }
        private void drawAim(float aimX, float aimY) {
            aimFieldCanva = aimField.getHolder().lockCanvas();
            if (aimFieldCanva != null) {
                aimFieldCanva.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
               // aimFieldCanva.drawCircle(aimX, aimY, rAim, paintAim);
                aimFieldCanva.drawCircle(aimX, aimY, rAim, paintAim);
                aimField.getHolder().unlockCanvasAndPost(aimFieldCanva);
            }
        }

        public void setPlayer(Player player) {
            this.player = player;
        }
    }

    private class Stick implements View.OnTouchListener {
        protected Paint paint;
        protected Float xDotStick, yDotStick; // Small circke I cold dot
        // Joystick circle radius
        protected float rBig, rSmall, specialR, jumpR, maxDotDistance;
        protected float rBigD, jumpRD; // rBigD = rBig^2; jumpRD = jumpR^2
        protected float x, y;
        protected DashPathEffect dashPath;
        protected SurfaceView stickSurfaceView;

        Stick(final SurfaceView stickSurfaceView) {
            // Make surfaceView transparent
            this.stickSurfaceView = stickSurfaceView;
            stickSurfaceView.setZOrderOnTop(true);    // necessary
            stickSurfaceView.getHolder().setFormat(PixelFormat.TRANSPARENT);

            stickSurfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {

                @Override
                public void surfaceCreated(SurfaceHolder holder) {
                    if (GameScreen.getWindowSize().y > 0)
                        stickSurfaceView.getLayoutParams().height =
                            stickSurfaceView.getLayoutParams().width = (int)
                                   (GameScreen.getWindowSize().y / 2.8);
                    else stickSurfaceView.getLayoutParams().height =
                            stickSurfaceView.getLayoutParams().width = 200;
                    paint = new Paint(Paint.ANTI_ALIAS_FLAG);
                    paint.setColor(Color.BLUE);
                    paint.setAlpha(150);
                    paint.setStyle(Paint.Style.STROKE);
                    paint.setStrokeWidth(stickSurfaceView.getLayoutParams().height / 40);
                    dashPath = new DashPathEffect(new float[] { 5, 10, 5, 5 }, 1);
                    x = y = jumpR = stickSurfaceView.getLayoutParams().height / 2;
                    jumpR -= paint.getStrokeWidth() / 2;
                    rBig = (float) (jumpR / 2); // 1.8
                    rSmall = (float) (rBig / 2.5);
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
            Canvas canva = stickSurfaceView.getHolder().lockCanvas();
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
            stickSurfaceView.getHolder().unlockCanvasAndPost(canva);
            return true;
        }

        //-1 dot is not in circle 0 in circle 1 on circle
        public synchronized Integer stickInField() {
            if (xDotStick == null || yDotStick == null)
                return null;
            // Exception NullPointer ? Bad synchronization?
            float pos;
            try {
                pos = (x - xDotStick) * (x - xDotStick) + (y - yDotStick) *
                        (y - yDotStick);
            } catch (Exception e) {
                e.printStackTrace();
                return 0;
            }
            if (pos < rBigD)
                return 0;
            if (pos < jumpRD)
                return 1;
            return -1;
        }

        public Double getDirectionStick() {
            if (xDotStick == null || yDotStick == null)
                return null;
            double a, c, result;
            // Exception NullPointer ?
            try {
                a = Math.pow(Math.pow(x - xDotStick, 2) + Math.pow(y - yDotStick, 2), 0.5);
                c = Math.pow(Math.pow(x - xDotStick, 2) + Math.pow(y - rBig - yDotStick, 2), 0.5);
                result = xDotStick < x ? PI2 - Math.acos((a * a + rBig * rBig -
                        c * c) / (2 * a * rBig)) : Math.acos((a * a + rBig * rBig - c * c) /
                        (2 * a * rBig));
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
            return result;
        }

        public void cleanStickPosition() {
            xDotStick = yDotStick = null;
        }

    }

    Joystick(SurfaceView rightStickSurfaceV, SurfaceView leftStickSurfaceV,
             SurfaceView aimFieldSrfaceV) {
        this.leftStickSurfaceV = leftStickSurfaceV;
        this.rightStickSurfaceV = rightStickSurfaceV;
        this.aimFieldSrfaceV = aimFieldSrfaceV;
        if (leftStickSurfaceV != null) {
            leftStick = new LeftStick(leftStickSurfaceV, aimFieldSrfaceV);
            leftStickSurfaceV.setOnTouchListener(leftStick);
        }
        if (rightStickSurfaceV != null) {
            rightStick = new Stick(rightStickSurfaceV);
            rightStickSurfaceV.setOnTouchListener(rightStick);
        }
    }

    public Double getDirectionStick() {
        return rightStick.getDirectionStick();
    }

    public Integer stickInField() {
        return rightStick.stickInField();
    }

    public Double getAimAngle() {
        return leftStick.getDirectionStick();
    }

    public Integer isAimFire() {
        leftStick.moveAim();
        return leftStick.stickInField();
    }

    public static Double getAngleBetween(Point src, Point target) {
        if (target == null || src == null)
            return null;
        double a = Math.pow(Math.pow(src.x - target.x, 2) +
                Math.pow(src.y - target.y, 2), 0.5),
                c = Math.pow(Math.pow(src.x - target.x, 2) + Math.pow(src.y -
                        10 - target.y, 2), 0.5);
        Double result = target.x < src.x ? PI2 - Math.acos((a * a + 10 * 10 -
                c * c) / (2 * a * 10)) : Math.acos((a * a + 10 * 10 - c * c) /
                (2 * a * 10));
        return result;
    }

    public void setPlayer(Player player) {
        leftStick.setPlayer(player);
    }
}