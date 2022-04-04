package com.team404.battleship;

import android.support.annotation.CallSuper;

import com.badlogic.gdx.Screen;

public abstract class BaseScreen implements Screen {
    protected AppAsset asset;
    BaseScreen()
    {
        asset = AppAsset.getInstance();
    }

}
