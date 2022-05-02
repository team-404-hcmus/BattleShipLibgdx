package com.team404.battleship;


import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ReflectionPool;

import java.util.ArrayList;

public class Grid extends Table {
    private final float cellSize;
    protected final int rows;
    protected final int cols;
    protected ArrayList<Ship> m_Ships;
    public Grid(float CellSize, Skin skin, int rowCount, int colCount){
        m_Ships = new ArrayList<>();
        this.cellSize = CellSize;
        this.rows = rowCount;
        this.cols = colCount;
        for(int y= 0; y < colCount; ++y)
        {
            for (int x =0 ; x < rowCount; ++x)
            {
                ImageButton img = new ImageButton(skin);
                add(img).size(CellSize);
            }
            row();
        }
        initListener();
    }

    public void initListener(){
        layout();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }


    void gridSnap(Ship ship,float x,float y)
    {
        Vector2 relativePos = stageToLocalCoordinates(new Vector2(x,y));

        final float cellWidth = ship.getWidth()/cellSize;
        final float cellHeight = ship.getHeight()/cellSize;
        relativePos.x += cellSize*(rows/2f);
        relativePos.y += cellSize*(cols/2f);
        relativePos.x /=cellSize;
        relativePos.y /=cellSize;

        relativePos.x = Math.max(Math.min(Math.round(relativePos.x),10f-cellWidth),0f);
        relativePos.y = Math.max(Math.min(Math.round(relativePos.y),10f-cellHeight),0f);
        ship.setBoardIndex((int)relativePos.x,(int)relativePos.y);
        Vector2 unit = new Vector2(1,1);
        unit.scl(rows/2);
        relativePos.sub(unit);
        relativePos.scl(cellSize);
        Vector2 pos = localToStageCoordinates(relativePos);
        ship.setPosition(pos.x,pos.y);

    }


    public boolean isSnappable(Ship ship){
        Rectangle bound = ship.getBounds();
        for(Ship _ship : m_Ships)
        {
            if(_ship != ship && _ship.getBounds().overlaps(bound))
            {
                return false;
            }
        }
        return true;

    }

    public void addShip(Ship ship){
        m_Ships.add(ship);

    }

    public ArrayList<Ship> getShipsList(){
        return m_Ships;
    }
}
