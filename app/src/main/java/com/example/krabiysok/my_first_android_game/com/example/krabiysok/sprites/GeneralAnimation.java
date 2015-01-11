package com.example.krabiysok.my_first_android_game.com.example.krabiysok.sprites;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

import com.example.krabiysok.my_first_android_game.GameScreen;

/**
 * Created by KrabiySok on 12/30/2014.
 */
public class GeneralAnimation {
    private static final double angle45 = Math.PI / 4, angle135 = Math.PI - angle45,
            angle225 = Math.PI + angle45, angle315 = Math.PI + 2 * angle45,
            PI360 = Math.PI * 2, PI90 = Math.PI / 2, PI270 = PI90 + Math.PI;
    private int rows, columns, newXPosition, newYPosition, maxXAnimation, maxYAnimation;
    private Bitmap sprite;
    private Rect src, dst;
    private Point bmpRezolution, spriteRezolution, position;
    private double angle;
    private byte forward, back, left, right;
    private boolean resultOfAnimMove;

    protected GeneralAnimation(int x, int y, int rows, int columns, Bitmap sprite,
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
        /*double weight = ((double) (GameScreen.getWindowSize().x) / 30) / bmpRezolution.x,
                height = ((double) (GameScreen.getWindowSize().y) / 30) / bmpRezolution.y;
        if (height <= 1 && weight <= 1)
            height = weight = height > weight ? height : weight;
        else height = weight = 1 / (height > weight ? height : weight);
        this.spriteRezolution.y *= height;
        this.spriteRezolution.x *= weight;*/
        src = new Rect(0, 0, bmpRezolution.x, bmpRezolution.y);
        dst = new Rect(position.x, position.y, position.x + this.spriteRezolution.x,
                position.y + this.spriteRezolution.y);
        forward = back = left = right = 0;
        maxXAnimation = GameScreen.getWindowSize().x - this.spriteRezolution.x;
        maxYAnimation = GameScreen.getWindowSize().y - this.spriteRezolution.y;
    }

    public boolean drawMove(Canvas canva, Double moveAngle, int distance) {
        resultOfAnimMove = true;
        if (moveAngle != null) {
            if (moveAngle > Math.PI)
                angle = PI360 - moveAngle;
            else angle = moveAngle;
            if (angle > PI90)
                angle = Math.PI - angle;
            newXPosition = (int) (distance * Math.cos(angle));
            newYPosition = (int) (distance * Math.sin(angle));

            if (moveAngle > angle315 || moveAngle <= angle45) {
                position.y -= newYPosition;
                position.x += moveAngle > angle315 ? -newXPosition : newXPosition;
                moveBack();
            }
            else if (moveAngle > angle45 && moveAngle <= angle135) {
                    position.x += newYPosition;
                    position.y += moveAngle > PI90 ? newYPosition : -newYPosition;
                    moveRight();
                }
                else if (moveAngle > angle135 && moveAngle <= angle225) {
                        position.y += newYPosition;
                        position.x += moveAngle > Math.PI ? -newXPosition : newXPosition;
                        moveForward();
                    }
                    else if (moveAngle > angle225 && moveAngle <= angle315) {
                        position.x -= newXPosition;
                        position.y += moveAngle > PI270 ? -newYPosition : newYPosition;
                        moveLeft();
                    }
        } else {
            forward = 0;
            moveForward();
        }
        // Protect from going beyond game screen
        if (position.x < 0) {
            position.x = 0;
            resultOfAnimMove = false;
        }
        if (position.x > maxXAnimation) {
            position.x = maxXAnimation;
            resultOfAnimMove = false;
        }
        if (position.y > maxYAnimation) {
            position.y = maxYAnimation;
            resultOfAnimMove = false;
        }
        if (position.y < 0) {
            position.y = 0;
            resultOfAnimMove = false;
        }
        dst.set(position.x, position.y, position.x + spriteRezolution.x,
                position.y + spriteRezolution.y);
        canva.drawBitmap(sprite, src, this.dst, null);
        return resultOfAnimMove;
    }

    private void moveForward() {
        back = left = right = 0;
        if ( forward >= columns) {
            forward = 0;
        }
        src.set(forward * bmpRezolution.x, 0,
                (forward + 1) * bmpRezolution.x, bmpRezolution.y);
        forward++;
    }

    private void moveLeft() {
        back = forward = right = 0;
        if ( left >= columns) {
            left = 0;
        }
        src.set(left * bmpRezolution.x, bmpRezolution.y,
                (left + 1) * bmpRezolution.x, bmpRezolution.y * 2);
        left++;
    }

    private void moveRight() {
        back = forward = left = 0;
        if ( right >= columns) {
            right = 0;
        }
        src.set(right * bmpRezolution.x, bmpRezolution.y * 2,
                (right + 1) * bmpRezolution.x, bmpRezolution.y * 3);
        right++;
    }

    private void moveBack() {
        right = forward = left = 0;
        if ( back >= columns) {
            back = 0;
        }
        src.set(back * bmpRezolution.x, bmpRezolution.y * 3,
                (back + 1) * bmpRezolution.y, bmpRezolution.x * 4);
        back++;
    }

    public Point getPosition() {
        return new Point(position);
    }

    public Point getSpriteRezolution() {
        return new Point(spriteRezolution);
    }
}
