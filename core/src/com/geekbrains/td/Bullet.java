package com.geekbrains.td;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

public class Bullet implements Poolable {
    private Vector2 position;
    private Vector2 velocity;
    private boolean active;
    private Circle solidBody;

    public Vector2 getPosition() {
        return position;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public void updateSolidBodyPosition() {
        this.solidBody.set(this.position.x - 8, this.position.y - 8, 16);
    }

    @Override
    public boolean isActive() {
        return active;
    }

    public void deactivate() {
        this.active = false;
    }

    public Bullet() {
        this.position = new Vector2(0, 0);
        this.velocity = new Vector2(0, 0);
        this.active = false;
        this.solidBody = new Circle(0, 0, 16);
    }

    public void setup(float x, float y, float vx, float vy) {
        this.position.set(x, y);
        this.velocity.set(vx, vy);
        this.active = true;
    }
}
