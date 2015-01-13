package com.example.krabiysok.my_first_android_game.com.example.krabiysok.sprites.com.example.krabisok.bullets;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;

import com.example.krabiysok.my_first_android_game.GameScreen;
import com.example.krabiysok.my_first_android_game.com.example.krabiysok.sprites.GeneralAnimation;
import com.example.krabiysok.my_first_android_game.com.example.krabiysok.sprites.Player;

/**
 * Created by KrabiySok on 1/11/2015.
 */
public class Bullet extends GeneralAnimation {
    private int damage, speed;
    private double angle, moveAngle;
    private GeneralAnimation bulletBelongs;

    Bullet(int x, int y, int rows, int columns, Bitmap sprite, double spriteRatioHieght,
           int damage, int speed, double angle, GeneralAnimation bulletBelongs) {
        super(x, y, rows, columns, sprite, spriteRatioHieght);
        this.damage = damage;
        this.speed = speed;
        this.moveAngle = angle;
        this.bulletBelongs = bulletBelongs;
    }

    public int getDamage() {
        return damage;
    }

    public GeneralAnimation getBulletBelongs() {
        return bulletBelongs;
    }

    //No bullet animation
    // Rotate bullet from last raw.
    public boolean drawMove(Canvas canva) {
        resultOfAnimMove = true;
        if (moveAngle > Math.PI)
            angle = PI360 - moveAngle;
        else angle = moveAngle;
        if (angle > PI90)
            angle = Math.PI - angle;
        newYPosition = (int) (speed * Math.cos(angle));
        newXPosition = (int) (speed * Math.sin(angle));

        if (moveAngle > angle315 || moveAngle <= angle45) {
            position.y -= newYPosition;
            position.x += moveAngle > angle315 ? -newXPosition : newXPosition;
        }
        else if (moveAngle > angle45 && moveAngle <= angle135) {
            position.x += newXPosition;
            position.y += moveAngle > PI90 ? newYPosition : -newYPosition;
        }
        else if (moveAngle > angle135 && moveAngle <= angle225) {
            position.y += newYPosition;
            position.x += moveAngle > Math.PI ? -newXPosition : newXPosition;
        }
        else if (moveAngle > angle225 && moveAngle <= angle315) {
            position.x -= newXPosition;
            position.y += moveAngle > PI270 ? -newYPosition : newYPosition;
        }

        // Protect from going beyond game screen
        maxXAnimatPos = GameScreen.getWindowSize().x - this.spriteResolution.x;
        if (position.x < minXAnimatPos - 20) {
            resultOfAnimMove = false;
        }
        if (position.x > maxXAnimatPos + 20) {
            resultOfAnimMove = false;
        }
        if (position.y > GameScreen.getWindowSize().y + 20) {
            resultOfAnimMove = false;
        }
        if (position.y < minYAnimatPos) {
            resultOfAnimMove = false;
        }
        spriteResolution.x = (int) (spriteNormalResolution.x *
                ((float) position.y / GameScreen.getWindowSize().y));
        spriteResolution.y = (int) (spriteNormalResolution.y *
                ((float)position.y / GameScreen.getWindowSize().y));
        dst.set(position.x, position.y, position.x + (int) (spriteResolution.x ),
                position.y + (int) (spriteResolution.y ));

        matrix.setTranslate(dst.centerX(), dst.centerY());
        matrix.preRotate((float) (moveAngle * 57.296));
        bulletSprite = Bitmap.createBitmap(sprite, 0, bmpRezolution.y * (rows - 1),
                bmpRezolution.x, bmpRezolution.y, matrix, true);
        canva.drawBitmap(bulletSprite, src, dst, null);
        return resultOfAnimMove;
    }
}
