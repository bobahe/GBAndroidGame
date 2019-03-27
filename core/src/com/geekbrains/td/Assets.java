package com.geekbrains.td;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;

/**
 * Организует единое место для управления ресурсами (картинками, шрифтами, звуками)
 */
public class Assets {
    private static final Assets ourInstance = new Assets();

    public static Assets getInstance() {
        return ourInstance;
    }

    private AssetManager assetManager;
    private TextureAtlas textureAtlas;

    public TextureAtlas getAtlas() {
        return textureAtlas;
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    private Assets() {
        assetManager = new AssetManager();
    }

    /**
     * Загружает необходимые ресурсы в зависимости от переданного типа экрана
     * @param type
     */
    public void loadAssets(ScreenManager.ScreenType type) {
        // загрузка необходимых ассетсов для разных типов экранов
        switch (type) {
            case GAME:
                // загрузка атласа текстур AssetManagero'ом
                assetManager.load("images/game.pack", TextureAtlas.class);
                // генерация шрифта с размером 24
                createStandardFont(24);
                createStandardFont(36);
                break;
        }
    }

    /**
     * Генерация шрифта с заданными паратметрами
     * @param size размер генерируемого шрифта
     */
    public void createStandardFont(int size) {
        FileHandleResolver resolver = new InternalFileHandleResolver();
        assetManager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
        assetManager.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));
        FreetypeFontLoader.FreeTypeFontLoaderParameter fontParameter = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        fontParameter.fontFileName = "fonts/gomarice.ttf";
        fontParameter.fontParameters.size = size;
        fontParameter.fontParameters.color = Color.WHITE;
        fontParameter.fontParameters.borderWidth = 1;
        fontParameter.fontParameters.borderColor = Color.BLACK;
        fontParameter.fontParameters.shadowOffsetX = 1;
        fontParameter.fontParameters.shadowOffsetY = 1;
        fontParameter.fontParameters.shadowColor = Color.BLACK;
        assetManager.load("fonts/zorque" + size + ".ttf", BitmapFont.class, fontParameter);
    }

    /**
     * Инициализирует атлас текстур. Необходимо запускать после того, как assetManager его полностью загрузит в память
     */
    public void makeLinks() {
        textureAtlas = assetManager.get("images/game.pack", TextureAtlas.class);
    }

    /**
     * Очистка памяти от уже ненужных ресурсов
     */
    public void clear() {
        assetManager.clear();
    }
}
