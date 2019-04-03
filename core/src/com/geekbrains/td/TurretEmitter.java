package com.geekbrains.td;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TurretEmitter extends ObjectPool<Turret> {
    private GameScreen gameScreen;

    public TurretEmitter(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    @Override
    protected Turret newObject() {
        return new Turret(gameScreen);
    }

    public boolean setup(int cellX, int cellY, Turret.TurretType type) {
        if (!canIDeployItHere(cellX, cellY)) {
            return false;
        }
        Turret turret = getActiveElement();
        turret.setup(cellX, cellY, type);
        return true;
    }

    public void render(SpriteBatch batch) {
        for (int i = 0; i < activeList.size(); i++) {
            activeList.get(i).render(batch);
        }
    }

    public void update(float dt) {
        for (int i = 0; i < activeList.size(); i++) {
            activeList.get(i).update(dt);
        }
    }

    public boolean canIDeployItHere(int cellX, int cellY) {
        if (!gameScreen.getMap().isCellEmpty(cellX, cellY)) {
            return false;
        }
        if (getTurretByCell(cellX, cellY) != null) {
            return false;
        }
        return true;
    }

    public void destroyTurret(int cellX, int cellY) {
        Turret t = getTurretByCell(cellX, cellY);
        if (t != null) {
            t.deactivate();
        }
    }

    public Turret getTurretByCell(int cellX, int cellY) {
        for (int i = 0; i < activeList.size(); i++) {
            Turret t = activeList.get(i);
            if (t.getCellX() == cellX && t.getCellY() == cellY) {
                return t;
            }
        }

        return null;
    }
}
