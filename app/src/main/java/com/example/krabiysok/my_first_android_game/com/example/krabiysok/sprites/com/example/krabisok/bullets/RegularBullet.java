package com.example.krabiysok.my_first_android_game.com.example.krabiysok.sprites.com.example.krabisok.bullets;

import android.graphics.Bitmap;
import android.graphics.Point;

/**
 * Created by KrabiySok on 1/11/2015.
 */
public class RegularBullet extends Bullet {
    RegularBullet(int x, int y, int rows, int columns, Bitmap sprite, Point spriteRezolution, int damage, int speed, double angle) {
        super(x, y, rows, columns, sprite, spriteRezolution, damage, speed, angle);
    }
}
