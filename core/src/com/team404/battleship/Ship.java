package com.team404.battleship;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Null;

import org.w3c.dom.Text;

import jdk.javadoc.internal.doclets.formats.html.markup.Table;


public class Ship extends Actor {
    private final Texture hTxt;
    private final Texture vTxt;
    private TextureRegionDrawable renderTxt;
    private boolean isVertical = false;
    private final float cellSize;
    private final float totalSize;
    private final int shipSize;
    protected Float ogX = null;
    protected Float ogY = null;
    private DragAndDrop.Target target;
    private final DragAndDrop dnd;
    private int m_boardX;
    private int m_boardY;
    Rectangle bounds;
    public Rectangle getBounds() {
        return bounds;
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        bounds.setX((int)x);
        bounds.setY((int)y);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        renderTxt.draw(batch,getX(),getY(),getWidth()/2,getHeight()/2,getWidth(),getHeight(),1,1,getRotation());
    }


    public Ship(Texture txHorizon,Texture  txVertical ,float cellSize,int shipSize,boolean isClickable){
        this(txHorizon, txVertical, cellSize, shipSize);
        if(!isClickable)
        {
            this.setTouchable(Touchable.childrenOnly);
        }

    }
    public Ship(Texture txHorizon,Texture  txVertical ,float cellSize,int shipSize){
        this.vTxt = txVertical;
        this.hTxt = txHorizon;
        this.cellSize = cellSize;
        this.totalSize = cellSize*shipSize;
        this.shipSize = shipSize;
        this.bounds = new Rectangle();
        SetShipHorizon();
        dnd = new DragAndDrop();
        dnd.setDragActorPosition(getWidth()/2,-getHeight()/2);
    }

    private void SetShipVertical(){
        setBounds(0, 0, cellSize, totalSize);
        this.renderTxt = new TextureRegionDrawable(new TextureRegion(vTxt));
        this.bounds.setSize(cellSize,totalSize);
    }

    private void SetShipHorizon(){
        setBounds(0, 0, totalSize, cellSize);
        this.renderTxt = new TextureRegionDrawable(new TextureRegion(hTxt));
        this.bounds.setSize(totalSize,cellSize);
    }

    public void toggleOrientation(){
        float x = getX();
        float y = getY();
        if(isVertical)
        {
            SetShipHorizon();
            isVertical = false;
        }
        else{
            SetShipVertical();
            isVertical = true;
        }
        final float coef = isVertical ? 1 :-1;
        setPosition(x+coef*(totalSize/2-cellSize/2),y-coef*(totalSize/2-cellSize/2));
        dnd.setDragActorPosition(getWidth()/2,-getHeight()/2);
    }

    public void AddTarget(DragAndDrop.Target T){
        dnd.addTarget(T);
        target = T;
        dnd.addSource(new DragAndDrop.Source(this) {
            @Override
            public DragAndDrop.Payload dragStart(InputEvent event, float x, float y, int pointer) {
                DragAndDrop.Payload pl = new DragAndDrop.Payload();
                pl.setDragActor(this.getActor());
                if(ogX == null || ogY == null)
                {
                    ogX = getX();
                    ogY = getY();
                }
                return pl;
            }

            @Override
            public void dragStop(InputEvent event, float x, float y, int pointer, DragAndDrop.Payload payload, DragAndDrop.Target target) {
                if(target == null)
                {
                    returnToOrigin();
                }

            }
        });
    }

    public void returnToOrigin(){
        this.setPosition(ogX,ogY);
    }

    public int getSize(){
        return this.shipSize;
    }


    public void newOrigin(){
            ogX = getX();
            ogY = getY();
    }

    protected DragAndDrop.Target getTarget(){
        return this.target;
    }
    protected boolean getOrientation(){
        return isVertical;
    }
    protected void moveData(float x, float y, int size, boolean ort){
        if(size != this.shipSize) return;
        if(ort != this.isVertical)
        {
            toggleOrientation();
        }
        this.setPosition(x, y);

    }

    void setBoardIndex(int x, int y){
        m_boardX =x;
        m_boardY =y;

    }
    int getBoardIndexX(){
        return m_boardX;
    }
    int getBoardIndexY(){
        return m_boardY;
    }

}
