package com.dscalzi.lastcoingame;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity{

    private TextView remainingLbl;
    private TextView lastMoveLbl;
    private TextView trashTalkLbl;
    private Button btnTakeOne;
    private Button btnTakeTwo;

    private CoinGame game;
    private AI ai;
    private Player player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.remainingLbl = (TextView) findViewById(R.id.coinsLeftTextView);
        this.lastMoveLbl = (TextView) findViewById(R.id.lastMoveTextView);
        this.trashTalkLbl = (TextView) findViewById(R.id.trashTalkTextView);
        this.btnTakeOne = (Button) findViewById(R.id.btnTakeOne);
        this.btnTakeTwo = (Button) findViewById(R.id.btnTakeTwo);
        Button btnReset = (Button) findViewById(R.id.btnReset);

        this.initGame();

        if(!game.isGoingFirst()) {
            ai.makeMove(game.getCoinsLeft());
            checkWin(ai);
        }


        btnTakeOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                player.makeMove(1);
                checkWin(player);
                if(!game.isOver()) {
                    ai.makeMove(game.getCoinsLeft());
                    checkWin(ai);
                }
            }
        });

        btnTakeTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                player.makeMove(2);
                checkWin(player);
                if(!game.isOver()) {
                    ai.makeMove(game.getCoinsLeft());
                    checkWin(ai);
                }
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent myIntent = new Intent(MainActivity.this, SettingsActivity.class);
                finish();
                MainActivity.this.startActivity(myIntent);
            }
        });
    }

    public void printMove(int coinsLeft, int coinsTaken, User user){

        remainingLbl.setText(String.format("%d", coinsLeft));

        if(user instanceof AI){
            lastMoveLbl.setText(String.format(getResources().getString(R.string.aiTaken), coinsTaken));
            trashTalkLbl.setText(ai.getRandomTrashTalk());
        }
        if(user instanceof Player){
            lastMoveLbl.setText(String.format(getResources().getString(R.string.playerTaken), coinsTaken));
        }

    }

    public void checkWin(User lastUser){
        if(game.isOver()){
            if(lastUser instanceof Player)
                trashTalkLbl.setText(R.string.aiWin);
                trashTalkLbl.setTextColor(Color.parseColor("#FF0000"));
            if(lastUser instanceof AI) {
                trashTalkLbl.setText(R.string.playerWin);
                trashTalkLbl.setTextColor(Color.parseColor("#5ec165"));
            }
            btnTakeOne.setEnabled(false);
            btnTakeTwo.setEnabled(false);
        }
        if(game.getCoinsLeft() == 1)
            btnTakeTwo.setEnabled(false);
    }

    public void initInterface(){
        remainingLbl.setText(String.format("%d", game.getCoinsLeft()));
        trashTalkLbl.setText(ai.getRandomGreeting());
    }

    protected void initGame(){
        this.game = new CoinGame(this.getIntent().getIntExtra("cgtotal", 23), this.getIntent().getBooleanExtra("cgfirst", true), this.getIntent().getIntExtra("cgdiff", 2));
        System.out.println(game.getCoinsLeft());
        game.connectApp(this);
        this.ai = game.getAI();
        this.player = game.getPlayer();
        this.initInterface();
        btnTakeTwo.setEnabled(true);
        btnTakeOne.setEnabled(true);
    }
}
