package com.team404.battleship;
import com.badlogic.gdx.Gdx;
public class LoadingScreen extends BaseScreen{

    LoadingScreen(){

    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if(asset.load())
        {
            ((battleship)Gdx.app.getApplicationListener()).setScreen(new HomeScreen());
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
