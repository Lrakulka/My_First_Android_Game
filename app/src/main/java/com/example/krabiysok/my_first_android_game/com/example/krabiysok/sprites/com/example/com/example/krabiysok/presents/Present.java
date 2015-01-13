package com.example.krabiysok.my_first_android_game.com.example.krabiysok.sprites.com.example.com.example.krabiysok.presents;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;

import com.example.krabiysok.my_first_android_game.GameScreen;
import com.example.krabiysok.my_first_android_game.com.example.krabiysok.sprites.Player;

/**
 * Created by KrabiySok on 1/13/2015.
 */
abstract public class Present {
    private final static float RATIO = (float) (0.1);
    private Bitmap image;
    private Point size, position;
    private Rect dst;
    private int presentX0, presentX1, presentY0, presentY1, playerX0, playerX1, 
            playerY0, playerY1;
    Present(Bitmap image, int x, int y) {
        this.image = image;
        position = new Point(x, y);
        float p = GameScreen.getWindowSize().y * RATIO;
        size = new Point((int) ((image.getWidth() * p) / 
                image.getHeight()), (int) p);
        dst = new Rect(x, y, (int) (x + size.x * ((float) position.y /
                        GameScreen.getWindowSize().y)), (int) (y + size.y *
                ((float) position.y / GameScreen.getWindowSize().y)));
    }
    
    public boolean takes(Player player) {
        presentX0 = position.x;
        presentY0 = position.y;
        playerX0 = player.getPosition().x;
        playerY0 = player.getPosition().y;
        presentX1 = presentX0 + size.x;
        presentY1 = presentY0 + size.y;
        playerX1 = playerX0 + player.getSpriteResolution().x;
        playerY1 = playerY0 + player.getSpriteResolution().y;
        if ((playerX0 <= presentX0 && presentX0 <= playerX1 &&
                playerY0 <= presentY0 && presentY0 <= playerY1) ||
                (playerX0 <= presentX1 && presentX1 <= playerX1 &&
                        playerY0 <= presentY1 && presentY1 <= playerY1)) {
            presentForPlayer(player);
            return true;
        }
        return false;
    }

    abstract protected void presentForPlayer(Player player);
    
    public void draw(Canvas canva) {
        canva.drawBitmap(image, null, dst, null);
    }
}
