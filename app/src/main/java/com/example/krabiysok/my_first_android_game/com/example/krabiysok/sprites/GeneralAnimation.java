package com.example.krabiysok.my_first_android_game.com.example.krabiysok.sprites;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;

import com.example.krabiysok.my_first_android_game.GameScreen;

/**
 * Created by KrabiySok on 12/30/2014.
 */
public class GeneralAnimation {
    public static final double angle45 = Math.PI / 4, angle135 = Math.PI - angle45,
            angle225 = Math.PI + angle45, angle315 = Math.PI + 2 * angle45,
            PI360 = Math.PI * 2, PI90 = Math.PI / 2, PI270 = PI90 + Math.PI;
    protected int rows, columns, newXPosition, newYPosition, maxXAnimatPos, maxYAnimatPos,
            minXAnimatPos, minYAnimatPos;
    protected Bitmap sprite, bulletSprite;
    protected Rect src, dst;
    protected Point bmpRezolution, spriteResolution, position, spriteNormalResolution;
    private double angle;
    private byte forward, back, left, right;
    protected boolean resultOfAnimMove;
    protected Matrix matrix;

    protected GeneralAnimation(int x, int y, int rows, int columns, Bitmap sprite,
                               double spriteRatioHieght) {
        this.rows = rows;
        this.columns = columns;
        this.sprite = sprite;
        bmpRezolution = new Point(sprite.getWidth() / columns,
                sprite.getHeight() / rows);
        position = new Point(x, y);
        spriteNormalResolution = new Point();
        // Gets resolution of sprite on game screen
        this.spriteNormalResolution.y = (int) (GameScreen.getWindowSize().y * spriteRatioHieght);
        this.spriteNormalResolution.x =  bmpRezolution.x *
                this.spriteNormalResolution.y / bmpRezolution.y;
        src = new Rect(0, 0, bmpRezolution.x, bmpRezolution.y);
        dst = new Rect(position.x, position.y, position.x + this.spriteNormalResolution.x,
                position.y + this.spriteNormalResolution.y);
        matrix = new Matrix();
        forward = back = left = right = 0;
        maxXAnimatPos = GameScreen.getWindowSize().x - this.spriteNormalResolution.x;
        maxYAnimatPos = GameScreen.GAME_SCREEN_HEIGHT_MAX - this.spriteNormalResolution.y;
        minYAnimatPos = GameScreen.GAME_SCREEN_HEIGHT_MIN;
        minXAnimatPos = 0;
        this.spriteResolution = new Point(spriteNormalResolution);
    }

    public boolean drawMove(Canvas canva, Double moveAngle, int distance) {
        resultOfAnimMove = true;
        if (moveAngle != null) {
            if (moveAngle > Math.PI)
                angle = PI360 - moveAngle;
            else angle = moveAngle;
            if (angle > PI90)
                angle = Math.PI - angle;
            newYPosition = (int) (distance * Math.cos(angle));
            newXPosition = (int) (distance * Math.sin(angle));

            if (moveAngle > angle315 || moveAngle <= angle45) {
                position.y -= newYPosition;
                position.x += moveAngle > angle315 ? -newXPosition : newXPosition;
                moveBack();
            }
            else if (moveAngle > angle45 && moveAngle <= angle135) {
                    position.x += newXPosition;
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
        maxXAnimatPos = GameScreen.getWindowSize().x - this.spriteResolution.x;
        if (position.x < minXAnimatPos) {
            position.x = minXAnimatPos;
            resultOfAnimMove = false;
        }
        if (position.x > maxXAnimatPos) {
            position.x = maxXAnimatPos;
            resultOfAnimMove = false;
        }
        if (position.y > maxYAnimatPos) {
            position.y = maxYAnimatPos;
            resultOfAnimMove = false;
        }
        if (position.y < minYAnimatPos) {
            position.y = minYAnimatPos;
            resultOfAnimMove = false;
        }
        spriteResolution.x = (int) (spriteNormalResolution.x *
                ((float) position.y / GameScreen.getWindowSize().y));
        spriteResolution.y = (int) (spriteNormalResolution.y *
                ((float)position.y / GameScreen.getWindowSize().y));
        dst.set(position.x, position.y, position.x + spriteResolution.x,
                position.y + spriteResolution.y);
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
                (back + 1) * bmpRezolution.x, bmpRezolution.y * 4);
        back++;
    }

    public Point getPosition() {
        return new Point(position);
    }

    public Point getSpriteResolution() {
        return new Point(spriteResolution);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GeneralAnimation that = (GeneralAnimation) o;

        if (Double.compare(that.angle, angle) != 0) return false;
        if (back != that.back) return false;
        if (columns != that.columns) return false;
        if (forward != that.forward) return false;
        if (left != that.left) return false;
        if (resultOfAnimMove != that.resultOfAnimMove) return false;
        if (right != that.right) return false;
        if (rows != that.rows) return false;
        if (!bmpRezolution.equals(that.bmpRezolution)) return false;
        if (!bulletSprite.equals(that.bulletSprite)) return false;
        if (!dst.equals(that.dst)) return false;
        if (matrix != null ? !matrix.equals(that.matrix) : that.matrix != null) return false;
        if (!sprite.equals(that.sprite)) return false;
        if (spriteNormalResolution != null ? !spriteNormalResolution.equals(that.spriteNormalResolution) : that.spriteNormalResolution != null)
            return false;
        if (spriteResolution != null ? !spriteResolution.equals(that.spriteResolution) : that.spriteResolution != null)
            return false;
        if (!src.equals(that.src)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = rows;
        result = 31 * result + columns;
        result = 31 * result + sprite.hashCode();
        result = 31 * result + bulletSprite.hashCode();
        result = 31 * result + src.hashCode();
        result = 31 * result + dst.hashCode();
        result = 31 * result + bmpRezolution.hashCode();
        result = 31 * result + (spriteResolution != null ? spriteResolution.hashCode() : 0);
        result = 31 * result + (spriteNormalResolution != null ? spriteNormalResolution.hashCode() : 0);
        temp = Double.doubleToLongBits(angle);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (int) forward;
        result = 31 * result + (int) back;
        result = 31 * result + (int) left;
        result = 31 * result + (int) right;
        result = 31 * result + (resultOfAnimMove ? 1 : 0);
        result = 31 * result + (matrix != null ? matrix.hashCode() : 0);
        return result;
    }
}
