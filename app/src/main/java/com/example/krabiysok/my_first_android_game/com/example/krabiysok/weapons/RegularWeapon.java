package com.example.krabiysok.my_first_android_game.com.example.krabiysok.weapons;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.krabiysok.my_first_android_game.GameProcess;
import com.example.krabiysok.my_first_android_game.MainActivity;
import com.example.krabiysok.my_first_android_game.R;
import com.example.krabiysok.my_first_android_game.com.example.krabiysok.sprites.GeneralAnimation;
import com.example.krabiysok.my_first_android_game.com.example.krabiysok.sprites.com.example.krabisok.bullets.Bullet;
import com.example.krabiysok.my_first_android_game.com.example.krabiysok.sprites.com.example.krabisok.bullets.RegularBullet;

/**
 * Created by KrabiySok on 1/11/2015.
 */
public class RegularWeapon extends Weapon {

    protected RegularWeapon(int reloudTime, int ammo, Bitmap weapon, Bullet bullet, int bulletReloud) {
        super(1000 / GameProcess.fps, 900, BitmapFactory.decodeResource(
                MainActivity.getContext().getResources(), R.drawable.RegularWepon, bulletReloud);
    }

    @Override
    public Bullet getBullet(int x, int y, GeneralAnimation bulletBelongs, double angle) {
        return new RegularBullet(x, y, bulletBelongs, angle);
    }
}
