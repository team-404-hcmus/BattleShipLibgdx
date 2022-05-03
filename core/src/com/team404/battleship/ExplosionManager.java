package com.team404.battleship;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Queue;

import java.util.ArrayList;
import java.util.Iterator;

public class ExplosionManager {

    private Array<effect> animationQueue;
    private TextureRegion[] animSheet;
    float state = 0f;
    ExplosionManager(TextureRegion[] animSheet){
        animationQueue = new Array<>();
        this.animSheet = animSheet;
    }

    class effect{
        final float x;
        final float y;
        final float width;
        final float height;
        float state =0f;
        Animation<TextureRegion> animator;
        effect(float _x, float _y, float w, float h, float duration, TextureRegion[] sheet) {
            x= _x;
            y= _y;
            width = w;
            height = h;
            animator = new Animation<>(duration,sheet);
        }
        public void render(SpriteBatch batch,float deltaT){
            TextureRegion region = animator.getKeyFrame(state,false);
            batch.draw(region,x,y,width,height);
            state+=deltaT;
        }
        boolean isFinish(){
            return animator.isAnimationFinished(state);
        }

    }


    public void spawnAt(float x, float y,float cellSize,float duration){
        animationQueue.add(new effect(x,y,cellSize,cellSize,duration,animSheet));
    }
    public void render(SpriteBatch batch,float deltaT){
        ArrayList<Integer> removingIndex = new ArrayList<>();
        batch.begin();
        for(int i = 0; i < animationQueue.size;i++)
        {
            if(animationQueue.get(i).isFinish())
            {
                removingIndex.add(i);
                continue;
            }
            animationQueue.get(i).render(batch,deltaT);
        }
        batch.end();
        for(Integer i: removingIndex)
        {
            animationQueue.removeIndex(i);
        }
        state+=deltaT;
    }


}
