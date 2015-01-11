package com.example.krabiysok.my_first_android_game.com.example.krabiysok.sprites;

import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.example.krabiysok.my_first_android_game.GameProcess;
import com.example.krabiysok.my_first_android_game.Joystick;
import com.example.krabiysok.my_first_android_game.MainActivity;
import com.example.krabiysok.my_first_android_game.R;
import com.example.krabiysok.my_first_android_game.com.example.krabiysok.sprites.com.example.krabisok.bullets.Bullet;

import java.util.ArrayList;

/**
 * Created by KrabiySok on 1/10/2015.
 */
public class Player extends GeneralAnimation {
    private static final int HEALTH = 100, SPEED = 20000 / GameProcess.fps,
            ACCELER_TIME = 30000 / GameProcess.fps, ACCELERATION = 2;
    private Joystick joystick;
    private int health = HEALTH, accelerTime = ACCELER_TIME;
    private ArrayList<Weapon> weapons;
    private Weapon weapon;
    private Bullet bullet;
    private Double playerAngle;
    private int moveSpeed, playerX0, playerX1, playerY0, playerY1,
            bulletX0, bulletX1, bulletY0, bulletY1;

    Player(int x, int y, Joystick joystick) {
        super(x, y, 4, 3,
                BitmapFactory.decodeResource(MainActivity.getContext().getResources(),
                R.drawable.boy), null);
        this.joystick = joystick;
        weapons = new ArrayList<>(new RegularWeapon());
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
        for(int i = 0; i < weapons.size(); i++) {
            weapon = weapons.get(i);
            if (weapon.isNoAmmo()) {
                weapons.remove(i);
                i--;
            } break;
        }
        if (weapon.reloudTime() < weapon.BULLET_RELOUD)
            weapon.reloud();
        if (joystick.getAimPosition() != null &&
                weapon.reloudTime() == weapon.BULLET_RELOUD) {
            weapon.startReloud();
            return weapon.getBullet();
        }
        return null;
    }

    public boolean isAlive(ArrayList<Bullet> bullets) {
        for(int i = 0; i < bullets.size(); i++) {
            playerX0 = getPosition().x;
            playerY0 = getPosition().y;
            bulletX0 = bullet.getPosition().x;
            bulletY0 = bullet.getPosition().y;
            playerX1 = playerX0 + getSpriteRezolution().x;
            playerY1 = playerY0 + getSpriteRezolution().y;
            bulletX1 = bulletX0 + bullet.getSpriteRezolution().x;
            bulletY1 = bulletY0 + bullet.getSpriteRezolution().y;
            if ((bulletX0 >= playerX0 && bulletX1 <= playerX0 &&
                    bulletY0 >= playerY0 && bulletY1 <= playerY0) ||
                    (bulletX0 >= playerX1 && bulletX1 <= playerX1 &&
                            bulletY0 >= playerY1 && bulletY1 <= playerY1)) {
                health -= bullet.damage();
                bullets.remove(i);
                i--;
            }
        }
        if (health <= 0)
            return false;
        return true;
    }

    public void addWeapon(Weapon weapon) {
        weapons.add(weapon);
    }

    public Weapon getWeapon() {
        return weapon;
    }
}
