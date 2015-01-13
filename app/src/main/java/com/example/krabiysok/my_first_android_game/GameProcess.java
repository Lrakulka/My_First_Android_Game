package com.example.krabiysok.my_first_android_game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import com.example.krabiysok.my_first_android_game.com.example.krabiysok.sprites.Player;
import com.example.krabiysok.my_first_android_game.com.example.krabiysok.sprites.com.example.krabisok.bullets.Bullet;
import com.example.krabiysok.my_first_android_game.com.example.krabiysok.sprites.com.example.krabiysok.enemies.Enemie;
import com.example.krabiysok.my_first_android_game.com.example.krabiysok.sprites.com.example.krabiysok.enemies.Major;
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
        Player player = new Player(gameScreen.getWindowSize().x / 2,
                gameScreen.getWindowSize().y / 2, joystick);
        ArrayList<Bullet> bullets = new ArrayList<>();
        Bullet bullet;
        ArrayList<Enemie> enemies = new ArrayList<>();
        Enemie enimie;
        /*ArrayList<Present> presents = new ArrayList<>();
        Present present;*/
        player.addWeapon(new SpecialWeapon());
        while (!stop) {
            Log.d("LogApp", "game Go");
            if ((enemies.size() < 1) /*&& (enemies.isEmpty() ||
                    (random.nextInt() % 400) < player.getScore100())*/)
                /*switch (random.nextGaussian() % 3) {
                    case 0: { enemies.add(new Enimie()); break;}
                    case 1: { enemies.add(new Enimie()); break;}
                    case 2: { enemies.add(new Enimie()); break;}
                }*/
                enemies.add(new Major(20, 30));/*(new Major(random.nextInt() % 2 == 1 ? 0 : GameScreen.getWindowSize().x,
                        GeneralAnimation.minYAnimatPos + random.nextInt() %
                        (GameScreen.getWindowSize().y - GeneralAnimation.maxYAnimatPos)));*/
            canva = gameScreen.getCanvas();
            bullets.addAll(player.action(canva));
           // presents.addAll(mrCat.action(canva));
            for(int i = 0; i < enemies.size(); ++i) {
                enimie = enemies.get(i);
                bullets.addAll(enimie.action(canva, player));
                if (!enimie.isAlive(bullets, player)) {
                    enemies.remove(i);
                    i--;
                }
            }
            /*for(int i = 0; i < presents.size(); ++i) {
                present = presents.get(i);
                if (present.takes(player))
                    i--;
                else presents.draw(canva);
            }*/
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
                Thread.sleep(fps);
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
