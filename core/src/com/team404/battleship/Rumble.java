package com.team404.battleship;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;


public class Rumble extends SequenceAction {
    private float ogX;
    private float ogY;
    private boolean began = false;
    private MoveByAction shake;
    private MoveByAction shake1;
    private MoveByAction shake2;
    private MoveToAction moveBack;
    public Rumble()
    {

        shake = new MoveByAction();
        shake1 = new MoveByAction();
        shake2 = new MoveByAction();
        moveBack = new MoveToAction();
        addAction(shake);
        addAction(shake1);
        addAction(shake2);
        addAction(moveBack);
    }

    @Override
    public boolean act(float delta) {
        if(!began)
        {
            begin();
        }
        return super.act(delta);
    }
    private void begin(){
        ogX = target.getX();
        ogY = target.getY();
        moveBack.setPosition(ogX,ogY);
        moveBack.setDuration(.2f);
        shake.setDuration(0.05f);
        shake.setAmount(5,0);

        shake1.setDuration(0.05f);
        shake1.setAmount(-5,0);

        shake2.setDuration(0.05f);
        shake2.setAmount(-5,0);
        began = true;
    }
}
