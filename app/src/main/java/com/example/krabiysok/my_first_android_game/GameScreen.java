package com.example.krabiysok.my_first_android_game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by KrabiySok on 1/6/2015.
 */
public class GameScreen {
    public static int GAME_SCREEN_HEIGHT_MIN, GAME_SCREEN_HEIGHT_MAX;
    private SurfaceView windowSurface;
    private Context context;
    private Bitmap backGround;
    private static final Point windowSize = new Point();

    public GameScreen(final SurfaceView windowSurface, Context context) {
        this.context = context;
        this.windowSurface = windowSurface;
        // Just in case
        windowSize.x = 800;
        windowSize.y = 480;
        windowSurface.getHolder().addCallback(new SurfaceHolder.Callback() {
            public void surfaceDestroyed(SurfaceHolder holder) {
            }

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                setBackGround(generateBackGround(R.drawable.back_ground));
                windowSize.x = windowSurface.getWidth();
                windowSize.y = windowSurface.getHeight();
                GameScreen.GAME_SCREEN_HEIGHT_MAX = GameScreen.getWindowSize().y;
                GameScreen.GAME_SCREEN_HEIGHT_MIN = (int) (GameScreen.getWindowSize().y * 0.25);
                draw(getCanvas());
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width,
                                       int height) {
            }
        });
    }

    public SurfaceView getsView() {
        return windowSurface;
    }

    public Canvas getCanvas() {
        Canvas canva = windowSurface.getHolder().lockCanvas();
        if (canva != null) {
            if (backGround != null)
                canva.drawBitmap(backGround, null, new Rect(0, 0,
                        windowSurface.getWidth(), windowSurface.getHeight()), null);
        }
        return canva;
    }

    public Bitmap generateBackGround(int backGrounId) {
        return BitmapFactory.decodeResource(context.getResources(), backGrounId);
        //Need to write cicada method for generation unique background
    }

    public Bitmap getBackGround() {
        return backGround;
    }

    public void setBackGround(Bitmap backGround) {
        this.backGround = backGround;
    }

    public void draw(Canvas canva) {
        if (canva != null)
            windowSurface.getHolder().unlockCanvasAndPost(canva);
    }

    public static Point getWindowSize() {
        return windowSize;
    }
}
