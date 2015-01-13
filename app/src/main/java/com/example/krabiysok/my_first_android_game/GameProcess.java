package com.example.krabiysok.my_first_android_game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import com.example.krabiysok.my_first_android_game.com.example.krabiysok.sprites.Player;
import com.example.krabiysok.my_first_android_game.com.example.krabiysok.sprites.com.example.MrCat;
import com.example.krabiysok.my_first_android_game.com.example.krabiysok.sprites.com.example.com.example.krabiysok.presents.Present;
import com.example.krabiysok.my_first_android_game.com.example.krabiysok.sprites.com.example.krabisok.bullets.Bullet;
import com.example.krabiysok.my_first_android_game.com.example.krabiysok.sprites.com.example.krabiysok.enemies.Enemie;
import com.example.krabiysok.my_first_android_game.com.example.krabiysok.sprites.com.example.krabiysok.enemies.Major;
import com.example.krabiysok.my_first_android_game.com.example.krabiysok.sprites.com.example.krabiysok.enemies.Sucub;
import com.example.krabiysok.my_first_android_game.com.example.krabiysok.weapons.SpecialWeapon;

import java.util.ArrayList;
import java.util.Random;


/**
 * Created by KrabiySok on 1/10/2015.
 */
public class GameProcess implements Runnable {
    public static final int fps = 100; // Speed of updates
    private static GameProcess gameProcess = new GameProcess();
    private GameScreen gameScreen;
    private Joystick joystick;
    private boolean stop, sleepGame;
    private Thread gameThread;
    private Paint paint;

    private GameProcess() {
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setTextSize(GameScreen.getWindowSize().y / 10);
    }

    public static GameProcess getGameProcess() {
        if (gameProcess.gameScreen != null)
            return gameProcess;
        return null;
    }

    public static GameProcess getGameProcess(GameScreen gameScreen, Joystick joystick) {
        if (gameProcess.gameScreen == null && gameScreen != null && joystick != null) {
            gameProcess.gameScreen = gameScreen;
            gameProcess.joystick = joystick;
            gameProcess.gameThread = new Thread(gameProcess, "Game Thread");
            gameProcess.gameThread.setDaemon(true);
        }
        return gameProcess;
    }

    @Override
    public void run() {
        Canvas canva;
        int currentFPS = fps;
        Player player = new Player(gameScreen.getWindowSize().x / 2,
                gameScreen.getWindowSize().y / 2, joystick);
        MyArrayList<Bullet> bullets = new MyArrayList<>();
        Bullet bullet;
        ArrayList<Enemie> enemies = new ArrayList<>();
        Enemie enimie;
        MyArrayList<Present> presents = new MyArrayList<>();
        Present present;
        MrCat mrCat = new MrCat();
        Random random = new Random();
        int randomAbsInt, x, y;
        while (!stop) {
            Log.d("LogApp", "game Go");
            if (player.getScore100() > currentFPS)
                currentFPS = fps - player.getScore100();
            randomAbsInt = (Math.abs(random.nextInt()) % fps);
            if ((enemies.size() < 7) && (enemies.isEmpty() ||
                    randomAbsInt < player.getScore100())) {
                x = randomAbsInt % 2 == 1 ? 0 : GameScreen.getWindowSize().x;
                y = GameScreen.GAME_SCREEN_HEIGHT_MIN + randomAbsInt %
                        (GameScreen.getWindowSize().y - GameScreen.GAME_SCREEN_HEIGHT_MIN);
                switch (randomAbsInt % 3) {
                    case 0: {
                        enemies.add(new Major(x, y));
                        break;
                    }
                    case 1: {
                        enemies.add(new Sucub(x, y));
                        break;
                    }
                    case 2: {
                        enemies.add(new Sucub(x, y));
                        break;
                    }
                }
            }
            canva = gameScreen.getCanvas();
            bullets.addAll(player.action(canva));
            presents.add(mrCat.action(canva, randomAbsInt));
            for(int i = 0; i < enemies.size(); ++i) {
                enimie = enemies.get(i);
                bullets.addAll(enimie.action(canva, player));
                if (!enimie.isAlive(bullets, player)) {
                    enemies.remove(i);
                    i--;
                }
            }
            for(int i = 0; i < presents.size(); ++i) {
                present = presents.get(i);
                if (present.takes(player)) {
                    presents.remove(i);
                    i--;
                }
                else present.draw(canva);
            }
            if (!player.isAlive(bullets)) {
                canva.drawText("Game Over", (float) (GameScreen.getWindowSize().x / 2 -
                        GameScreen.getWindowSize().x / 5),
                        GameScreen.getWindowSize().y / 2, paint);
                stopGame(); //---
            }
            for(int i = 0; i < bullets.size(); ++i) {
                bullet = bullets.get(i);
                if (!bullet.drawMove(canva)) {
                    bullets.remove(i);
                    i--;
                }
            }
            gameScreen.draw(canva);
            try {
                if (currentFPS < 20)
                    Thread.sleep(30); // I did so specifically
                else Thread.sleep(currentFPS);
                if (sleepGame)
                    synchronized (gameThread) {
                        gameThread.wait();
                    }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void startGame() {
        if (gameThread.getState() == Thread.State.NEW) {
            stop = false;
            gameThread.start();
        } else if (sleepGame) {
            sleepGame = false;
            synchronized (gameThread) {
                gameThread.notify();
            }
        }
    }

    public void sleepGame() {
        sleepGame = true;
    }

    public void stopGame() {
        stop = true;
    }
}
