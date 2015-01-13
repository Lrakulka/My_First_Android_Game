package com.example.krabiysok.my_first_android_game.com.example.krabiysok.sprites.com.example.krabiysok.enemies;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.example.krabiysok.my_first_android_game.GameProcess;
import com.example.krabiysok.my_first_android_game.Joystick;
import com.example.krabiysok.my_first_android_game.com.example.krabiysok.sprites.GeneralAnimation;
import com.example.krabiysok.my_first_android_game.com.example.krabiysok.sprites.Player;
import com.example.krabiysok.my_first_android_game.com.example.krabiysok.sprites.com.example.krabisok.bullets.Bullet;
import com.example.krabiysok.my_first_android_game.com.example.krabiysok.weapons.MajorWeapon;
import com.example.krabiysok.my_first_android_game.com.example.krabiysok.weapons.Weapon;

import java.util.ArrayList;

/**
 * Created by KrabiySok on 1/13/2015.
 */
public class Enemie extends GeneralAnimation {
    protected int health, accelerTime, speed, acceleration, acceler_time;
    protected ArrayList<Bullet> enemieBullets;
    protected Weapon weapon;
    protected Bullet bullet;
    protected Double enemieAngle;
    protected int moveSpeed, enemieX0, enemieX1, enemieY0, enemieY1,
            bulletX0, bulletX1, bulletY0, bulletY1;

    public Enemie(int x, int y, Bitmap enemieSprite, int health,
                  int speed, int acceler_time, int acceleration) {
        super(x, y, 4, 3, enemieSprite, 0.12);
        this.health = health;
        this.speed = speed;
        this.acceler_time = acceler_time;
        this.acceleration = acceleration;
    }

    public ArrayList<Bullet> action(Canvas canva, Player player) {
        enemieAngle = Joystick.getAngleBetween(getPosition(), player.getPosition());
        moveSpeed = speed;
        if (enemieAngle != null) {
            if (accelerTime > 0) {
                moveSpeed *= acceleration;
                accelerTime--;
            }
        }
        drawMove(canva, enemieAngle, moveSpeed);
        if (weapon.getAmmo() > 0) {
            if (weapon.getReloudTime() < weapon.getBulletReloud())
                weapon.reloud();
            enemieBullets.clear();
            if (weapon.getReloudTime() == weapon.getBulletReloud()) {
                weapon.startReloud();
                enemieBullets.add(weapon.getBullet(getPosition().x, getPosition().y,
                        this, enemieAngle));
            }
        }
        return enemieBullets;
    }

    public boolean isAlive(ArrayList<Bullet> bullets, Player player) {
        for(int i = 0; i < bullets.size(); i++) {
            bullet = bullets.get(i);
            if (bullet.getBulletBelongs().equals(player)) {
                enemieX0 = getPosition().x;
                enemieY0 = getPosition().y;
                bulletX0 = bullet.getPosition().x;
                bulletY0 = bullet.getPosition().y;
                enemieX1 = enemieX0 + getSpriteResolution().x;
                enemieY1 = enemieY0 + getSpriteResolution().y;
                bulletX1 = bulletX0 + bullet.getSpriteResolution().x;
                bulletY1 = bulletY0 + bullet.getSpriteResolution().y;
                if ((bulletX0 <= enemieX0 && enemieX0 <= bulletX1 &&
                        bulletY0 <= enemieY0 && enemieY0 <= bulletY1) ||
                        (bulletX0 <= enemieX1 && enemieX1 <= bulletX1 &&
                                bulletY0 <= enemieY1 && enemieY1 <= bulletY1)) {
                    health -= bullet.getDamage();
                    if (bullet.getBulletBelongs().equals(player))
                        player.addScore(bullet.getDamage());
                    bullets.remove(i);
                    i--;
                }
            }
        }
        if (health <= 0)
            return false;
        return true;
    }

    public Weapon getWeapon() {
        return weapon;
    }
}
