package com.example.krabiysok.my_first_android_game;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;


public class MainActivity extends Activity {
    GameScreen gameScreen;
    Joystick joystick;
    GameProcess gameProcess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
            this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        else {
            this.requestWindowFeature(Window.FEATURE_NO_TITLE);
            this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            setContentView(R.layout.main_layout);
            gameScreen = new GameScreen((SurfaceView) findViewById(R.id.gameScreen), this);
            joystick = new Joystick((SurfaceView) findViewById(R.id.joystick),
                    (SurfaceView) findViewById(R.id.gameScreen));
            gameProcess = GameProcess.getGameProcess(gameScreen, joystick);
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            Log.d("LogApp", "active");
            gameProcess.startGame();
        } else {
            Log.d("LogApp", "disabled");
            gameProcess.sleepGame();
        }
    }

    // This method doesn't call after finishing application!!!!(need to repair)
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("LogApp", "Destroy");
        if (gameProcess != null)
            gameProcess.stopGame();
    }
}
