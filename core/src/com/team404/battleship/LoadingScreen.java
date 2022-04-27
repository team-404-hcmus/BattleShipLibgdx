package com.team404.battleship;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;

public class LoadingScreen<T extends BaseScreen> implements Screen{

    private AppAsset asset;
    private T screen;
    LoadingScreen(T nextScreen){
        asset = AppAsset.getInstance();
        screen = nextScreen;
    }
    @Override
    public void show() {
        screen.createData();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if(screen.load())
        {
            ((Game)Gdx.app.getApplicationListener()).setScreen(screen);
        }
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
