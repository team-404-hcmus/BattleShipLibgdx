package com.team404.battleship;

import java.util.ArrayList;
import java.util.Random;

public class BaseGame {
    boolean activePlayer;
    final Player p1;
    final Player p2;
    final ArrayList<GameEvent> m_eventsHandler;
    BaseGame(Player p1, Player p2){
        this.p1 = p1;
        this.p2 = p2;
        activePlayer = true;
        m_eventsHandler = new ArrayList<>();
    }
    class ShootResult{
        public boolean result;
        public boolean m_hasShip;
    }
    ShootResult shoot(int x, int y){
        Player current = p1;
        if(activePlayer)
        {
            current = p2;
        }
        ShootResult result = new ShootResult();
        result.result = current.getShot(x,y);
        result.m_hasShip = current.getPlayerBoard().getCell(x,y).m_hasShip;
        if(result.result){
            activePlayer = !activePlayer;
        }
        return result;
    }

    boolean end(){
        return p1.isAllSunk() ||
                p2.isAllSunk();
    }

    void RegisterPlayer(boolean[][] i_shipBoard){

    }

    void AddListener(GameEvent event){
        m_eventsHandler.add(event);
    }

    void next(){
        Random rand = new Random();
        int randX = rand.nextInt(10);
        int randY = rand.nextInt(10);
        while(!shoot(randX,randY).result)
        {
            randX = rand.nextInt(10);
            randY = rand.nextInt(10);
        }
        for(GameEvent e : m_eventsHandler)
        {
            e.GetShoot(randX,randY);
        }
    }

}
