package com.example.krabiysok.my_first_android_game.com.example.krabiysok.sprites;

import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Message;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.krabiysok.my_first_android_game.GameProcess;
import com.example.krabiysok.my_first_android_game.Joystick;
import com.example.krabiysok.my_first_android_game.MainActivity;
import com.example.krabiysok.my_first_android_game.R;
import com.example.krabiysok.my_first_android_game.com.example.krabiysok.sprites.com.example.krabisok.bullets.Bullet;
import com.example.krabiysok.my_first_android_game.com.example.krabiysok.weapons.RegularWeapon;
import com.example.krabiysok.my_first_android_game.com.example.krabiysok.weapons.Weapon;

import java.util.ArrayList;

/**
 * Created by KrabiySok on 1/10/2015.
 */
public class Player extends GeneralAnimation {
    private static final int HEALTH = 1000, SPEED = 1000 / GameProcess.fps,
            ACCELER_TIME = 10000 / GameProcess.fps, ACCELERATION = 2;
    private Joystick joystick;
    private int health = HEALTH, accelerTime = ACCELER_TIME, score;
    private ArrayList<Weapon> weapons;
    private ArrayList<Bullet> playerBullets;
    private Weapon weapon;
    private Bullet bullet;
    private Double playerAngle;
    private int moveSpeed, playerX0, playerX1, playerY0, playerY1,
            bulletX0, bulletX1, bulletY0, bulletY1;
    private Message msg;

    public Player(int x, int y, Joystick joystick) {
        super(x, y, 4, 3,
                BitmapFactory.decodeResource(MainActivity.getContext().getResources(),
                R.drawable.boy), 0.12);
        playerBullets  = new ArrayList<>();
        this.joystick = joystick;
        weapons = new ArrayList<>();
        weapons.add(new RegularWeapon());
        weapon = weapons.get(0);
        msg = MainActivity.getHandler().
                obtainMessage(MainActivity.SCORE_CHANGED, score, 0);
        MainActivity.getHandler().sendMessage(msg);
        msg = MainActivity.getHandler().
                obtainMessage(MainActivity.WEAPON_CHANGED, weapon.getWeapon(), weapon.getAmmo());
        MainActivity.getHandler().sendMessage(msg);
    }

    public ArrayList<Bullet> action(Canvas canva) {
        playerAngle = joystick.getDirectionStick();
        moveSpeed = SPEED;
        if (playerAngle != null) {
            if (accelerTime > 0 && joystick.stickInField() == 1) {
                moveSpeed *= ACCELERATION;
                accelerTime--;
            }
        }
        drawMove(canva, playerAngle, moveSpeed);
        for (int i = weapons.size() - 1; ; i--) {
            if (weapons.get(i).getAmmo() <= 0 && i != 0) {
                weapons.remove(i);
            } else {
                if (weapon == null || !weapon.equals(weapons.get(i))) {
                    weapon = weapons.get(i);
                    msg = MainActivity.getHandler().
                         obtainMessage(MainActivity.WEAPON_CHANGED, weapon.getWeapon(),
                                 weapon.getAmmo());
                    MainActivity.getHandler().sendMessage(msg);
                }
                break;
            }
        }
        if (weapon.getReloudTime() < weapon.getBulletReloud()) {
            weapon.reloud();
        }
        playerBullets.clear();
        if (joystick.getAimPosition() != null &&
                weapon.getReloudTime() == weapon.getBulletReloud()) {
            weapon.startReloud();
            msg = MainActivity.getHandler().
                    obtainMessage(MainActivity.AMMO_CHANGED, weapon.getAmmo(), weapon.getAmmo());
            MainActivity.getHandler().sendMessage(msg);
            playerBullets.add(weapon.getBullet(getPosition().x, getPosition().y,
                    this, joystick.getAimAngle(getPosition())));
        }
        return playerBullets;
    }

    public boolean isAlive(ArrayList<Bullet> bullets) {
        for(int i = 0; i < bullets.size(); i++) {
            bullet = bullets.get(i);
            if (!bullet.getBulletBelongs().equals(this)) {
                playerX0 = getPosition().x;
                playerY0 = getPosition().y;
                bulletX0 = bullet.getPosition().x;
                bulletY0 = bullet.getPosition().y;
                playerX1 = playerX0 + getSpriteResolution().x;
                playerY1 = playerY0 + getSpriteResolution().y;
                bulletX1 = bulletX0 + bullet.getSpriteResolution().x;
                bulletY1 = bulletY0 + bullet.getSpriteResolution().y;
                if ((bulletX0 <= playerX0 && playerX0 <= bulletX1 &&
                        bulletY0 <= playerY0 && playerY0 <= bulletY1) ||
                        (bulletX0 <= playerX1 && playerX1 <= bulletX1 &&
                                bulletY0 <= playerY1 && playerY1 <= bulletY1)) {
                    health -= bullet.getDamage();
                    bullets.remove(i);
                    i--;
                }
            }
        }
        if (health <= 0)
            return false;
        msg = MainActivity.getHandler().
                obtainMessage(MainActivity.HEALTH_CHANGED, health, accelerTime);
        MainActivity.getHandler().sendMessage(msg);
        return true;
    }

    public void addWeapon(Weapon weapon) {
        weapons.add(weapon);
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public int getScore100() {
        int score100 = score / 100;
        return score100;
    }

    public int getScore() {
        return score;
    }

    public void takeHealth(int take) {
        health -= take;
    }

    public boolean addHealth(int health) {
        this.health += health;
        if (this.health >= HEALTH) {
            this.health = HEALTH;
            return false;
        }
        return true;
    }

    public boolean addAccelerationTime(int accelerationTime) {
        this.accelerTime += accelerationTime;
        if (this.accelerTime >= ACCELER_TIME) {
            this.accelerTime = ACCELER_TIME;
            return false;
        }
        return true;
    }

    public void addScore(int points) {
        score += points;
        msg = MainActivity.getHandler().
                obtainMessage(MainActivity.SCORE_CHANGED, score, 0);
        MainActivity.getHandler().sendMessage(msg);
    }
}
