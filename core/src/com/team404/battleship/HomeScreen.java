package com.team404.battleship;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;


public class HomeScreen extends BaseScreen {

    private Stage stage;
    private Skin skin;
    private TextButton SinglePlayer;
    private TextButton Multiplayer;
    private TextButton Settingbtn;
    private Table menuTable;
    private SpriteBatch batch;
    private Texture img;
    private HomeScreenPayLoad hsData;

    public HomeScreen(){
        super(null);

    }
    @Override
    public void show() {
        batch = new SpriteBatch();
        asset.getSkin();
        stage = new Stage(new ScreenViewport());

        SinglePlayer = new TextButton("Single Player",asset.getSkin());
        SinglePlayer.addListener(new InputListener(){
            @Override
            public boolean handle(Event e) {
                return super.handle(e);
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                ((battleship)Gdx.app.getApplicationListener()).setScreen(new LoadingScreen(new PlanningScreen()));
                super.touchUp(event, x, y, pointer, button);
            }
        });
        final int padding = 20;

        Multiplayer = new TextButton("Multi Player",asset.getSkin());
        Settingbtn = new TextButton("Setting",asset.getSkin());
        menuTable = new Table();

        menuTable.add(SinglePlayer).padBottom(padding).size(500,200);
        menuTable.row();
        menuTable.add(Multiplayer).padBottom(padding).size(500,200);
        menuTable.row();
        menuTable.add(Settingbtn).padBottom(padding).size(500,200);
        menuTable.setX(Gdx.graphics.getWidth()/2f);
        menuTable.setY(Gdx.graphics.getHeight()/3.5f);


        menuTable.debug();

        stage.addActor(menuTable);
        img = asset.getBackground();
        Gdx.input.setInputProcessor(stage);
        ((battleship)Gdx.app.getApplicationListener()).m_brigde.Log("loading Screen");
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        batch.begin();
        batch.draw(img,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        batch.end();
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        stage.dispose();
        batch.dispose();
    }

    @Override
    public void dispose() {

    }

    @Override
    void createData() {
        hsData = new HomeScreenPayLoad();
        this.data = hsData;
    }

    public class HomeScreenPayLoad implements ILoadable{

        @Override
        public boolean load() {
            return true;

        }
    }
}
