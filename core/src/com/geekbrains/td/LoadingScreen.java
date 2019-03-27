package com.geekbrains.td;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Представляет экран загрузки
 */
public class LoadingScreen implements Screen {
    private SpriteBatch batch;
    private Texture texture;

    public LoadingScreen(SpriteBatch batch) {
        this.batch = batch;
        // создает картинку в памяти с соответствующими параметрами
        Pixmap pixmap = new Pixmap(1280, 40, Pixmap.Format.RGB888);
        // устанавливает цвет заливки
        pixmap.setColor(Color.GREEN);
        // зиливает картинку
        pixmap.fill();
        this.texture = new Texture(pixmap);
        pixmap.dispose();
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        // проверяет загрузились ли все необходимые ресурсы AssetsManager'ом
        if (Assets.getInstance().getAssetManager().update()) {
            // инициализирует ссылку на загруженный атлас
            Assets.getInstance().makeLinks();
            // осуществляет переход к целевому экрану
            ScreenManager.getInstance().goToTarget();
        }
        batch.begin();
        // отрисовывает текстуру сгенерированную с помощью pixmap с длиной соответствующей прогрессу загрузки ресурсов
        batch.draw(texture, 0, 0, 1280 * Assets.getInstance().getAssetManager().getProgress(), 40);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        // производит масштабирование под экран устройства, на котором запущена игра
        ScreenManager.getInstance().resize(width, height);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        texture.dispose();
    }
}
