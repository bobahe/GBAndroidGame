package com.geekbrains.td;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Turret {
    private GameScreen gameScreen;

    private TextureRegion texture;
    private int cellX, cellY;
    private float angle;
    private Vector2 direction;

    public Turret(GameScreen gameScreen, TextureAtlas atlas) {
        this.gameScreen = gameScreen;
        this.texture = new TextureRegion(atlas.findRegion("turrets"), 0, 0, 80, 80);
        this.cellX = 8;
        this.cellY = 4;
        direction = new Vector2();
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, cellX * 80, cellY * 80, 40, 40, 80, 80, 1, 1, angle);
    }

    public void update(float dt) {
        //angle += 180.0 * dt;
        direction.set(gameScreen.getMonster().getPosition().x - cellX * 80,
                gameScreen.getMonster().getPosition().y - cellY * 80);

        angle = (float) Math.toDegrees(Math.atan2(direction.y, direction.x));
    }

    public void setTurretPosition(int cellX, int cellY) {
        this.cellX = cellX;
        this.cellY = cellY;
    }
}
