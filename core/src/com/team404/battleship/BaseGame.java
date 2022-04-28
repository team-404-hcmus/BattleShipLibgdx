package com.team404.battleship;

public class BaseGame {
    boolean activePlayer;
    final Player p1;
    final Player p2;
    BaseGame(Player p1, Player p2){
        this.p1 = p1;
        this.p2 = p2;
        activePlayer = true;
    }
    boolean shoot(int x, int y){
        Player current = p1;
        if(!activePlayer)
        {
            current = p2;
        }
        boolean result = current.getShot(x,y);
        if(result){
            activePlayer = !activePlayer;
        }
        return result;
    }

}
