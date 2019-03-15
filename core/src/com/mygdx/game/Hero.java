package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

public class Hero {
    private Texture texture;
    private Vector2 position;
    private float speed;

    protected Circle collider;

    public Hero() {
        texture = new Texture("ship64.png");
        position = new Vector2(640.0f, 360.0f);
        speed = 300.0f;

        collider = new Circle(position.x - 32, position.y - 32, 32);
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x - 32.0f, position.y - 32.0f);
    }

    public void update(float dt) {
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            position.x += speed * dt;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            position.x -= speed * dt;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            position.y += speed * dt;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            position.y -= speed * dt;
        }
        if (Gdx.input.isTouched()) {
            if (Gdx.input.getX() > position.x) {
                position.x += speed * dt;
            }
            if (Gdx.input.getX() < position.x) {
                position.x -= speed * dt;
            }
            if (720.0f - Gdx.input.getY() > position.y) {
                position.y += speed * dt;
            }
            if (720.0f - Gdx.input.getY() < position.y) {
                position.y -= speed * dt;
            }
        }

        checkWindowBounds();

        collider.x = position.x - 32;
        collider.y = position.y - 32;
    }

    private void checkWindowBounds() {
        if (position.x + 32 >= 1280) {
            position.x = 1280 - 32;
        }

        if (position.x - 32 < 0) {
            position.x = 32;
        }

        if (position.y + 23 >= 720) {
            position.y = 19;
        }

        if (position.y - 19 < 0) {
            position.y = 697;
        }
    }
}
