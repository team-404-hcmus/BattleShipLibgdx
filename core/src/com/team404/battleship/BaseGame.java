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
    boolean shoot(int x, int y){
        Player current = p1;
        if(activePlayer)
        {
            current = p2;
        }
        boolean result = current.getShot(x,y);
        if(result){
            activePlayer = !activePlayer;
        }
        return result;
    }

    boolean end(){
        return p1.isAllSunk() || p2.isAllSunk();
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
        shoot(randX,randY);
        for(GameEvent e : m_eventsHandler)
        {
            e.GetShoot(randX,randY);
        }
    }

}
