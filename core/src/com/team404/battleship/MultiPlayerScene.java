package com.team404.battleship;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;


public class MultiPlayerScene extends BaseScreen{

    private SpriteBatch batch;
    private Stage stage;

    public class MultiplayerSceneData implements ILoadable{

        @Override
        public boolean load() {
            return true;

        }
    }

    MultiPlayerScene() {
        super(null);
    }

    @Override
    void createData() {
        this.data = new MultiplayerSceneData();
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        stage = new Stage();
        Table tbl = new Table();
        tbl.setFillParent(true);
        TextButton btn1 = new TextButton("Join", asset.getSkin());
        TextButton btn2 = new TextButton("Open Setting", asset.getSkin());
        btn1.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((battleship)Gdx.app.getApplicationListener()).m_brigde.DeCreaseVolume();
            }
        });
        btn2.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((battleship)Gdx.app.getApplicationListener()).m_brigde.InCreaseVolume();
            }
        });
        tbl.top().pad(300f);
        tbl.add(btn1).width(500f);
        tbl.add(btn2).width(500f);
        stage.addActor(tbl);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        batch.begin();
        batch.draw(asset.getBackground(),0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
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

    }
}
