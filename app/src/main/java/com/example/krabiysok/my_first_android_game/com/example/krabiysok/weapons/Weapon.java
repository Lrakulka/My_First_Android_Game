package com.example.krabiysok.my_first_android_game.com.example.krabiysok.weapons;

import android.graphics.Bitmap;

import com.example.krabiysok.my_first_android_game.com.example.krabiysok.sprites.GeneralAnimation;
import com.example.krabiysok.my_first_android_game.com.example.krabiysok.sprites.com.example.krabisok.bullets.Bullet;

/**
 * Created by KrabiySok on 1/11/2015.
 */
public abstract class Weapon {
    private int weaponImageID;
    private int reloudTime, ammo;
    public int bulletReloud;

    protected Weapon(int bulletReloud, int ammo, int weapon) {
        this.bulletReloud = bulletReloud;
        this.reloudTime = bulletReloud;
        this.ammo = ammo;
        this.weaponImageID = weapon;
    }

    public void startReloud() {
        ammo--;
        reloudTime = 0;
    }

    public void reloud() {
        if (reloudTime < bulletReloud) {
            reloudTime++;
        }
    }

    public int getWeapon() {
        return weaponImageID;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Weapon weapon = (Weapon) o;

        if (ammo != weapon.ammo) return false;
        if (bulletReloud != weapon.bulletReloud) return false;
        if (reloudTime != weapon.reloudTime) return false;
        if (weaponImageID != weapon.weaponImageID) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = weaponImageID;
        result = 31 * result + reloudTime;
        result = 31 * result + ammo;
        result = 31 * result + bulletReloud;
        return result;
    }
}
