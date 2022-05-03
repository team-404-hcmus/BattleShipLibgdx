package com.team404.battleship;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class Rocket extends Actor {

    private TextureRegionDrawable renderTxt;
    private TextureRegion[] frames;
    private Animation<TextureRegion> animation;
    float state = 0f;
    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        renderTxt.draw(batch,getX(),getY(),getWidth()/2,getHeight()/2,getWidth(),getHeight(),1,1,getRotation()-90);
    }


    public Rocket(Texture Atlas,float width,float height){
        final int cols = 12;
        final int rows = 1;
        TextureRegion[][] tmp = TextureRegion.split(Atlas,
                Atlas.getWidth() / cols,
                Atlas.getHeight() / rows);

        frames = new TextureRegion[rows*cols];
        int index = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                frames[index++] = tmp[i][j];
            }
        }
        animation = new Animation<>(0.01f,frames);
        renderTxt = new TextureRegionDrawable();
        setWidth(width);
        setHeight(height);
    }

    @Override
    public void act(float delta) {
        renderTxt.setRegion(animation.getKeyFrame(state));
        state+=delta;
        super.act(delta);

    }
}
