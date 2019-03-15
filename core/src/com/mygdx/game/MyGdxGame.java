package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MyGdxGame extends ApplicationAdapter {
    SpriteBatch batch;
    Background background;
    Hero hero;
    Meteor meteor;

    // Разобраться с кодом
    // Не давать кораблю вылетать за лвую и правую сторону экрана
    // Верхнюю и нижнюю сторону экрана корабль должен пролетать насквозь
    // * Добавить астероид, который летает по экрану по типу звезд
    // и проверять столкновение этого астероида с кораблем, при столкновении
    // "пересоздавать" астероид

    // Варианты игры: Гонки, Герои 3, Марио, RTS, tower defence, косм. стрелялка вид (сбоку/сверху)
    // worms, battle toads

    @Override
    public void create() {
        batch = new SpriteBatch();
        background = new Background();
        hero = new Hero();
        meteor = new Meteor();
    }

    @Override
    public void render() {
        float dt = Gdx.graphics.getDeltaTime();
        update(dt);
        Gdx.gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.render(batch);
        meteor.render(batch);
        hero.render(batch);
        batch.end();
    }

    public void update(float dt) {
        background.update(dt);
        meteor.update(dt);
        hero.update(dt);

        if (hero.collider.overlaps(meteor.collider)) {
            meteor.setInitialPosition();
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
