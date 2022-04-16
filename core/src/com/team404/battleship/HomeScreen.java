package com.team404.battleship;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
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
                ((battleship)Gdx.app.getApplicationListener()).setScreen(new GameSetting());
                super.touchUp(event, x, y, pointer, button);
            }
        });
        final int padding = 20;

        Multiplayer = new TextButton("Multi Player",asset.getSkin());
        Settingbtn = new TextButton("Setting",asset.getSkin());
        menuTable = new Table();

        menuTable.add(SinglePlayer).padBottom(padding);
        menuTable.row();
        menuTable.add(Multiplayer).padBottom(padding);
        menuTable.row();
        menuTable.add(Settingbtn).padBottom(padding);
        menuTable.setX(Gdx.graphics.getWidth()/2f);
        menuTable.setY(Gdx.graphics.getHeight()/4f);


        menuTable.debug();
        Ship k = new Ship(asset.getShip5txt(), 0,0);

        DragAndDrop dnd = new DragAndDrop();
        dnd.setDragActorPosition(k.getWidth(),0);
        dnd.addSource(new DragAndDrop.Source(k) {
            @Override
            public DragAndDrop.Payload dragStart(InputEvent event, float x, float y, int pointer) {
                DragAndDrop.Payload payload = new DragAndDrop.Payload();
                payload.setObject("Some payload!");

                payload.setDragActor(getActor());



                return payload;
            }
        });
        dnd.setDragActorPosition(k.getWidth()/2,-k.getHeight()/2);
        dnd.addTarget(new DragAndDrop.Target(menuTable){
            @Override
            public boolean drag(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
                return true;
            }

            @Override
            public void drop(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
                Actor at = payload.getDragActor();
                Actor thisActor = getActor();


                Vector2 pos = new Vector2(thisActor.getWidth(),thisActor.getHeight()/2);
                thisActor.localToStageCoordinates(pos);
                at.setPosition(pos.x,pos.y);
            }
        });
        stage.addActor(menuTable);
        stage.addActor(k);
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
