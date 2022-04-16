package com.team404.battleship;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;

import org.w3c.dom.Text;


public class Ship extends Actor {
    private final Texture texture;
    private float MouseX = 0f;
    private float MouseY = 0f;
    private  int shipSize;
    private final float cellSize;
    private final float totalSize;
    boolean isDrag = false;


    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(this.texture,
                getX(),
                getY(),totalSize,cellSize);
    }


    public Ship(Texture tx,float cellSize,int shipSize){
        this.texture = tx;
        this.shipSize = shipSize;
        this.cellSize = cellSize;
        this.totalSize = cellSize*shipSize;
        setBounds(0, 0, totalSize, cellSize);
    }

    @Override
    public float getX(){
        if(isDrag())
        {
            return MouseX;
        }
        return super.getX();
    }
    @Override
    public float getY(){
        if(isDrag())
        {
            return MouseY;
        }
        return super.getY();
    }
    public boolean isDrag(){
        return isDrag;
    }
    public void setDrag(boolean d)
    {
        isDrag =d;
    }
    private void setMouse(float x, float y)
    {
        this.MouseX =x;
        this.MouseY = y;
    }

}
