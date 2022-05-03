package com.team404.battleship;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.graphics.Texture;

public class AppAsset {
    private static AppAsset App_Asset = null;
    private BitmapFont font;
    private AssetManager m_AssetManager;

    //Explosion sound
    public Sound sound;
    public Sound bg_sfx;
    private AppAsset(){
        m_AssetManager = new AssetManager();
        GenerateFont();
        ObjectMap<String, Object> fontMap = new ObjectMap<>();
        fontMap.put("font", font);
        SkinLoader.SkinParameter parameter = new SkinLoader.SkinParameter(fontMap);

        /* Load the skin as usual */
        m_AssetManager.load("skin/uiskin.json", Skin.class, parameter);
        m_AssetManager.load("empty_bg.jpg",Texture.class);
        m_AssetManager.finishLoading();
        sound = Gdx.audio.newSound(Gdx.files.internal("sound/explosion.mp3"));
        bg_sfx = Gdx.audio.newSound(Gdx.files.internal("sound/background.mp3"));
    }

    static public AppAsset getInstance()
    {
        if(App_Asset == null)
        {
            App_Asset = new AppAsset();
        }
        return App_Asset;
    }

    public void DisposeAll(){
        m_AssetManager.dispose();
    }

    private void GenerateFont(){
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Lobster_1.3.otf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 50;
        parameter.color = Color.BLACK;
        font = generator.generateFont(parameter);
        generator.dispose();
    }
    public boolean load(){
        return m_AssetManager.update();
    }
    public Skin getSkin(){
        return m_AssetManager.get("skin/uiskin.json",Skin.class);
    }
    public Texture getBackground(){
        return m_AssetManager.get("empty_bg.jpg");
    }
    public boolean isFinish(){return m_AssetManager.isFinished();}

}
