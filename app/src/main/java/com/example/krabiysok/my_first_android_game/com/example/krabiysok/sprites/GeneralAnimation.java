package com.example.krabiysok.my_first_android_game.com.example.krabiysok.sprites;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;

import com.example.krabiysok.my_first_android_game.GameScreen;

/**
 * Created by KrabiySok on 12/30/2014.
 */
public class GeneralAnimation {
    private int rows;
    private int columns ;
    private Bitmap sprite;
    private Rect src, dst;
    private Point bmpRezolution, spriteRezolution, position;
    private byte forward, back, left, right;
    private static final double angle45 = Math.PI / 4, angle135 = Math.PI - angle45,
        angle225 = Math.PI + angle45, angle315 = Math.PI + 2 * angle45;

    GeneralAnimation(int x, int y, int rows, int columns, Bitmap sprite,
                     Point spriteRezolution) {
        this.rows = rows;
        this.columns = columns;
        this.sprite = sprite;
        bmpRezolution = new Point(sprite.getWidth() / columns,
                sprite.getHeight() / rows);
        position = new Point(x, y);
        if (spriteRezolution != null)
            this.spriteRezolution = spriteRezolution;
        else {
            this.spriteRezolution = new Point (bmpRezolution.x, bmpRezolution.y);
        }
        double height = (GameScreen.getWindowSize().x / 6) / bmpRezolution.x,
                weight = (GameScreen.getWindowSize().y / 6) / bmpRezolution.y;
        if (height <= 1 && weight <= 1)
            height = weight = height > weight ? height : weight;
        else height = weight = 1 / (height > weight ? height : weight);
        spriteRezolution.set((int) (spriteRezolution.y * height),
                (int) (spriteRezolution.x * weight));
        src = new Rect(0, 0, bmpRezolution.x, bmpRezolution.y);
        dst = new Rect(position.x, position.y, position.x + spriteRezolution.x,
                position.y + spriteRezolution.y);
        forward = back = left = right = 0;
    }

    public void draw(Canvas canva, Point position, Double moveAngle) {
        if (position != null) {
            this.position = position;
            dst.set(position.x, position.y, position.x + spriteRezolution.x,
                    position.y + spriteRezolution.y);
        }
        if (moveAngle == null || moveAngle > angle315 || moveAngle <= angle45)
            moveBack();
        else if (moveAngle > angle45 && moveAngle <= angle135)
                moveRight();
            else if (moveAngle > angle135 && moveAngle <= angle225)
                    moveForward();
                else if (moveAngle > angle225 && moveAngle <= angle315)
                        moveLeft();
        canva.drawBitmap(sprite, src, this.dst, null);
    }

    private void moveForward() {
        back = left = right = 0;
        if ( forward < columns) {
            forward = 0;
        }
        src.set(forward * bmpRezolution.x, 0,
                (forward + 1) * bmpRezolution.x, bmpRezolution.y);
        forward++;
    }

    private void moveLeft() {
        back = forward = right = 0;
        if ( left < columns) {
            left = 0;
        }
        src.set(left * bmpRezolution.x, bmpRezolution.y,
                (left + 1) * bmpRezolution.x, bmpRezolution.y * 2);
        left++;
    }

    private void moveRight() {
        back = forward = left = 0;
        if ( right < columns) {
            right = 0;
        }
        src.set(right * bmpRezolution.x, bmpRezolution.y * 2,
                (right + 1) * bmpRezolution.x, bmpRezolution.y * 3);
        right++;
    }

    private void moveBack() {
        right = forward = left = 0;
        if ( back < columns) {
            back = 0;
        }
        src.set(back * bmpRezolution.x, bmpRezolution.y * 3,
                (back + 1) * bmpRezolution.y, bmpRezolution.x * 4);
        back++;
    }
}
