package com.geekbrains.td;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class King {
    private GameScreen gameScreen;
    private Vector2 position;
    private TextureRegion[] texture;
    private TextureRegion textureHp;
    private TextureRegion textureBackHp;
    private float animationTimer, timePerFrame;
    private int hp, hpMax;
    private Vector2 velocity;
    private Vector2 destination;

    public boolean isAlive() {
        return hp >= 0;
    }

    public int getCellX() {
        return (int) (position.x / 80);
    }

    public int getCellY() {
        return (int) (position.y / 80);
    }

    public Vector2 getPosition() {
        return position;
    }

    public void takeDamage(int amount) {
        hp -= amount;
    }

    public King(GameScreen gameScreen) {
        this.position = new Vector2(0 * 80 + 40, 4 * 80 + 40);
        this.velocity = new Vector2(0, 0);
        this.destination = new Vector2(0 * 80 + 40, 4 * 80 + 40);
        this.texture = new TextureRegion(Assets.getInstance().getAtlas().findRegion("animatedKing")).split(80, 80)[0];
        this.textureHp = Assets.getInstance().getAtlas().findRegion("monsterHp");
        this.textureBackHp = Assets.getInstance().getAtlas().findRegion("monsterBackHP");
        this.timePerFrame = 0.1f;
        this.hpMax = 200;
        this.hp = this.hpMax;
        this.gameScreen = gameScreen;
        getNextPoint();
    }

    private void getNextPoint() {
        while (true) {
            boolean directionAxisX = MathUtils.randomBoolean();
            int step = MathUtils.random(-1, 1);

            if (directionAxisX) {
                destination.set(getCellX() + step, getCellY());
            } else {
                destination.set(getCellX(), getCellY() + step);
            }

            if (destination.x >= 0 && destination.x < 8 && destination.y >= 0 && destination.y < 9) {
                if (gameScreen.getMap().isCellEmpty((int) destination.x, (int) destination.y)) {
                    break;
                }
            }
        }

        destination.scl(80, 80).add(40, 40);
    }

    public void update(float dt) {
        animationTimer += dt;

        velocity.set(destination).sub(position).nor().scl(150.0f);
        position.mulAdd(velocity, dt);
        if (position.dst(destination) < 2.0f) {
            getNextPoint();
        }
    }

    public void render(SpriteBatch batch, BitmapFont font) {
        int index = (int) (animationTimer / timePerFrame) % texture.length;
        batch.draw(texture[index], position.x - 40, position.y - 40);
        batch.setColor(1, 1, 1, 0.8f);
        batch.draw(textureBackHp, position.x - 30, position.y + 40 - 16 + 4 * (float) Math.sin(animationTimer * 5));
        batch.draw(textureHp, position.x - 30 + 2, position.y + 40 - 14 + 4 * (float) Math.sin(animationTimer * 5), 56 * ((float) hp / hpMax), 12);
        font.draw(batch, "" + hp, position.x - 30, position.y + 42 + 4 * (float) Math.sin(animationTimer * 5), 60, 1, false);
        batch.setColor(1, 1, 1, 1);
    }

    public boolean wasKilled() {
        return this.hp <= 0;
    }
}
