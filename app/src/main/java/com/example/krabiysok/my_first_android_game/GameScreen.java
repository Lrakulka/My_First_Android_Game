package com.example.krabiysok.my_first_android_game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
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

    public GameScreen(Context context, int backGroundID) {
        super(context);
        sView = this;
        displayParameters = new Point();
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).
                getDefaultDisplay().getSize(displayParameters);
        isHeightMax = displayParameters.x > displayParameters.y;
        backGround = BitmapFactory.decodeResource(context.getResources(), backGroundID);
        sView.getHolder().addCallback(new SurfaceHolder.Callback() {
            public void surfaceDestroyed(SurfaceHolder holder) {
            }

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                if (!isHeightMax) {
                    int p = displayParameters.x;
                    displayParameters.x = displayParameters.y;
                    displayParameters.y = p;
                }
                sView.getLayoutParams().height = displayParameters.y;
                sView.getLayoutParams().width = displayParameters.x;
                draw(getCanvas());
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width,
                                       int height) {
            }
        });
    }

    public Canvas getCanvas() {
        Canvas canva = sView.getHolder().lockCanvas();
        if (canva != null) {
            canva.drawBitmap(backGround, null, new Rect(0,
                0, sView.getLayoutParams().width, sView.getLayoutParams().height), null);
        }
        return canva;
    }

    public void draw(Canvas canva) {
        if (canva != null)
            sView.getHolder().unlockCanvasAndPost(canva);
    }
}
