package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Meteor {
    private Vector2 position;
    private Texture texture;
    private float speed;
    private float rotationSpeed;
    private float rotation;
    private int meteorType;

    protected Circle collider;

    public Meteor() {
        position = new Vector2(1280 + 32, MathUtils.random(32, 698));
        texture = new Texture("asteroids64.png");
        this.speed = MathUtils.random(150f, 200f);
        this.rotationSpeed = MathUtils.random(40f, 80f);
        meteorType = MathUtils.random(0, 3);

        collider = new Circle(position.x - 32, position.y - 32, 32);
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x - 32, position.y - 32, 32, 32,
                64, 64, 1, 1, rotation,
                meteorType * 64, 0, 64, 64, false, false);
    }

    public void update(float dt) {
        position.x -= speed * dt;
        rotation += rotationSpeed * dt;

        if (position.x < -32) {
            setInitialPosition();
        }

        collider.x = position.x - 32;
        collider.y = position.y - 32;
    }

    public void setInitialPosition() {
        position.x = 1280 + 32;
        position.y = MathUtils.random(32, 698);
        speed = MathUtils.random(150f, 200f);
        rotationSpeed = MathUtils.random(40f, 80f);
        meteorType = MathUtils.random(0, 3);
    }
}
