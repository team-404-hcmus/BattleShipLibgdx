package com.team404.battleship;

import android.support.annotation.CallSuper;

import com.badlogic.gdx.Screen;

public abstract class BaseScreen implements Screen {
    protected AppAsset asset;
    protected battleship m_battleship;

    BaseScreen(battleship bs)
    {
        asset = AppAsset.getInstance();
        m_battleship = bs;
    }

}
