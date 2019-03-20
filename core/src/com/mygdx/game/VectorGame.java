package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class VectorGame extends ApplicationAdapter {
    // Домашнее задание:
    // У нас есть пушка в левом нижнем углу экрана (пушку можно не рисовать)
    // Кликая мышкой по экрану пушка должна стрельнуть камнем в этом направлении
    // На камень действует сила тяжести
    // Камнем можно повторно стрельнуть, только если он вылетел за пределы экрана
    SpriteBatch batch;
    Texture textureAsteroids;
    TextureRegion astReg;
    Cannonball cannonball;


    @Override
    public void create() {
        batch = new SpriteBatch();
        textureAsteroids = new Texture("asteroids64.png");
        astReg = new TextureRegion(textureAsteroids, 0, 0, 64, 64);
        cannonball = new Cannonball(astReg);
    }

    @Override
    public void render() {
        float dt = Gdx.graphics.getDeltaTime();
        update(dt);
        Gdx.gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        cannonball.render(batch);
        batch.end();
    }

    public void update(float dt) {
        cannonball.update(dt);

        if (Gdx.input.isTouched()) {
            cannonball.shoot(Gdx.input.getX(), 720 - Gdx.input.getY());
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
