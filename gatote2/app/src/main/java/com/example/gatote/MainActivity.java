package com.example.gatote;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView playerOneScore, playerTwoScore, playerStatus;
    private Button[] buttons = new Button[36];
    private Button resetGame;
    private int PlayerOneScoreCount, PlayerTwoScoreCount, roundCount;
    boolean activePlayer;

    int [] gameState={2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2};
    int [][] posicionGanadora={
            {0,1,2,3},{1,2,3,4},{2,3,4,5},{6,7,8,9}, {7,8,9,10},{8,9,10,11},{12,13,14,15},{13,14,15,16},{14,15,16,17}, {18,19,20,21},{19,20,21,22},{20,21,22,23}, {24,25,26,27},{25,26,27,28},{26,27,28,29}, {30,31,32,33},{31,32,33,34},{32,33,34,35},
            {0,6,12,18},{6,12,18,24},{12,18,24,30},{1,7,13,19},{7,13,19,25},{13,19,25,31}, {2,8,14,20},{8,14,20,26},{14,20,26,32}, {3,9,15,21},{9,15,21,27},{15,21,27,33}, {4,10,16,22},{10,16,22,28},{16,22,28,34}, {5,11,17,23},{11,17,23,29},{17,23,29,35}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playerOneScore = (TextView)  findViewById(R.id.playerOneScore);
        playerTwoScore = (TextView) findViewById(R.id.playerTowneScore);
        playerStatus = (TextView) findViewById(R.id.PlayerStatus);

        resetGame = (Button) findViewById(R.id.resteGame);

        for(int i=0; i< buttons.length; i++){
            String butonId= "btn" + i;
            int resourceId = getResources().getIdentifier(butonId, "id",getPackageName());
            buttons[i] = (Button) findViewById(resourceId);
            buttons[i].setOnClickListener(this);
        };
        roundCount=0;
        PlayerOneScoreCount =0;
        PlayerTwoScoreCount=0;
        activePlayer=true;
    }

    @Override
    public void onClick(View view) {
        if(!((Button)view).getText().toString().equals("")){
            return;
        }
        String butonId = view.getResources().getResourceEntryName(view.getId());
        int gameStatePointer = Integer.parseInt(butonId.substring(butonId.length()-1,butonId.length()));

        if (activePlayer){
            ((Button)view).setText("x");
            ((Button)view).setTextColor(Color.parseColor("#8758FF"));
            gameState[gameStatePointer]=0;
        }else{
            ((Button)view).setText("O");
            ((Button)view).setTextColor(Color.parseColor("#F5E740"));
            gameState[gameStatePointer]=1;
        }
        roundCount++;
        if(checkW()){
            if(activePlayer){
            PlayerOneScoreCount++;
            updatePlayerScore();
            Toast.makeText(this,"player one won", Toast.LENGTH_SHORT).show();
            playAgain();
            }else {
                PlayerTwoScoreCount++;
                updatePlayerScore();
                Toast.makeText(this, "player two won", Toast.LENGTH_SHORT).show();
                playAgain();
            }

        }else if(roundCount==36) {
            playAgain();
            Toast.makeText(this, "no winner", Toast.LENGTH_SHORT).show();
        }else{
            activePlayer = !activePlayer;
        }
        if(PlayerOneScoreCount>PlayerTwoScoreCount){
            playerStatus.setText("el jugador uno va ganando");
        }else if(PlayerTwoScoreCount>PlayerOneScoreCount){
            playerStatus.setText("el jugador dos va ganando");
        }else{
            playerStatus.setText("niguno va ganando");
        }
        resetGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playAgain();
                PlayerOneScoreCount=0;
                PlayerTwoScoreCount=0;
                playerStatus.setText("");
                updatePlayerScore();
            }
        });
    }

    public boolean checkW(){
        boolean winRest = false;

        for(int [] winposs : posicionGanadora){
            if(gameState[winposs[0]] == gameState[winposs[1]] &&
                    gameState[winposs[1]] == gameState[winposs[2]] &&
                    gameState[winposs[2]] == gameState[winposs[3]] &&
                        gameState[winposs[0]] != 2){
                winRest=true;
            }
        }
        return winRest;
    }
    public void updatePlayerScore ( ) {
        playerOneScore.setText(Integer.toString(PlayerOneScoreCount));
        playerTwoScore.setText(Integer.toString(PlayerTwoScoreCount));
    }

    public void playAgain(){
        roundCount=0;
        activePlayer=true;
        for(int i=0;i < buttons.length ; i++ ) {
        gameState[i] = 2;
        buttons[i].setText("");
        }
    }
}