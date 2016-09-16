package com.ist_311.slidingpuzzle;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
    }

    /**
     * Login button listener.
     * @param view the login button.
     */
    public void play(View view) {

        Intent intent = new Intent(this, PuzzleActivity.class);
        startActivity(intent);
    }

    /**
     * Login button listener.
     * @param view the login button.
     */
    public void settings(View view) {
//
//        Intent intent = new Intent(this, SettingsActivity.class);
//        startActivity(intent);
    }

//    /**
//     * Login button listener.
//     * @param view the login button.
//     */
//    public void highScores(View view) {
//
//        Intent intent = new Intent(this, HighScoresActivity.class);
//        startActivity(intent);
//    }
}
