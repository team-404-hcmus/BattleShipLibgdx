package com.team404.battleship;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
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
    public HomeScreen(){
        batch = new SpriteBatch();
        asset.getSkin();
        stage = new Stage(new ScreenViewport());

        SinglePlayer = new TextButton("Single Player",asset.getSkin());
        SinglePlayer.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                ((battleship)Gdx.app.getApplicationListener()).setScreen(new GameSetting());
            }
        });


        Multiplayer = new TextButton("Multi Player",asset.getSkin());
        Settingbtn = new TextButton("Setting",asset.getSkin());
        menuTable = new Table();
        menuTable.add(SinglePlayer).padBottom(50);
        menuTable.row();
        menuTable.add(Multiplayer).padBottom(50);
        menuTable.row();
        menuTable.add(Settingbtn).padBottom(50);
        menuTable.setX(Gdx.graphics.getWidth()/2f);
        menuTable.setY(Gdx.graphics.getHeight()/3f);
        menuTable.debug();
        stage.addActor(menuTable);
        img = asset.getBackground();
        Gdx.input.setInputProcessor(stage);
        ((battleship)Gdx.app.getApplicationListener()).m_brigde.Log("loading Screen");
    }
    @Override
    public void show() {

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

    }

    @Override
    public void dispose() {
        stage.dispose();
        batch.dispose();
    }
}
