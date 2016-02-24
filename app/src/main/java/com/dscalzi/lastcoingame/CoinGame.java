package com.dscalzi.lastcoingame;

public class CoinGame{

    //Data
    private int coinsLeft;
    private boolean goingFirst;
    private int difficulty; //0 Easy | 1 Medium | 2 Hard
    private transient MainActivity app;
    private boolean connected;

    //Users
    private AI ai;
    private Player player;

    public CoinGame(int totalCoins, boolean goingFirst, int difficulty){
        this.coinsLeft = totalCoins;
        this.goingFirst = goingFirst;
        this.difficulty = difficulty;
        this.connected = false;
        initUsers();
    }

    public void connectApp(MainActivity app){
        this.app = app;
        this.connected = true;
    }

    private void initUsers(){
        this.ai = new AI(difficulty, this);
        this.player = new Player(this);
    }

    public void registerMove(int coinsTaken, User user){
        if(!this.connected)
            return;
        this.coinsLeft = this.coinsLeft - coinsTaken;
        app.printMove(coinsLeft, coinsTaken, user);
    }

    public boolean isOver(){
        return coinsLeft <= 0;
    }

    public static int parseDifficulty(String s){
        return s.equalsIgnoreCase("Easy") ? 0 : (s.equalsIgnoreCase("Medium") ? 1 : (s.equalsIgnoreCase("Hard") ? 2 : -1));
    }

    public AI getAI(){
        return this.ai;
    }

    public Player getPlayer(){
        return this.player;
    }

    public MainActivity getApp() { return this.app; }

    public int getCoinsLeft(){
        return this.coinsLeft;
    }

    public boolean isGoingFirst(){
        return this.goingFirst;
    }

}
