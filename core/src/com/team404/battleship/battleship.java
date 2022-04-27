package com.team404.battleship;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class battleship extends Game {
	AppAsset appAsset;
	public JavaAndroidBrigde m_brigde;
	battleship(JavaAndroidBrigde brigde)
	{
		m_brigde = brigde;

	}

	@Override
	public void create() {
		appAsset = AppAsset.getInstance();
		setScreen(new HomeScreen());
	}

	@Override
	public void dispose() {
		super.dispose();
		appAsset.DisposeAll();
	}

	@Override
	public void render() {
		super.render();
	}
}
