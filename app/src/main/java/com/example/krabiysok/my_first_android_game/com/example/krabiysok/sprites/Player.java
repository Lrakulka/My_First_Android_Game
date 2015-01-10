package com.example.krabiysok.my_first_android_game.com.example.krabiysok.sprites;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;

import com.example.krabiysok.my_first_android_game.Joystick;
import com.example.krabiysok.my_first_android_game.MainActivity;
import com.example.krabiysok.my_first_android_game.R;

import java.util.ArrayList;

/**
 * Created by KrabiySok on 1/10/2015.
 */
public class Player extends GeneralAnimation {
    private static final int HEALTH = 100, SPEED = 20, ACCELER_TIME = 30,
            BULLET_RELOUD = 3, ACCELERATION = 2;
    private Joystick joystick;
    private int health = HEALTH, speed = SPEED, accelerTime = ACCELER_TIME,
            bulletReaold = BULLET_RELOUD, getAcceleration = ACCELERATION;

    Player(int x, int y, Joystick joystick) {
        super(x, y, 4, 3,
                BitmapFactory.decodeResource(MainActivity.getContext().getResources(),
                R.drawable.boy), null);
        this.joystick = joystick;
    }

    public ArrayList<Bullet> action(Canvas canva) {
        Double playerAngle;
        playerAngle = joystick.getStick().getDirectionStick();

        draw(canva, null, playerAngle);
    }
}
