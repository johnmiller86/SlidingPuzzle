package com.ist_311.slidingpuzzle.utilities;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.facebook.FacebookSdk.getApplicationContext;
import static com.ist_311.slidingpuzzle.activities.MainActivity.sessionManager;

public class SettingFunctions {

    /**
     * Gets the user's settings from MySQL.
     */
    public void getSettings(final Context context, final String email) {
        String requestString = "get_settings";
        StringRequest strReq = new StringRequest(Request.Method.POST, Config.URL_GET_SETTINGS, new Response.Listener<String>() {

                @Override
            public void onResponse(String response) {

                try {
                    // Retrieve JSON error object
                    JSONObject jsonObject = new JSONObject(response);
                    boolean error = jsonObject.getBoolean("error");

                    // Check for error node in json
                    if (!error) {
                        // Configuring settings
                        sessionManager.setCols(jsonObject.getInt("columns"));
                        sessionManager.setRows(jsonObject.getInt("rows"));
                        sessionManager.setPuzzlePath(jsonObject.getString("puzzle_path"));
                    } else {
                        // Error fetching data. Get the error message
                        String errorMsg = jsonObject.getString("error_msg");
                        Toast.makeText(context, errorMsg, Toast.LENGTH_LONG).show();
                    }
                }
                // JSON error
                catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(context, "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                return params;
            }
        };

        // Adding request to request queue
        VolleyController.getVolleyController().addToRequestQueue(strReq, requestString);
    }

    /**
     * Saves the user's settings in MySQL.
     */
    public void saveSettings(final Activity activity) {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        String requestString = "save_settings";
        StringRequest strReq = new StringRequest(Request.Method.POST, Config.URL_SAVE_SETTINGS, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);

                try {
                    // Retrieve JSON error object
                    JSONObject jsonObject = new JSONObject(response);
                    boolean error = jsonObject.getBoolean("error");

                    // Check for error node in json
                    if (!error) {
                        Toast.makeText(getApplicationContext(), "Settings saved!",Toast.LENGTH_LONG);
                    } else {
                        // Error fetching data. Get the error message
                        String errorMsg = jsonObject.getString("error_msg");
                        Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_LONG).show();
                    }
                }
                // JSON error
                catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<>();
                params.put("email", sessionManager.getEmail());
                params.put("rows", String.valueOf(sessionManager.getRows()));
                params.put("columns", String.valueOf(sessionManager.getCols()));
                params.put("puzzle_path", sessionManager.getPuzzlePath());
                return params;
            }
        };

        // Adding request to request queue
        VolleyController.getVolleyController().addToRequestQueue(strReq, requestString);
    }
}