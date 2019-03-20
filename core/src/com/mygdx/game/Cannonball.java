package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Cannonball {
    Vector2 position;
    Vector2 velocity;
    TextureRegion region;
    Vector2 mousePosition;
    Vector2 tmp;

    public Cannonball(TextureRegion region) {
        this.position = new Vector2(-32, -32);
        this.velocity = new Vector2(0, 0);
        this.region = region;
        this.mousePosition = new Vector2(0, 0);
        this.tmp = new Vector2(0, 0);
    }

    public void render(SpriteBatch batch) {
        batch.draw(region, position.x - 32, position.y - 32);
    }

    public void update(float dt) {
        position.mulAdd(velocity, dt);

        velocity.y -= 300.0f * dt;
        velocity.x *= 0.997f;

        if (velocity.x < 10 && velocity.y < 10) {
            setInitialPosition();
        }

        if (position.y < 32.0f) {
            position.y = 32.0f;
            velocity.y *= -0.7f;
        }
    }

    private void setInitialPosition() {
        position.x = -32;
        position.y = -32;
        velocity.set(0, 0);
    }

    public void shoot(float x, float y) {
        if (position.x >= 1280 + 32 || position.x <= -32) {
            setInitialPosition();

            mousePosition.x = x;
            mousePosition.y = y;

            tmp.set(mousePosition);
            tmp.sub(position);
            tmp.nor();
            tmp.scl(1200.0f);

            velocity.add(tmp);
        }
    }
}
