package com.team404.battleship;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class battleship extends Game {
	AppAsset appAsset;

	@Override
	public void create() {
		appAsset = AppAsset.getInstance();
	}

	@Override
	public void dispose() {
		super.dispose();
		appAsset.DisposeAll();
	}

	@Override
	public void render() {
		super.render();
		if(appAsset.load())
		{
			setScreen(new HomeScreen(this));
		}

	}

}
