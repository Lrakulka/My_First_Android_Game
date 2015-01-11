package com.example.krabiysok.my_first_android_game.com.example.krabiysok.weapons;

import android.graphics.Bitmap;

import com.example.krabiysok.my_first_android_game.com.example.krabiysok.sprites.com.example.krabisok.bullets.Bullet;

/**
 * Created by KrabiySok on 1/11/2015.
 */
public abstract class Weapon {
    private Bitmap weapon;
    private int reloudTime, ammo;
    private Bullet bullet;
    public int bulletReloud;

    protected Weapon(int reloudTime, int ammo, Bitmap weapon,
                     Bullet bullet, int bulletReloud) {
        this.bulletReloud = bulletReloud;
        this.reloudTime = reloudTime;
        this.ammo = ammo;
        this.weapon = weapon;
        this.bullet = bullet;
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

    public abstract Bullet getBullet();

    public int getBulletReloud() {
        return bulletReloud;
    }
}
