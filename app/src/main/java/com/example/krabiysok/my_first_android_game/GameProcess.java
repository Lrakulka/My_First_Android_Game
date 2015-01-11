package com.example.krabiysok.my_first_android_game;

import android.graphics.Canvas;
import android.util.Log;

import com.example.krabiysok.my_first_android_game.com.example.krabiysok.sprites.Player;

import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;


/**
 * Created by KrabiySok on 1/10/2015.
 */
public class GameProcess implements Runnable {
    private static GameProcess gameProcess = new GameProcess();
    private GameScreen gameScreen;
    private Joystick joystick;
    private boolean stop, sleepGame;
    private Thread gameThread;
    public static final int fps = 100; // Speed of updates
    private GameProcess() {}

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
        Player player = new Player(gameScreen.getWindowSize().x,
                gameScreen.getWindowSize().y, joystick);
        ArrayList<Enemie> enemies = new ArrayList<>();
        ArrayList<Bullet> bullets = new ArrayList<>();
        ArrayList<Present> presents = new ArrayList<>();
        Enimie enimie;
        Present present;
        Random random = new Random();
        while (!stop) {
            Log.d("LogApp", "game Go");
            if (enemies.isEmpty() || random.nextInt() % 20 < Player.getScore100())
                switch (random.nextGaussian() % 3) {
                    case 0: { enemies.add(new Enimie()); break;}
                    case 1: { enemies.add(new Enimie()); break;}
                    case 2: { enemies.add(new Enimie()); break;}
                }
            canva = gameScreen.getCanvas();
            bullets.addAll(player.action(canva));
            presents.addAll(mrCat.action(canva));
            for(int i = 0; i < enemies.size(); ++i) {
                enimie = enemies.get(i);
                bullets.addAll(enimie.action(canva));
                if (!enimie.isAlive(bullets)) {
                    enemies.remove(i);
                    i--;
                }
            }
            for(int i = 0; i < presents.size(); ++i) {
                present = presents.get(i);
                if (present.takes(player))
                    i--;
                else presents.draw(canva);
            }
            if (!player.isAlive(bullets))
                stopGame(); //---
            for(Bullet bullet : bullets)
                bullet.draw(canva);
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
