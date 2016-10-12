package com.ist_311.slidingpuzzle.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.ist_311.slidingpuzzle.R;
import com.ist_311.slidingpuzzle.utilities.DBHelper;
import com.ist_311.slidingpuzzle.utilities.DatabaseManager;

public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        // Database Manager
        DBHelper dbHelper = new DBHelper(this.getApplicationContext());
        DatabaseManager.initializeInstance(dbHelper);
    }

    /**
     * Play button listener.
     * @param view the login button.
     */
    @SuppressWarnings("unused")
    public void play(@SuppressWarnings("UnusedParameters") View view) {

        Intent intent = new Intent(this, PuzzleActivity.class);
        startActivity(intent);
    }

    /**
     * Settings button listener.
     * @param view the login button.
     */
    @SuppressWarnings("unused")
    public void settings(@SuppressWarnings("UnusedParameters") View view) {

        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

}
