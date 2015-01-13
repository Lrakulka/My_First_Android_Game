package com.example.krabiysok.my_first_android_game.com.example.krabiysok.sprites.com.example.krabiysok.enemies;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.krabiysok.my_first_android_game.GameProcess;
import com.example.krabiysok.my_first_android_game.MainActivity;
import com.example.krabiysok.my_first_android_game.R;

/**
 * Created by KrabiySok on 1/13/2015.
 */
public class Major extends Enemie {
    public Major(int x, int y) {
        super(x, y,
                BitmapFactory.decodeResource(MainActivity.getContext().getResources(),
                        R.drawable.major), 100, (int) (300 / GameProcess.fps), 0, 0);
    }
}
