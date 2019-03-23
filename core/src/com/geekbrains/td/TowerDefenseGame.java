package com.geekbrains.td;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TowerDefenseGame extends Game {
    //    Домашнее задание:
    // 1. Разобраться с кодом
    // 2. При повторном клике на выбранную клетку, туррель должна в нее переместиться
    // 3. (Задача на подумать, отвечать текстом) Как вы видите систему перемещения
    // монстров по заданной траектории?
    // 4. * Попробовать сделать так, чтобы туррель поворачивалась в сторону монстра

    // Ответ на 3 вопрос:
    // Может быть хранить в карте координаты ячеек для пути, а у монстра при достижении каждой ячейки
    // пересчитывать вектор движения до середины следующей ячейки? Если путей несолько в карте,
    // то выбирать случайным образом при создании монстра.

    private SpriteBatch batch;
    private Screen currentScreen;

    @Override
    public void create() {
        this.batch = new SpriteBatch();
        this.currentScreen = new GameScreen(batch);
        this.setScreen(currentScreen);
    }

    @Override
    public void render() {
        float dt = Gdx.graphics.getDeltaTime();
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.currentScreen.render(dt);
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
