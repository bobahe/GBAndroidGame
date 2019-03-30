package com.geekbrains.td;

public class Player {
    private int score;
    private int hp;
    private int cash;

    public Player() {
        score = 0;
        hp = 40;
        cash = 250;
    }

    public int getScore() {
        return score;
    }

    public void increaseScore(int score) {
        this.score += score;
    }

    public int getHp() {
        return hp;
    }

    public void decreaseHp(int hp) {
        this.hp -= hp;
    }

    public int getCash() {
        return cash;
    }

    public void increaseCash(int cash) {
        this.cash += cash;
    }

    public void decreaseCash(int cash) {
        this.cash -= cash;
    }
}
