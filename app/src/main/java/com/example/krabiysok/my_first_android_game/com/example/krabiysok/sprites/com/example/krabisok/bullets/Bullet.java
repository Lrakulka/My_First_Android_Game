package com.example.krabiysok.my_first_android_game.com.example.krabiysok.sprites.com.example.krabisok.bullets;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;

import com.example.krabiysok.my_first_android_game.com.example.krabiysok.sprites.GeneralAnimation;

/**
 * Created by KrabiySok on 1/11/2015.
 */
public class Bullet extends GeneralAnimation {
    private int damage, speed;
    private double angle;

    Bullet(int x, int y, int rows, int columns, Bitmap sprite, Point spriteRezolution,
           int damage, int speed, double angle) {
        super(x, y, rows, columns, sprite, spriteRezolution);
        this.damage = damage;
        this.speed = speed;
        this.angle = angle;
    }

    public int getDamage() {
        return damage;
    }

    public boolean draw(Canvas canva) {
        return drawMove(canva, angle, speed);
    }
}
