package com.dscalzi.lastcoingame;

public class Player implements User{

    private CoinGame game;

    public Player(CoinGame game){
        this.game = game;
    }

    @Override
    public void makeMove(int coinsTaken) {
        game.registerMove(coinsTaken, this);
    }

}
