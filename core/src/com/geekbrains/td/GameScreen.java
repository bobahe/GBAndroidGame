package com.geekbrains.td;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class GameScreen implements Screen {
    private SpriteBatch batch;
    private Vector2 mousePosition;
    private Map map;
    private TextureRegion selectedCellTexture;
    private ParticleEmitter particleEmitter;
    private MonsterEmitter monsterEmitter;
    private BulletEmitter bulletEmitter;
    private TurretEmitter turretEmitter;
    private InfoEmitter infoEmitter;
    private BitmapFont font18;
    private BitmapFont font24;
    private BitmapFont font134;
    private int selectedCellX, selectedCellY;
    private float monsterTimer;
    private Player player;
    private King king;

    private Stage stage;
    private Group groupTurretAction;
    private Group groupTurretSelection;
    private Group groupGameOver;

    private String gameoverText = "GAME OVER";
    private GlyphLayout layout;
    private float gameoverTimer;

    public Map getMap() {
        return map;
    }

    public ParticleEmitter getParticleEmitter() {
        return particleEmitter;
    }

    public MonsterEmitter getMonsterEmitter() {
        return monsterEmitter;
    }

    public BulletEmitter getBulletEmitter() {
        return bulletEmitter;
    }

    public GameScreen(SpriteBatch batch) {
        this.batch = batch;
    }

    public InfoEmitter getInfoEmitter() {
        return infoEmitter;
    }

    public King getKing() {
        return king;
    }

    @Override
    public void show() {
        this.player = new Player();
        this.mousePosition = new Vector2(0, 0);
        this.particleEmitter = new ParticleEmitter();
        this.font18 = Assets.getInstance().getAssetManager().get("fonts/zorque18.ttf");
        this.font24 = Assets.getInstance().getAssetManager().get("fonts/zorque24.ttf");
        this.font134 = Assets.getInstance().getAssetManager().get("fonts/zorque134.ttf");
        this.bulletEmitter = new BulletEmitter(this);
        this.map = new Map("level01.map");
        this.monsterEmitter = new MonsterEmitter(this);
        this.turretEmitter = new TurretEmitter(this);
        this.infoEmitter = new InfoEmitter(this);
        this.selectedCellTexture = Assets.getInstance().getAtlas().findRegion("cursor");
        this.king = new King(this);
        this.layout = new GlyphLayout(font134, gameoverText);
        this.createGUI();
    }

    public void createGUI() {
        stage = new Stage(ScreenManager.getInstance().getViewport(), batch);

        InputProcessor myProc = new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                mousePosition.set(screenX, screenY);
                ScreenManager.getInstance().getViewport().unproject(mousePosition);
//                if (selectedCellX == (int) (mousePosition.x / 80) && selectedCellY == (int) (mousePosition.y / 80)) {
//                    map.setWall((int) (mousePosition.x / 80), (int) (mousePosition.y / 80));
//                }

                selectedCellX = (int) (mousePosition.x / 80);
                selectedCellY = (int) (mousePosition.y / 80);

                groupTurretAction.setPosition(selectedCellX * 80 - 20, selectedCellY * 80 + 60);
                groupTurretSelection.setPosition(selectedCellX * 80 - 20, selectedCellY * 80 - 20);

                if (!map.isCellEmpty(selectedCellX, selectedCellY)) {
                    groupTurretSelection.setVisible(false);
                    groupTurretAction.setVisible(false);
                }

                if (turretEmitter.findTurretInCell(selectedCellX, selectedCellY) == null && map.isCellEmpty(selectedCellX, selectedCellY)) {
                    groupTurretAction.setVisible(false);
                    groupTurretSelection.setVisible(true);
                }

                if (turretEmitter.findTurretInCell(selectedCellX, selectedCellY) != null) {
                    groupTurretSelection.setVisible(false);
                    groupTurretAction.setVisible(true);
                }

                return true;
            }
        };

        InputMultiplexer inputMultiplexer = new InputMultiplexer(stage, myProc);
        Gdx.input.setInputProcessor(inputMultiplexer);

        Skin skin = new Skin();
        skin.addRegions(Assets.getInstance().getAtlas());

        TextButton.TextButtonStyle setrebturretButton = new TextButton.TextButtonStyle();
        TextButton.TextButtonStyle setblueturretButton = new TextButton.TextButtonStyle();
        TextButton.TextButtonStyle upgradeturretButton = new TextButton.TextButtonStyle();
        TextButton.TextButtonStyle destroyturretButton = new TextButton.TextButtonStyle();

        setrebturretButton.up = skin.getDrawable("setredturret");
        setblueturretButton.up = skin.getDrawable("setblueturret");
        upgradeturretButton.up = skin.getDrawable("upgradeturret");
        destroyturretButton.up = skin.getDrawable("destroyturret");

        setrebturretButton.font = font18;
        setblueturretButton.font = font18;
        upgradeturretButton.font = font18;
        destroyturretButton.font = font18;

        skin.add("setredturretskin", setrebturretButton);
        skin.add("setblurturretskin", setblueturretButton);
        skin.add("upgradeturretskin", upgradeturretButton);
        skin.add("destroyturretskin", destroyturretButton);

        groupGameOver = new Group();
        groupGameOver.setVisible(false);
        Pixmap pixmap = new Pixmap(1,1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.BLACK);
        pixmap.fillRectangle(0, 0, 1, 1);
        Texture fillTexture=new Texture(pixmap);
        pixmap.dispose();
        Image fillImage=new Image(fillTexture);
        fillImage.setSize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        fillImage.getColor().a=.7f;
        groupGameOver.addActor(fillImage);

        groupTurretAction = new Group();
        groupTurretAction.setVisible(false);
        Button btnUpgradeTurret = new TextButton("", skin, "upgradeturretskin");
        Button btnDestroyTurret = new TextButton("", skin, "destroyturretskin");
        btnUpgradeTurret.setPosition(0, 0);
        btnDestroyTurret.setPosition(80, 0);
        groupTurretAction.addActor(btnUpgradeTurret);
        groupTurretAction.addActor(btnDestroyTurret);

        groupTurretSelection = new Group();
        groupTurretSelection.setVisible(false);
        Button btnSetTurret1 = new TextButton("", skin, "setredturretskin");
        Button btnSetTurret2 = new TextButton("", skin, "setblurturretskin");
        btnSetTurret1.setPosition(0, 0);
        btnSetTurret2.setPosition(80, 0);
        groupTurretSelection.addActor(btnSetTurret1);
        groupTurretSelection.addActor(btnSetTurret2);

        btnSetTurret1.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (turretEmitter.buildTurret(player, "Red-Turret-I", selectedCellX, selectedCellY)) {
                    groupTurretSelection.setVisible(false);
                    groupTurretAction.setVisible(true);
                }
            }
        });

        btnSetTurret2.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (turretEmitter.buildTurret(player, "Blue-Turret-I", selectedCellX, selectedCellY)) {
                    groupTurretSelection.setVisible(false);
                    groupTurretAction.setVisible(true);
                }
            }
        });

        btnDestroyTurret.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                turretEmitter.removeTurret(player, selectedCellX, selectedCellY);
                groupTurretAction.setVisible(false);
                groupTurretSelection.setVisible(true);
            }
        });

        btnUpgradeTurret.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                turretEmitter.upgradeTurret(player, selectedCellX, selectedCellY);
            }
        });

        stage.addActor(groupGameOver);
        stage.addActor(groupTurretSelection);
        stage.addActor(groupTurretAction);

        skin.dispose();
    }

    @Override
    public void render(float delta) {
        float dt = Gdx.graphics.getDeltaTime();
        update(dt);
        batch.begin();
        map.render(batch);

        batch.setColor(1, 1, 0, 0.5f);
        batch.draw(selectedCellTexture, selectedCellX * 80, selectedCellY * 80);
        batch.setColor(1, 1, 1, 1);
        king.render(batch, font24);
        monsterEmitter.render(batch);
        turretEmitter.render(batch);
        bulletEmitter.render(batch);
        particleEmitter.render(batch);
        player.renderInfo(batch, font18);
        infoEmitter.render(batch, font24);
        batch.end();

        stage.draw();

        if (king.wasKilled()) {
            batch.begin();
            font134.draw(batch, layout, (1280 - layout.width) / 2, (720 + layout.height) / 2);
            batch.end();
        }

    }

    public void update(float dt) {
        if (!king.wasKilled()) {
            map.update(dt);
            king.update(dt);
            monsterEmitter.update(dt);
            turretEmitter.update(dt);
            particleEmitter.update(dt);
            generateMonsters(dt);
            bulletEmitter.update(dt);
            checkCollisions();
            checkMonstersInCastle();
            infoEmitter.update(dt);

            monsterEmitter.checkPool();
            particleEmitter.checkPool();
            bulletEmitter.checkPool();
            turretEmitter.checkPool();
            infoEmitter.checkPool();

            stage.act(dt);
        } else {
            Gdx.input.setInputProcessor(stage);
            groupGameOver.setVisible(true);
            stage.act(dt);
            gameoverTimer += dt;
            if (gameoverTimer > 5.0f) {
                ScreenManager.getInstance().changeScreen(ScreenManager.ScreenType.MENU);
            }
        }
    }

    public void checkMonstersInCastle() {
        for (int i = 0; i < monsterEmitter.getActiveList().size(); i++) {
            Monster m = monsterEmitter.getActiveList().get(i);
            if ((int) (m.getPosition().x / 80) == king.getCellX() && (int) (m.getPosition().y / 80) == king.getCellY()) {
                king.takeDamage(5);
                infoEmitter.setup(king.getPosition().x, king.getPosition().y, "DMG 5");
                m.deactivate();
            }
        }
    }

    public void checkCollisions() {
        for (int i = 0; i < bulletEmitter.getActiveList().size(); i++) {
            Bullet b = bulletEmitter.getActiveList().get(i);
            if (b.getPosition().x < 0 || b.getPosition().x > 1280 || b.getPosition().y < 0 || b.getPosition().y > 720) {
                b.deactivate();
                continue;
            }
            if (!map.isCellEmpty((int) (b.getPosition().x / 80), (int) (b.getPosition().y / 80))) {
                b.deactivate();
                continue;
            }
            for (int j = 0; j < monsterEmitter.getActiveList().size(); j++) {
                Monster m = monsterEmitter.getActiveList().get(j);
                if (m.getHitArea().contains(b.getPosition())) {
                    b.deactivate();
                    if (m.takeDamage(b.getPower())) {
                        player.changeCoins(5);
                        particleEmitter.getEffectBuilder().buildMonsterSplash(m.getPosition().x, m.getPosition().y);
                    }
                }
            }
        }
    }

    public void generateMonsters(float dt) {
        monsterTimer += dt;
        if (monsterTimer > 0.5f) {
            monsterTimer = 0;
            monsterEmitter.setup(15, MathUtils.random(0, 8));
        }
    }

    @Override
    public void resize(int width, int height) {
        ScreenManager.getInstance().resize(width, height);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
    }
}
