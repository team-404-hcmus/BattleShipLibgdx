package com.team404.battleship;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameSetting extends BaseScreen{

    private Texture empty_bg;
    private SpriteBatch batch;

    GameSetting() {
        batch = new SpriteBatch();
        empty_bg = new Texture("empty_bg.jpg");
        ((battleship)Gdx.app.getApplicationListener()).m_brigde.Log("loading Screen");

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        batch.begin();
        batch.draw(empty_bg,0,0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        batch.end();
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
}
