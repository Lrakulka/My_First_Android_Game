package com.example.krabiysok.my_first_android_game;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends Activity {
    public static final int HEALTH_CHANGED = 0, WEAPON_CHANGED = 1, AMMO_CHANGED = 2,
            SCORE_CHANGED = 3, SET_BUTTONS_VISIBLE = 4;
    private GameScreen gameScreen;
    private Joystick joystick;
    private GameProcess gameProcess;
    private static MainActivity context;
    private static Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            this.requestWindowFeature(Window.FEATURE_NO_TITLE);
            this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            setContentView(R.layout.main_layout);
            gameScreen = new GameScreen((SurfaceView) findViewById(R.id.gameScreen), this);
            joystick = new Joystick((SurfaceView) findViewById(R.id.joystickRight),
                    (SurfaceView) findViewById(R.id.joystickLeft),
                    (SurfaceView) findViewById(R.id.aimField));
            gameProcess = GameProcess.getGameProcess(gameScreen, joystick);
            context = this;
            handler = new Handler() {
                public void handleMessage(android.os.Message msg) {
                    switch (msg.what) {
                        case HEALTH_CHANGED:
                            ((ProgressBar) MainActivity.getContext().findViewById(R.id.health)).
                                    setProgress(msg.arg1);
                            ((ProgressBar) MainActivity.getContext().findViewById(R.id.energe)).
                                    setProgress(msg.arg2);
                            break;
                        case WEAPON_CHANGED:
                            ((ImageView) MainActivity.getContext().findViewById(R.id.weapons)).
                                    setImageResource(msg.arg1);
                            if (msg.arg2 > 0)
                                ((TextView) MainActivity.getContext().
                                        findViewById(R.id.weaponAmmo)).
                                        setText("Ammo " + String.valueOf(msg.arg2));
                            break;
                        case AMMO_CHANGED:
                            ((TextView) MainActivity.getContext().findViewById(R.id.weaponAmmo)).
                                    setText("Ammo " + String.valueOf(msg.arg1));
                            break;
                        case SCORE_CHANGED:
                            ((TextView) MainActivity.getContext().findViewById(R.id.gameScore)).
                                    setText("Score " + String.valueOf(msg.arg1));
                            break;
                        case SET_BUTTONS_VISIBLE:
                            ((Button) (MainActivity.getContext().
                                    findViewById(R.id.buttonClose))).setVisibility(View.VISIBLE);
                            ((Button) (MainActivity.getContext().
                                    findViewById(R.id.buttonRestart))).setVisibility(View.VISIBLE);
                        break;
                    }
                };
            };
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            Log.d("LogApp", "active");
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
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

    public static MainActivity getContext() {
        return context;
    }

    public static Handler getHandler() {
        return handler;
    }

    public void onCloseGame(View v) {
        finish();
    }

    public void onRestartGame(View v) {
        GameProcess.getGameProcess().restart();
        ((Button) (MainActivity.getContext().
                findViewById(R.id.buttonClose))).setVisibility(View.GONE);
        ((Button) (MainActivity.getContext().
                findViewById(R.id.buttonRestart))).setVisibility(View.GONE);
    }
}
