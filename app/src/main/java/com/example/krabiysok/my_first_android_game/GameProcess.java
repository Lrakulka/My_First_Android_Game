package com.example.krabiysok.my_first_android_game;

import android.util.Log;



/**
 * Created by KrabiySok on 1/10/2015.
 */
public class GameProcess implements Runnable {
    private static GameProcess gameProcess = new GameProcess();
    private GameScreen gameScreen;
    private Joystick joystick;
    private boolean stop, sleepGame;
    private Thread gameThread;

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
        while (!stop) {
            Log.d("LogApp", "as");
            try {
                Thread.sleep(2000);
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
