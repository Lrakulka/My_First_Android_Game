package com.example.krabiysok.my_first_android_game.com.example.krabiysok.weapons;

import android.graphics.BitmapFactory;

import com.example.krabiysok.my_first_android_game.GameProcess;
import com.example.krabiysok.my_first_android_game.MainActivity;
import com.example.krabiysok.my_first_android_game.R;
import com.example.krabiysok.my_first_android_game.com.example.krabiysok.sprites.GeneralAnimation;
import com.example.krabiysok.my_first_android_game.com.example.krabiysok.sprites.com.example.krabisok.bullets.Bullet;
import com.example.krabiysok.my_first_android_game.com.example.krabiysok.sprites.com.example.krabisok.bullets.EnemieBullet;

/**
 * Created by KrabiySok on 1/11/2015.
 */
public class MajorWeapon extends Weapon {
    public MajorWeapon() {
        super(500 / GameProcess.fps, 1000, BitmapFactory.decodeResource(
                MainActivity.getContext().getResources(), R.drawable.major_weapon));
    }

    @Override
    public Bullet getBullet(int x, int y, GeneralAnimation bulletBelongs, double angle) {
        return new EnemieBullet(x, y, bulletBelongs, angle);
    }
}
