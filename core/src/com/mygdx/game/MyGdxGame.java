package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MyGdxGame extends ApplicationAdapter {
    SpriteBatch batch;
    Background background;
    Hero hero;

    // Варианты игры:
    // =============================
    // Гонки
    // Герои 3
    // Платформер
    // RTS
    // Tower defence
    // Косм. стрелялка вид сбоку
    // Косм. стрелялка вид сверху
    // Worms
    // Battle toads
    // Набор логических игр (изучение английского)
    // Арканоид
    // Танки
    // Тактика?

    @Override
    public void create() {
        batch = new SpriteBatch();
        background = new Background();
        hero = new Hero();
    }

    @Override
    public void render() {
        float dt = Gdx.graphics.getDeltaTime();
        update(dt);
        Gdx.gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.render(batch);
        hero.render(batch);
        batch.end();
    }

    public void update(float dt) {
        background.update(dt);
        hero.update(dt);
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
