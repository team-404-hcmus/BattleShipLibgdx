package com.team404.battleship;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Player implements Serializable {
    private Board playerBoard = null;
    private ArrayList<ActionScene.ShipData> fleet;
    //Getter

    public Board getPlayerBoard() {
        return playerBoard;
    }

    //Create player
    Player(ArrayList<ActionScene.ShipData> ships) {
        fleet = ships;
        playerBoard = new Board(10);

        if(fleet != null)
        {
            for(ActionScene.ShipData s : fleet)
            {
                playerBoard.placeShip(s);
            }
        }
    }
    Player(Board board)
    {
        playerBoard = board;
    }
    //add, create board, ship
    public void setBoard(Board newBoard){
        this.playerBoard=newBoard;
    }


    //place ship randomly (only for debug)
//    private void placeShipRandomly(Board board,Ship ship) {
//        Random rng = new Random();
//        boolean dir = rng.nextBoolean();
//        // if dir is true then ship will be placed horizontally
//
//        int[] maxCoordinates = findMaxLocation(board.getSize(), ship.getSize(), dir);
//
//        //Then can't place ship
//        if (maxCoordinates == null) {
//            return ;
//        }
//
//        int maxX = maxCoordinates[0];
//        int maxY = maxCoordinates[1];
//
//        boolean placedShip = false;
//
//        while (!placedShip) {
//
//            int x = rng.nextInt(maxX);
//            int y = rng.nextInt(maxY);
//
//            //if was able to place ship on board
//            if (board.placeShip(ship, x, y, dir)) {
//                placedShip = true;
//            }
//        }
//
//        this.fleet.add(ship);
//    }
    private int[] findMaxLocation(int boardSize, int shipSize, boolean dir) {

        int maxX = boardSize;
        int maxY = boardSize;
        if (dir) {
            maxX = boardSize - shipSize;
        } else {
            maxY = boardSize - shipSize;
        }

        if (maxX < 0 || maxY < 0) {
            return null;
        }

        return new int[]{maxX, maxY};
    }
    //get shoot
    public boolean getShot(int x, int y)
    {
        return this.playerBoard.shoot(x,y);
    }
    //checking win-lose condition
    public boolean isAllSunk()
    {
        return this.playerBoard.isOver();
    }
}
