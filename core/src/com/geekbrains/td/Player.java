package com.geekbrains.td;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Player {
    private int coins;
    private int hp;
    private int hpMax;

    public int getCoins() {
        return coins;
    }

    public boolean isMoneyEnough(int amount) {
        return coins >= amount;
    }

    public void decreaseHp(int amount) {
        hp -= amount;
    }

    public void changeCoins(int amount) {
        this.coins += amount;
    }

    public Player() {
        this.coins = 500;
        this.hpMax = 100;
        this.hp = this.hpMax;
    }

    public void renderInfo(SpriteBatch batch, BitmapFont font) {
        font.draw(batch, "HP: " + hp + " / " + hpMax, 20, 700);
        font.draw(batch, "Coins: " + coins, 20, 680);
    }
}
