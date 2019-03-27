package com.geekbrains.td;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Предоставляет управление экранами
 */
public class ScreenManager {
    public enum ScreenType {
        GAME
    }

    public static final int WORLD_WIDTH = 1280;
    public static final int WORLD_HEIGHT = 720;

    private TowerDefenseGame game;
    private SpriteBatch batch;
    private GameScreen gameScreen;
    private LoadingScreen loadingScreen;
    private Screen targetScreen;
    private Viewport viewport;
    private Camera camera;

    private static ScreenManager ourInstance = new ScreenManager();

    public static ScreenManager getInstance() {
        return ourInstance;
    }

    public Viewport getViewport() {
        return viewport;
    }

    private ScreenManager() {
    }

    /**
     * Производит инициализацию менеджера
     * @param game объект игры
     * @param batch spritebatch
     */
    public void init(TowerDefenseGame game, SpriteBatch batch) {
        this.game = game;
        this.batch = batch;
        this.camera = new OrthographicCamera(WORLD_WIDTH, WORLD_HEIGHT);
        this.viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        this.gameScreen = new GameScreen(batch, camera);
        this.loadingScreen = new LoadingScreen(batch);
    }

    /**
     * Производит масштабирование в зависимости от размера окна или экрана устройства, на котором запущена игра
     * @param width
     * @param height
     */
    public void resize(int width, int height) {
        viewport.update(width, height);
        viewport.apply();
    }

    /**
     * Позиционирует камеру посередине экрана
     */
    public void resetCamera() {
        camera.position.set(WORLD_WIDTH / 2, WORLD_HEIGHT / 2, 0);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
    }

    /**
     * Меняет отображаемый экран через показ экрана загрузки
     * @param type тип экрана, который следует отобразить
     */
    public void changeScreen(ScreenType type) {
        Screen screen = game.getScreen();
        // Освобождает загруженные до этого ресурсы
        Assets.getInstance().clear();
        // если на момент вызова был активен какой-то экран, то освобождает и его ресурсы
        if (screen != null) {
            screen.dispose();
        }
        // инициализирует камеру
        resetCamera();
        // отображает экран загрузки
        game.setScreen(loadingScreen);
        switch (type) {
            // если тип экрана GAME, то устанавливает поле и загружает необходимые для этого экрана ресурсы
            case GAME:
                targetScreen = gameScreen;
                Assets.getInstance().loadAssets(ScreenType.GAME);
                break;
        }
    }

    /**
     * Отображает экран сохраненный в поле targetScreen
     */
    public void goToTarget() {
        game.setScreen(targetScreen);
    }
}
