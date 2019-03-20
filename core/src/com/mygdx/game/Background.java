package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

public class Background {
    private class Star {
        private float x, y;
        private float speed;

        public Star() {
            this.x = MathUtils.random(0, 1280);
            this.y = MathUtils.random(0, 720);
            this.speed = MathUtils.random(50.0f, 120.0f);
        }

        public void update(float dt) {
            x -= speed * dt;
            if (x < -16) {
                x = 1280.0f;
                y = MathUtils.random(0, 720);
                speed = MathUtils.random(50.0f, 120.0f);
            }
        }
    }

    private Texture texture;
    private Texture textureStar;
    private Star[] stars;

    public Background() {
        this.texture = new Texture("bg.png");
        this.textureStar = new Texture("star16.png");
        this.stars = new Star[400];
        for (int i = 0; i < stars.length; i++) {
            stars[i] = new Star();
        }
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, 0, 0);
        for (int i = 0; i < stars.length; i++) {
            float scale = stars[i].speed / 150.0f;
            if (MathUtils.random(0, 500) < 2) {
                scale *= 1.5f;
            }
            batch.draw(textureStar, stars[i].x - 8, stars[i].y - 8, 8, 8, 16, 16, scale, scale, 0, 0, 0, 16, 16, false, false);
        }
    }

    public void update(float dt) {
        for (int i = 0; i < stars.length; i++) {
            stars[i].update(dt);
        }
    }
}
