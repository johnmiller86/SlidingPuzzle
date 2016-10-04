package com.ist_311.slidingpuzzle.activities;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;

import com.ist_311.slidingpuzzle.R;
import com.ist_311.slidingpuzzle.utilities.DBHelper;
import com.ist_311.slidingpuzzle.utilities.DatabaseManager;
import com.ist_311.slidingpuzzle.utilities.SessionManager;

import java.util.List;

public class MainActivity extends FragmentActivity {

    // Session
    private SessionManager sessionManager;
    public static Fragment fragment;
    private final String FRAGMENT_TAG = "fragment_tag";
    public static final String PUZZLE_MODE_TAG = "puzzle_mode_tag";

    @SuppressLint("CommitTransaction")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Instantiating Session
        sessionManager = new SessionManager(getApplicationContext());

        // Database Manager
        DBHelper dbHelper = new DBHelper(this.getApplicationContext());
        DatabaseManager.initializeInstance(dbHelper);

        // Loading Fragment
        if (savedInstanceState != null && getSupportFragmentManager().getFragment(savedInstanceState, FRAGMENT_TAG) != null){
            // Recovering Fragment
            fragment = getSupportFragmentManager().getFragment(savedInstanceState, FRAGMENT_TAG);
        }else {
            // Creating new Fragment
            fragment = new MainMenuFragment();
        }
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {

        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {

            getSupportFragmentManager().popBackStack();
        }else{

            new AlertDialog.Builder(this)
                .setTitle("Exit")
                .setMessage("Are you sure you want to quit?")

                // Open Settings button
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                       finish();
                    }
                })

                // Denied, close app
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setIcon(R.mipmap.ic_launcher)
                .show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if (fragments != null) {
            for (Fragment fragment : fragments) {

                // Process the Fragment's permission request
                fragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle savedFragment) {
        super.onSaveInstanceState(savedFragment);

        //Save the Fragment's instance
        if (getSupportFragmentManager().getBackStackEntryCount() != 0) {
            getSupportFragmentManager().putFragment(savedFragment, FRAGMENT_TAG, fragment);
        }
    }
}
