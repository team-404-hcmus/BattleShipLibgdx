package com.team404.battleship;

public class Semaphore {
    int action = 0;


    Semaphore(int limit){
        action = limit;
    }


    public void increase(){
        action +=1;
    }
    public void decrease(){
        while(action <= 0){
            ;;
        }
        action -=1;
    }
}
