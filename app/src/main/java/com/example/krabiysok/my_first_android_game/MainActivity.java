package com.example.krabiysok.my_first_android_game;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;


public class MainActivity extends Activity {
    GameScreen gameScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("LogApp", "create");
        if (gameScreen == null)
            gameScreen = new GameScreen(this);
        if (gameScreen.isHeightMax())
            // Call onCreate again
            this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(gameScreen);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        //super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            Log.d("LogApp", "active");
            gameScreen.setBackGround(new BitmapFactory().decodeResource(getResources(),
                    R.drawable.lighthouse));
            gameScreen.draw(gameScreen.getCanvas());
        } else {
            Log.d("LogApp", "disabled");
        }
    }
}
