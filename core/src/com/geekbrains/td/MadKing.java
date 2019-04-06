package com.geekbrains.td;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class MadKing {
    private GameScreen gameScreen;

    private TextureRegion texture;

    private Vector2 position;
    private Vector2 velocity;
    private Vector2 destination;

    private Map map;

    public Vector2 getPosition() {
        return position;
    }

    public MadKing(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        this.map = gameScreen.getMap();
        this.texture = Assets.getInstance().getAtlas().findRegion("star16");
//        this.textureHp = Assets.getInstance().getAtlas().findRegion("monsterHp");
//        this.textureBackHp = Assets.getInstance().getAtlas().findRegion("monsterBackHP");
        this.position = new Vector2(40, 360);
        this.destination = new Vector2(0, 0);
        this.velocity = new Vector2(0, 0);
    }

    public void getNextPoint() {
        destination.set(MathUtils.random(0, 4), MathUtils.random(0, 8));
        destination.scl(80, 80).add(40, 40);
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x - 16, position.y - 16, 32, 32);
//        batch.draw(textureBackHp, position.x - 30, position.y + 40 - 16);
//        batch.draw(textureHp, position.x - 30 + 2, position.y + 40 - 14, 56 * ((float) hp / hpMax), 12);
    }

    public void update(float dt) {
        velocity.set(destination).sub(position).nor().scl(150.0f);
        position.mulAdd(velocity, dt);
        if (position.dst(destination) < 2.0f) {
            getNextPoint();
        }
    }
}
