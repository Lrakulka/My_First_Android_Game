package com.example.krabiysok.my_first_android_game.com.example.krabiysok.sprites.com.example.krabisok.bullets;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;

import com.example.krabiysok.my_first_android_game.GameProcess;
import com.example.krabiysok.my_first_android_game.MainActivity;
import com.example.krabiysok.my_first_android_game.R;
import com.example.krabiysok.my_first_android_game.com.example.krabiysok.sprites.GeneralAnimation;

/**
 * Created by KrabiySok on 1/11/2015.
 */
public class EnemieBullet extends Bullet {
    EnemieBullet(int x, int y, GeneralAnimation bulletBelongs, double angle) {
        super(x, y, 4, 4,
                BitmapFactory.decodeResource(MainActivity.getContext().getResources(),
                        R.drawable.EnemieBullet), null, 10, 30000 / GameProcess.fps,
                angle, bulletBelongs);
    }
}