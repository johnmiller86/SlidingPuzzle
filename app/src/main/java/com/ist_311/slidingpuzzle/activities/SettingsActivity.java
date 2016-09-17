package com.ist_311.slidingpuzzle.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.NumberPicker;

import com.ist_311.slidingpuzzle.R;
import com.ist_311.slidingpuzzle.utilities.SessionManager;

public class SettingsActivity extends AppCompatActivity {

    // Session
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        initialize();
    }

    private void initialize() {
        sessionManager = new SessionManager(getApplicationContext());
        NumberPicker numberPickerRows = (NumberPicker) findViewById(R.id.numberPickerRows);
        NumberPicker numberPickerCols = (NumberPicker) findViewById(R.id.numberPickerCols);
        ImageView imageView = (ImageView) findViewById(R.id.imageView);

        numberPickerCols.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i2) {
                sessionManager.setCols(i2);
            }
        });

        numberPickerRows.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i2) {
                sessionManager.setRows(i2);
            }
        });

        numberPickerRows.setMaxValue(8);
        numberPickerRows.setMinValue(2);
        numberPickerRows.setValue(sessionManager.getRows());
        numberPickerCols.setMaxValue(8);
        numberPickerCols.setMinValue(2);
        numberPickerCols.setValue(sessionManager.getCols());

    }
}
