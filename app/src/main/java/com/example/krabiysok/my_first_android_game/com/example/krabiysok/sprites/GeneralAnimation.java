package com.example.krabiysok.my_first_android_game.com.example.krabiysok.sprites;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;

/**
 * Created by KrabiySok on 12/30/2014.
 */
public class GeneralAnimation {
    private int rows;
    private int columns ;
    private Bitmap playerBMP;
    private Rect src, dst;
    private int widthBMP, heightBMP, width, height;
    private byte forward, back, left, right;

    GeneralAnimation(Activity activity, int rows, int columns,
              int widthAnimRezol, int heightAnimRezol, int sprite) {
       /* this.rows = rows;
        this.columns = columns;
        playerBMP = BitmapFactory.decodeResource(activity.getResources(), sprite);
        Point surfaceVSize = new
                Point(activity.findViewById(R.id.surfaceView).getHeight(),
                activity.findViewById(R.id.surfaceView).getWidth());
        widthBMP = playerBMP.getWidth() / columns;
        heightBMP = playerBMP.getHeight() / rows;
        width = widthBMP * (surfaceVSize.x / widthBMP / widthAnimRezol);
        height = heightBMP * (surfaceVSize.y / heightBMP / heightAnimRezol);
        src = new Rect();
        dst = new Rect(0, height - surfaceVSize.y, widthBMP, heightBMP);
        forward = back = left = right = 0;
  */  }

    public void draw(Canvas canva, Point position, int moveAngle) {
        if (position != null)
            dst.set(position.x, position.y, position.x + width, position.y + height);
        if (moveAngle > 315 || moveAngle <= 45)
            moveBack();
        else if (moveAngle > 45 && moveAngle <= 135)
                moveRight();
            else if (moveAngle > 135 && moveAngle <= 225)
                    moveForward();
                else if (moveAngle > 225 && moveAngle <= 315)
                        moveLeft();
        canva.drawBitmap(playerBMP, src, this.dst, null);
    }

    private void moveForward() {
        back = left = right = 0;
        if ( forward < columns) {
            forward = 0;
        }
        src.set(forward * width, 0, (forward + 1) * width, height);
        forward++;
    }

    private void moveLeft() {
        back = forward = right = 0;
        if ( left < columns) {
            left = 0;
        }
        src.set(left * width, height, (left + 1) * width, height * 2);
        left++;
    }

    private void moveRight() {
        back = forward = left = 0;
        if ( right < columns) {
            right = 0;
        }
        src.set(right * width, height * 2, (right + 1) * width, height * 3);
        right++;
    }

    private void moveBack() {
        right = forward = left = 0;
        if ( back < columns) {
            back = 0;
        }
        src.set(back * width, height * 3, (back + 1) * width, height * 4);
        back++;
    }

    public Rect getDst() {
        return dst;
    }
}
