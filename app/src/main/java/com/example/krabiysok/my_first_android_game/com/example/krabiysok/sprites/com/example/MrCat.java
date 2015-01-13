package com.example.krabiysok.my_first_android_game.com.example.krabiysok.sprites.com.example;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.example.krabiysok.my_first_android_game.GameProcess;
import com.example.krabiysok.my_first_android_game.GameScreen;
import com.example.krabiysok.my_first_android_game.MainActivity;
import com.example.krabiysok.my_first_android_game.R;
import com.example.krabiysok.my_first_android_game.com.example.krabiysok.sprites.GeneralAnimation;
import com.example.krabiysok.my_first_android_game.com.example.krabiysok.sprites.com.example.com.example.krabiysok.presents.AccelerationPresent;
import com.example.krabiysok.my_first_android_game.com.example.krabiysok.sprites.com.example.com.example.krabiysok.presents.HealthPresent;
import com.example.krabiysok.my_first_android_game.com.example.krabiysok.sprites.com.example.com.example.krabiysok.presents.Present;
import com.example.krabiysok.my_first_android_game.com.example.krabiysok.sprites.com.example.com.example.krabiysok.presents.WeaponPresent;
import com.example.krabiysok.my_first_android_game.com.example.krabiysok.sprites.com.example.krabisok.bullets.Bullet;

import java.util.ArrayList;

/**
 * Created by KrabiySok on 1/13/2015.
 */
public class MrCat extends GeneralAnimation {
    private static final int SPEED = 800 / GameProcess.fps,
            ACCELER_TIME = 0, ACCELERATION = 0;
    private static final double angle15 = angle45 / 3, angle150 = Math.PI -
            angle45 * 2, angle75 = angle45 * 3, angle195 = Math.PI + angle15,
            angle105 = PI90 + angle15, angle255 = PI270 - angle15;
    private double moveAngle;
    private boolean active;
    private int presentCount;

    public MrCat() {
        super(0, 0, 4, 3, BitmapFactory.decodeResource(MainActivity.getContext().getResources(),
                R.drawable.mr_cat), 0.12);
        moveAngle = PI270;
    }

    public Present action(Canvas canva, int randKey) {
        if (active) {
            if (position.x <= minXAnimatPos) {
                moveAngle = angle15 + ((angle150 * 1000) % randKey) / 1000;
                active = false;
            }
            if (position.x >= maxXAnimatPos) {
                moveAngle = angle195 + ((angle150 * 1000) % randKey) / 1000;
                active = false;
            }
            if (position.y >= maxYAnimatPos) {
                moveAngle = (randKey % 2) == 0 ? angle255 +
                        ((angle75 * 1000) % randKey) / 1000 :
                        ((angle105 * 1000) % randKey) / 1000;
                active = false;
            }
            if (position.y <= minYAnimatPos) {
                moveAngle = angle105 + ((angle150 * 1000) % randKey) / 1000;
                active = false;
            }
        }
        if (!active && (randKey % 50 < 2)) {
            active = true;
            presentCount = 0;
        }
        if (active) {
            drawMove(canva, moveAngle, SPEED);
            if (presentCount < 5 &&(randKey % 100) < 3) {
                presentCount++;
                switch (randKey % 3) {
                    case 0:
                        return new HealthPresent(position.x, position.y);
                    case 1:
                        return new WeaponPresent(position.x, position.y);
                    case 2:
                        return new AccelerationPresent(position.x, position.y);
                }
            }
        }
        return null;
    }
}
