package com.example.krabiysok.my_first_android_game.com.example.krabiysok.sprites.com.example.krabisok.bullets;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;

import com.example.krabiysok.my_first_android_game.com.example.krabiysok.sprites.GeneralAnimation;
import com.example.krabiysok.my_first_android_game.com.example.krabiysok.sprites.Player;

/**
 * Created by KrabiySok on 1/11/2015.
 */
public class Bullet extends GeneralAnimation {
    private int damage, speed;
    private double angle;
    private GeneralAnimation bulletBelongs;

    Bullet(int x, int y, int rows, int columns, Bitmap sprite, Point spriteRezolution,
           int damage, int speed, double angle, GeneralAnimation bulletBelongs) {
        super(x, y, rows, columns, sprite, spriteRezolution);
        this.damage = damage;
        this.speed = speed;
        this.angle = angle;
        this.bulletBelongs = bulletBelongs;
    }

    public int getDamage() {
        return damage;
    }

    public GeneralAnimation getBulletBelongs() {
        return bulletBelongs;
    }

    public boolean draw(Canvas canva) {
        return drawMove(canva, angle, speed);
    }
}
