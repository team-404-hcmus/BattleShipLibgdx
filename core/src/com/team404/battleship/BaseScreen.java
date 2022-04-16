package com.team404.battleship;

import android.support.annotation.CallSuper;

import com.badlogic.gdx.Screen;

public abstract class BaseScreen implements Screen, ILoadable {
    protected AppAsset asset;

    ILoadable data;
    BaseScreen()
    {
        asset = AppAsset.getInstance();
    }
    abstract void createData();
    @Override
    public boolean load(){
        return data.load();
    }

}
