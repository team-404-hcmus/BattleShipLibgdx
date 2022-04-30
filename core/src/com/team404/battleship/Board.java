package com.team404.battleship;

import com.badlogic.gdx.math.Vector2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Locale;

public class Board {
    private final int m_size;

    class Cell{
        boolean m_hasShip;
        boolean m_isHit;
    }
    private Cell[][] m_board = null;
    private final int placesShot=0;

    public Board(int size) {
        m_size = size;
        m_board = new Cell[size][size];
        for(int x =0; x < size;x ++)
        {
            for(int y =0; y < size;y++)
            {
                Cell cell = new Cell();
                cell.m_hasShip = false;
                cell.m_isHit = false;
                m_board[x][y] = cell;
            }
        }
    }
    void placeShip(ActionScene.ShipData ship){
        int deltaX = ship.Orientation ?0:1;
        int deltaY = ship.Orientation ?1:0;
        for(int i = 0; i < ship.size;i++)
        {
            int x = ship.logicX+deltaX;
            int y = ship.logicY+deltaY;
            m_board[x][y].m_hasShip = true;
        }

    }
    boolean isInBound(int x,int y)
    {
        return (0 <= x && x < m_size) && (0 <= y && y < m_size);
    }

    /**Shooting  Function **/
    boolean shoot(int i_x, int i_y){
        if(!isInBound(i_x,i_y))
        {
            String msg = String.format(Locale.ENGLISH,
                    "x: %d \n" +
                    "y:%d\n" +
                    "is not inbound with %d",i_x,i_y,m_size);
            throw new IndexOutOfBoundsException(msg);

        }
        Cell cell = m_board[i_x][i_y];
        if(cell.m_isHit) {
            return false;
        }
        cell.m_isHit = true;
        return true;
    }

    // is board over
    public boolean isOver() {
        for(int x =0; x < m_size;x ++)
        {
            for(int y =0; y < m_size;y++)
            {
                Cell cell =  m_board[x][y];
                if(cell.m_hasShip )
                {
                    if(!cell.m_isHit)
                    {
                        return false;
                    }

                }
            }
        }
        return true;
    }

    Cell getCell(int x,int y)
    {
        return m_board[x][y];
    }
}
