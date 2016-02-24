package com.dscalzi.lastcoingame;

/**
 * Created by Daniel on 2/21/2016.
 */
public class Player extends User{

    private CoinGame game;

    public Player(CoinGame game){
        this.game = game;
    }

    @Override
    public void makeMove(int coinsTaken) {
        game.registerMove(coinsTaken, this);
    }

}
