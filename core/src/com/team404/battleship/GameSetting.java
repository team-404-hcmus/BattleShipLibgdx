package com.team404.battleship;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Pool;

public class GameSetting extends BaseScreen{

    private Texture empty_bg;
    private SpriteBatch batch;
    private Stage stage;
    private Table cellTable;
    private final float cellSize;
    private Texture[] ship = new Texture[5];

    GameSetting() {

        cellSize = (Gdx.graphics.getHeight() - 100)/10;
        batch = new SpriteBatch();
        empty_bg = new Texture("empty_bg.jpg");
        stage = new Stage();
        cellTable = new Table();
        cellTable.setFillParent(true);
        ActionsFactory actionsFactory = ActionsFactory.getInstance();
        final MoveToAction moveToAction = (MoveToAction)actionsFactory.obtain(MoveToAction.class);
        moveToAction.setPosition(100,500);
        moveToAction.setDuration(5);
        for(int i = 0; i < 10; i++)
        {
            for(int k = 0;k < 10; k++)
            {
                ImageButton img = new ImageButton(asset.getSkin());
                img.setTransform(true);
                img.addListener(new InputListener(){
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        return true;
                    }

                    @Override
                    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                        super.touchUp(event, x, y, pointer, button);
                        cellTable.addAction(moveToAction);
                    }
                });
                cellTable.add(img).size(cellSize);

            }
            cellTable.row();
        }
        stage.addActor(cellTable);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        drawBackGround(delta);

        stage.draw();
        stage.act(delta);

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
        empty_bg.dispose();
    }

    private void drawBackGround(float delta){
        batch.begin();
        batch.draw(empty_bg,0,0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        batch.end();
    }
}
