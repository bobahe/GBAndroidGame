package com.geekbrains.td;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

/**
 * Представляет пул частиц и реализует их отрисовку
 */
public class ParticleEmitter extends ObjectPool<Particle> {
    private TextureRegion oneParticle;

    public ParticleEmitter() {
        this.oneParticle = Assets.getInstance().getAtlas().findRegion("star16");
    }

    @Override
    protected Particle newObject() {
        return new Particle();
    }

    public void render(SpriteBatch batch) {
        batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        for (int i = 0; i < activeList.size(); i++) {
            Particle o = activeList.get(i);
            float t = o.getTime() / o.getTimeMax();
            float scale = lerp(o.getSize1(), o.getSize2(), t);
            batch.setColor(lerp(o.getR1(), o.getR2(), t), lerp(o.getG1(), o.getG2(), t), lerp(o.getB1(), o.getB2(), t), lerp(o.getA1(), o.getA2(), t));
            batch.draw(oneParticle, o.getPosition().x - 8, o.getPosition().y - 8, 8, 8, 16, 16, scale, scale, 0);
        }
        batch.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
        for (int i = 0; i < activeList.size(); i++) {
            Particle o = activeList.get(i);
            float t = o.getTime() / o.getTimeMax();
            float scale = lerp(o.getSize1(), o.getSize2(), t);
            batch.setColor(lerp(o.getR1(), o.getR2(), t), lerp(o.getG1(), o.getG2(), t), lerp(o.getB1(), o.getB2(), t), lerp(o.getA1(), o.getA2(), t));
            batch.draw(oneParticle, o.getPosition().x - 8, o.getPosition().y - 8, 8, 8, 16, 16, scale, scale, 0);
        }
        batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        batch.setColor(1.0f, 1.0f, 1.0f, 1.0f);
    }

    /**
     * Устанавливает новые параметры для взятой из пула частицы
     * @param x координата х
     * @param y координата y
     * @param vx скорость по оси х
     * @param vy скорость по оси y
     * @param timeMax максимальное время жизни частицы
     * @param size1 начальный размер частицы
     * @param size2 конечный размер частицы
     * @param r1 красная составляющая начального цвета
     * @param g1 зеленая составляющая начального цвета
     * @param b1 синяя составляющая начального цвета
     * @param a1 альфа составляющая начального цвета
     * @param r2 красная составляющая начального цвета
     * @param g2 зеленая составляющая начального цвета
     * @param b2 синяя составляющая начального цвета
     * @param a2 альфа составляющая начального цвета
     */
    public void setup(float x, float y, float vx, float vy, float timeMax, float size1, float size2, float r1, float g1, float b1, float a1, float r2, float g2, float b2, float a2) {
        Particle item = getActiveElement();
        item.init(x, y, vx, vy, timeMax, size1, size2, r1, g1, b1, a1, r2, g2, b2, a2);
    }

    public void setupByTwoPoints(float x1, float y1, float x2, float y2, float timeMax, float size1, float size2, float r1, float g1, float b1, float a1, float r2, float g2, float b2, float a2) {
        Particle item = getActiveElement();
        item.init(x1, y1, (x2 - x1) / timeMax, (y2 - y1) / timeMax, timeMax, size1, size2, r1, g1, b1, a1, r2, g2, b2, a2);
    }

    public void update(float dt) {
        for (int i = 0; i < activeList.size(); i++) {
            activeList.get(i).update(dt);
        }
    }

    /**
     * Пересчитывает размер, цвет частицы в зависмости от времени
     * @param value1 актуальный размер
     * @param value2 конечный размер
     * @param point коэффициент времени жизни
     * @return размер или цвет частицы
     */
    public float lerp(float value1, float value2, float point) {
        return value1 + (value2 - value1) * point;
    }
}
