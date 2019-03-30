package com.geekbrains.td;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

public class Monster implements Poolable {
    private GameScreen gameScreen;
    private Map map;

    private TextureRegion texture;
    private TextureRegion textureHp;
    private Vector2 position;
    private Vector2 destination;

    private int currentPoint;
    private Vector2 velocity;

    private int hp;
    private int hpMax;

    private Circle solidBody;

    private boolean active;

    @Override
    public boolean isActive() {
        return active;
    }

    public Vector2 getPosition() {
        return position;
    }

    public Circle getSolidBody() {
        return solidBody;
    }

    public Monster(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        this.map = gameScreen.getMap();
        this.texture = Assets.getInstance().getAtlas().findRegion("monster");
        this.textureHp = Assets.getInstance().getAtlas().findRegion("monsterHp");
        this.position = new Vector2(640, 360);
        this.destination = new Vector2(0, 0);
        this.velocity = new Vector2(0, 0);
        this.hpMax = 100;
        this.hp = this.hpMax;
        this.active = false;
        this.solidBody = new Circle(this.position.x, this.position.y, 32);
    }

    public void activate(float x, float y) {
        this.texture = Assets.getInstance().getAtlas().findRegion("monster");
        this.textureHp = Assets.getInstance().getAtlas().findRegion("monsterHp");
        this.position = new Vector2(x, y);
        this.velocity = new Vector2(-100.0f, 0.0f);
        this.hpMax = 100;
        this.hp = this.hpMax;
        this.getNextPoint();
        this.active = true;
    }

    public void getNextPoint() {
        List<Vector2> path = new ArrayList<>();
        path.add(position);
        for (int i = 0; i < 5; i++) {
            Vector2 tmp = path.get(path.size() - 1);
            int tmpCX = (int) (tmp.x / 80);
            int tmpCY = (int) (tmp.y / 80);
            if (tmpCX > 0 && map.isCellEmpty(tmpCX - 1, tmpCY)) {
                path.add(new Vector2((tmpCX - 1) * 80 + 40, tmpCY * 80 + 40));
            } else if (tmpCY < 8 && map.isCellEmpty(tmpCX, tmpCY + 1)) {
                path.add(new Vector2(tmpCX * 80 + 40, (tmpCY + 1) * 80 + 40));
            } else if (tmpCY > 0 && map.isCellEmpty(tmpCX, tmpCY - 1)) {
                path.add(new Vector2(tmpCX * 80 + 40, (tmpCY - 1) * 80 + 40));
            } else {
                path.add(tmp);
            }
        }
        destination.set(path.get(1));
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x - 40, position.y - 40);
        batch.draw(textureHp, position.x - 40, position.y + 40 - 12, 56 * ((float) hp / hpMax), 12);
    }

    public void update(float dt) {
        velocity.set(destination).sub(position).nor().scl(100.0f);
        position.mulAdd(velocity, dt);
        this.solidBody.set(this.position.x + 32, this.position.y + 32, 26);

        if (position.x < 42) {
            deactivate();
            gameScreen.monsterEnteredTower();
        }

        if (position.dst(destination) < 2.0f) {
            getNextPoint();
        }

    }

    public void deactivate() {
        this.active = false;
    }

    public void damaged(int hp) {
        this.hp -= hp;

        if (this.hp <= 0) {
            deactivate();
            gameScreen.monsterKilled();
        }
    }
}
