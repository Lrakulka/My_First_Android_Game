package com.example.krabiysok.my_first_android_game.com.example.krabiysok.weapons;

import android.graphics.Bitmap;

import com.example.krabiysok.my_first_android_game.com.example.krabiysok.sprites.GeneralAnimation;
import com.example.krabiysok.my_first_android_game.com.example.krabiysok.sprites.com.example.krabisok.bullets.Bullet;
import com.example.krabiysok.my_first_android_game.com.example.krabiysok.sprites.com.example.krabisok.bullets.RegularBullet;

/**
 * Created by KrabiySok on 1/11/2015.
 */
public class RegularWeapon extends Weapon {

    protected RegularWeapon(int reloudTime, int ammo, Bitmap weapon, Bullet bullet, int bulletReloud) {
        super(reloudTime, ammo, weapon, bullet, bulletReloud);
    }

    @Override
    public Bullet getBullet(int x, int y, GeneralAnimation bulletBelongs, double angle) {
        return new RegularBullet(x, y, bulletBelongs, angle);
    }
}
