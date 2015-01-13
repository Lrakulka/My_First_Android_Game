package com.example.krabiysok.my_first_android_game.com.example.krabiysok.sprites.com.example.krabisok.bullets;

import android.graphics.BitmapFactory;

import com.example.krabiysok.my_first_android_game.GameProcess;
import com.example.krabiysok.my_first_android_game.MainActivity;
import com.example.krabiysok.my_first_android_game.R;
import com.example.krabiysok.my_first_android_game.com.example.krabiysok.sprites.GeneralAnimation;

/**
 * Created by KrabiySok on 1/11/2015.
 */
public class SpecialBullet extends Bullet {
    public SpecialBullet(int x, int y, GeneralAnimation bulletBelongs, double angle) {
        super(x, y, 4, 4,
                BitmapFactory.decodeResource(MainActivity.getContext().getResources(),
                        R.drawable.special_bullet), 0.1, 30, 1900 / GameProcess.fps,
                angle, bulletBelongs);
    }
}
