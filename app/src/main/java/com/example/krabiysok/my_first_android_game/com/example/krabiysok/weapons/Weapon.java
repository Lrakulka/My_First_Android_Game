package com.example.krabiysok.my_first_android_game.com.example.krabiysok.weapons;

import android.graphics.Bitmap;

import com.example.krabiysok.my_first_android_game.com.example.krabiysok.sprites.GeneralAnimation;
import com.example.krabiysok.my_first_android_game.com.example.krabiysok.sprites.com.example.krabisok.bullets.Bullet;

/**
 * Created by KrabiySok on 1/11/2015.
 */
public abstract class Weapon {
    private Bitmap weapon;
    private int reloudTime, ammo;
    public int bulletReloud;

    protected Weapon(int bulletReloud, int ammo, Bitmap weapon) {
        this.bulletReloud = bulletReloud;
        this.reloudTime = bulletReloud;
        this.ammo = ammo;
        this.weapon = weapon;
    }

    public void startReloud() {
        reloudTime = 0;
    }

    public void reloud() {
        if (reloudTime < bulletReloud)
            reloudTime ++;
    }

    public Bitmap getWeapon() {
        return weapon;
    }

    public int getReloudTime() {
        return reloudTime;
    }

    public int getAmmo() {
        return ammo;
    }

    public abstract Bullet getBullet(int x, int y,
                                     GeneralAnimation bulletBelongs, double angle);

    public int getBulletReloud() {
        return bulletReloud;
    }
}