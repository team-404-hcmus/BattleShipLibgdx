package com.team404.battleship;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.*;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import sun.font.TrueTypeFont;

public class MyGdxGame extends Game {
	SpriteBatch batch;
	Texture img;
	Button btn;
	private Stage stage;
	private Table table;
	FreeTypeFontGenerator generator;
	FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
	Pixmap bgPixmap;
	BitmapFont font12; // font size 12 pixels
	Skin skin;



	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/PilotCommand-3zn93.otf"));
		stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);
		parameter.size = 70;
		font12 = generator.generateFont(parameter);
		TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
		style.font = font12;
		skin = new Skin(Gdx.files.internal("data/uiskin.json"));
		skin.getFont("PilotCommand-3zn93").getData().setScale(3f);

		Button btn = new TextButton("test",skin);
		bgPixmap = new Pixmap(1,1, Pixmap.Format.RGB565);
		bgPixmap.setColor(Color.GREEN);
		bgPixmap.fill();
		TextureRegionDrawable textureRegionDrawableBg = new TextureRegionDrawable(new TextureRegion(new Texture(bgPixmap)));
		btn.setBackground(textureRegionDrawableBg);
		btn.scaleBy(10f,5f);
//		btn.setWidth(400);
//		btn.setHeight(200);

		stage.addActor(btn);
	}

	@Override
	public void render () {
		ScreenUtils.clear(1, 1, 1, 1);
		stage.act(Gdx.graphics.getDeltaTime());
		batch.begin();
//		batch.draw(img, 0, 0);
		stage.draw();
		batch.end();
	}



	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
		generator.dispose();
		bgPixmap.dispose();
	}
}
