package com.example.lineapp.appgame;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public class Activiyapp_game extends AppCompatActivity {

    private MyViewGame game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        game = new MyViewGame(this);
        setContentView(game);
    }

    @Override
    protected void onStart() {
        Log.d("Activiyapp_game.java", "onStart()");
        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.d("Activiyapp_game.java", "onResume()");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.d("Activiyapp_game.java", "onPause()");
        game.pause();
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.d("Activiyapp_game.java", "onStop()");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d("Activiyapp_game.java", "onDestroy()");
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}